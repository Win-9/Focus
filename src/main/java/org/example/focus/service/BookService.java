package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.BookResponseDto;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.exist.BookExistException;
import org.example.focus.exception.notFound.FileBoundException;
import org.example.focus.exception.notexist.BookNotExistException;
import org.example.focus.repsitory.BookMarkRepository;
import org.example.focus.repsitory.BookRepository;
import org.example.focus.util.EncryptUtil;
import org.example.focus.util.FileRequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;
    private final BookMarkRepository bookMarkRepository;

    public CalendarReadInfoResponseDto showCalendarData(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.withDayOfMonth(startDate.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        // 책 수정 날짜 추출
        List<LocalDate> bookReadDateList = bookRepository.findAllByModifiedDateBetween(startDate, endDate)
                .stream().map(a -> a.getRegisteredDate())
                .collect(Collectors.toList());

        // 북마크 수정 날짜 추출
        List<LocalDate> bookMarkReadDateList = bookMarkRepository.findAllByModifiedDateBetween(startDate, endDate)
                .stream().map(a -> a.getModifiedDate())
                .collect(Collectors.toList());

        List<LocalDate> readDateList = Stream.of(bookReadDateList, bookMarkReadDateList)
                .flatMap(x -> x.stream())
                .distinct()
                .collect(Collectors.toList());

        return CalendarReadInfoResponseDto.of(readDateList, year, month);
    }

    public BookResponseDto processBook(BookCoverRequestDto request, MultipartFile file) {
        boolean isBookExist = bookRepository.existsByTitle(request.getTitle());
        if (isBookExist) {
            throw new BookExistException(ErrorCode.EXIST_BOOK);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
        }

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .extension(extension)
                .coverImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                        request.getTitle() + "bookCover." + extension)
                .modifiedDate(LocalDate.now())
                .registeredDate(LocalDate.now())
                .build();

        fileRequestService.sendBookImageReqeust(ImageRequestDto.of(book), file);
        bookRepository.save(book);
        return BookResponseDto.from(book);
    }

    public List<BookListResponseDto> showBookList() {
        List<Book> bookList = bookRepository.findAllByOrderByModifiedDateDesc();
        return bookList.stream()
                .map(b -> BookListResponseDto.from(b))
                .toList();
    }

    public BookResponseDto modifyBook(long bookId, BookCoverRequestDto request, MultipartFile file) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));
        book.changeBookInformation(request);

        if (file != null) {
            String originName = file.getOriginalFilename();
            if (originName == null || originName.lastIndexOf(".") == -1) {
                throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
            }

            String extension = originName.substring(originName.lastIndexOf(".") + 1);
            book.changeExtension(extension);
            book.changeCoverImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                    request.getTitle() + "bookCover." + extension);

            fileRequestService.deleteBookImage(ImageRequestDto.of(book));
            fileRequestService.sendBookImageReqeust(ImageRequestDto.of(book), file);
        }
        return BookResponseDto.from(book);
    }

    public void removeBook(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));
        bookRepository.delete(book);
    }
}
