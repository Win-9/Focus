package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.BookResponseDto;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.exist.BookExistException;
import org.example.focus.exception.notFound.FileBoundException;
import org.example.focus.exception.notexist.BookNotExistException;
import org.example.focus.repository.BookMarkRepository;
import org.example.focus.repository.BookRepository;
import org.example.focus.util.EncryptUtil;
import org.example.focus.util.FileRequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;
    private final BookMarkRepository bookMarkRepository;

    public CalendarReadInfoResponseDto showCalendarData(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 책 수정 날짜 추출
        List<LocalDate> bookReadDateList = bookRepository.findAllByModifiedDateBetween(startDate, endDate)
                .stream().map(Book::getRegisteredDate)
                .collect(Collectors.toList());

        // 북마크 수정 날짜 추출
        List<LocalDate> bookMarkReadDateList = bookMarkRepository.findAllByModifiedDateBetween(startDate, endDate)
                .stream().map(BookMark::getModifiedDate)
                .collect(Collectors.toList());

        List<LocalDate> readDateList = Stream.of(bookReadDateList, bookMarkReadDateList)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        return CalendarReadInfoResponseDto.of(readDateList, year, month);
    }

    public BookResponseDto processBook(BookCoverRequestDto request, MultipartFile file) {
        boolean isBookExist = bookRepository.existsByTitle(request.getTitle());
        if (isBookExist) {
            throw new BookExistException(ErrorCode.EXIST_BOOK);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .modifiedDate(LocalDate.now())
                .registeredDate(LocalDate.now())
                .build();

        if (!file.isEmpty()) {
            String originalFilename = getOriginFileName(file);
            String extension = getExtensionFromOriginFilename(originalFilename);

            book.changeExtension(extension);
            book.changeCoverImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                    request.getTitle() + "bookCover." + extension);

            String response = fileRequestService.sendBookImageReqeust(ImageRequestDto.of(book), file);
            log.info("imageHost Save = {}", response);
        }
        bookRepository.save(book);
        return BookResponseDto.from(book);
    }

    private String getExtensionFromOriginFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return extension;
    }

    private String getOriginFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
        }
        return originalFilename;
    }

    public List<BookListResponseDto> showBookList() {
        List<Book> bookList = bookRepository.findAllByOrderByModifiedDateDesc();
        return bookList.stream()
                .map(BookListResponseDto::from)
                .toList();
    }

    public BookResponseDto modifyBook(long bookId, BookCoverRequestDto request, MultipartFile file) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));
        book.changeBookInformation(request);

        if (file != null) {
            String originFilename = getOriginFileName(file);
            String extension = getExtensionFromOriginFilename(originFilename);

            book.changeExtension(extension);
            book.changeCoverImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                    request.getTitle() + "bookCover." + extension);

            fileRequestService.deleteBookImage(ImageRequestDto.of(book));
            String response = fileRequestService.sendBookImageReqeust(ImageRequestDto.of(book), file);
            log.info("imageHost Save = {}", response);
        }
        return BookResponseDto.from(book);
    }

    public void removeBook(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));
        bookRepository.delete(book);
    }

    public BookResponseDto showBook(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));
        return BookResponseDto.from(book);
    }
}
