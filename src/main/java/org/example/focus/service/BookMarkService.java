package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.AllBookMarkResponsePageDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.notFound.FileBoundException;
import org.example.focus.exception.notexist.BookMarkNotExistException;
import org.example.focus.exception.notexist.BookNotExistException;
import org.example.focus.repository.BookMarkRepository;
import org.example.focus.repository.BookRepository;
import org.example.focus.util.EncryptUtil;
import org.example.focus.util.FileRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;

    public BookMarkResponseDto processBookMark(BookMarkRequestDto request, MultipartFile file) {
        Book book = bookRepository.findById(Long.valueOf(request.getBookId()))
                .orElseThrow(() -> new BookNotExistException(ErrorCode.BOOK_NOT_EXIST));

        BookMark bookMark = BookMark.builder()
                .date(LocalDate.now())
                .page(request.getPage())
                .content(request.getContent())
                .modifiedDate(LocalDate.now())
                .build();
        bookMark.changeBook(book);

        if (file != null) {
            String originFilename = getOriginFilename(file);
            String extension = getExtensionFromOriginFilename(originFilename);

            bookMark.changeExtension(extension);
            bookMark.changeThubnail(EncryptUtil.imageAccessUrl + book.getTitle() + "/" +
                    book.getTitle() + "thumbnail" + request.getPage() + "." + extension);

            String response = fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
            log.info("imageHost Save = {}", response);
        }

        bookMarkRepository.save(bookMark);
        return BookMarkResponseDto.from(bookMark);
    }

    private String getExtensionFromOriginFilename(String originFilename) {
        return originFilename.substring(originFilename.lastIndexOf(".") + 1);
    }

    private String getOriginFilename(MultipartFile file) {
        String originName = file.getOriginalFilename();
        if (originName == null || originName.lastIndexOf(".") == -1) {
            throw new FileBoundException(ErrorCode.EXTENSION_NOT_FOUND);
        }

        return originName;
    }

    public List<AllBookMarkResponseDto> showBookMarkList(Long memberId, Long bookId) {
        boolean isBookExist = bookRepository.existsByMemberIdAndId(memberId, bookId);
        if (!isBookExist) {
            throw new BookNotExistException(ErrorCode.BOOK_NOT_EXIST);
        }

        List<BookMark> bookMarkList = bookMarkRepository.findAllByBookIdOrderByModifiedDateAsc(bookId);

        return bookMarkList.stream()
                .map(AllBookMarkResponseDto::from)
                .toList();
    }

    public BookMarkResponseDto showBookMark(Long bookMarkId) {
        return bookMarkRepository.findById(bookMarkId)
                .map(BookMarkResponseDto::from)
                .orElseThrow(() -> new BookMarkNotExistException(ErrorCode.BOOKMAKR_NOT_EXIST));
    }

    public BookMarkResponseDto modifyBookMark(long bookMarkId, BookMarkModifyRequestdto request, MultipartFile file) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new BookMarkNotExistException(ErrorCode.BOOKMAKR_NOT_EXIST));

        bookMark.changeBookMarkInfo(request);
        bookMark.changeModifiedDate(LocalDate.now());

        if (file != null) {
            String originFilename = getOriginFilename(file);
            String extension = getExtensionFromOriginFilename(originFilename);

            bookMark.changeExtension(extension);
            bookMark.changeThubnail(EncryptUtil.imageAccessUrl + bookMark.getBook().getTitle() + "/" +
                    bookMark.getBook().getTitle() + "thumbnail" + request.getPage() + "." + extension);

            fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
            String response = fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
            log.info("imageHost Save = {}", response);
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

    public AllBookMarkResponsePageDto showAllBookMarkList(Pageable pageable, Long count) {
        Page<AllBookMarkResponseDto> pageResult = bookMarkRepository.findAllByOrderByModifiedDateDesc(pageable, count);
        List<AllBookMarkResponseDto> list = pageResult.getContent();
        LinkedHashMap<LocalDate, List<AllBookMarkResponseDto>> collect = list.stream()
                .collect(Collectors.groupingBy(
                        AllBookMarkResponseDto::getDate,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
        return AllBookMarkResponsePageDto.from(collect, pageResult);
    }
}
