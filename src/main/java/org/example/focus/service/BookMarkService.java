package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.notFound.FileBoundException;
import org.example.focus.exception.notexist.BookMarkNotExistException;
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

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;

    public BookMarkResponseDto processBookMark(BookMarkRequestDto request, MultipartFile file) {
        boolean isBookExist = bookRepository.existsByTitle(request.getTitle());
        if (!isBookExist) {
            throw new BookNotExistException(ErrorCode.BOOK_NOT_EXIST);
        }

        String originName = file.getOriginalFilename();

        if (originName == null || originName.lastIndexOf(".") == -1) {
            throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
        }

        String extension = originName.substring(originName.lastIndexOf(".") + 1);
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));

        BookMark bookMark = BookMark.builder()
                .date(LocalDateTime.now())
                .page(request.getPage())
                .text(request.getText())
                .thumbnailImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                        request.getTitle() + "thumbnail" + request.getPage() + "." + extension)
                .modifiedDate(LocalDate.now())
                .extension(extension)
                .build();
        bookMark.changeBook(book);

        fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
        bookMarkRepository.save(bookMark);
        return BookMarkResponseDto.from(bookMark);
    }

    public List<AllBookMarkResponseDto> showBookMarkList(Long bookId) {
        boolean isBookExist = bookRepository.existsById(bookId);
        if (!isBookExist) {
            throw new BookNotExistException(ErrorCode.BOOK_NOT_EXIST);
        }

        List<BookMark> bookMarkList = bookMarkRepository.findAllByBookIdOrderByDateAsc(bookId);

        return bookMarkList.stream()
                .map(b -> AllBookMarkResponseDto.from(b))
                .toList();
    }

    public BookMarkResponseDto showBookMark(Long bookMarkId) {
        return bookMarkRepository.findById(bookMarkId)
                .map(b -> BookMarkResponseDto.from(b))
                .orElseThrow(() -> new BookMarkNotExistException(ErrorCode.BOOKMAKR_NOT_EXIST));
    }

    public BookMarkResponseDto modifyBookMark(long bookMarkId, BookMarkModifyRequestdto request, MultipartFile file) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new BookMarkNotExistException(ErrorCode.BOOKMAKR_NOT_EXIST));

        bookMark.changeBookMarkInfo(request);
        bookMark.changeModifiedDate(LocalDate.now());

        if (file != null) {
            String originName = file.getOriginalFilename();
            if (originName == null || originName.lastIndexOf(".") == -1) {
                throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
            }

            String extension = originName.substring(0, originName.lastIndexOf("."));
            bookMark.changeExtension(extension);
            bookMark.changeThubnail(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                    request.getTitle() + "thumbnail" + request.getPage() + "." + extension);

            fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
            fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
        }

        bookMarkRepository.save(bookMark);
        return BookMarkResponseDto.from(bookMark);
    }

    public void deleteBookMark(long bookMarkId) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new BookMarkNotExistException(ErrorCode.BOOKMAKR_NOT_EXIST));
        bookMarkRepository.delete(bookMark);
        fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
    }

}
