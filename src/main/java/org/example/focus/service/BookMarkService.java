package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.repsitory.BookMarkRepository;
import org.example.focus.repsitory.BookRepository;
import org.example.focus.util.EncryptUtil;
import org.example.focus.util.FileRequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;

    public void processBookMark(BookMarkRequestDto request, MultipartFile file) {
        String originName = file.getOriginalFilename();
        String extension = originName.substring(0, originName.lastIndexOf("."));
        Book book = bookRepository.findById(request.getBookId()).get();

        BookMark bookMark = BookMark.builder()
                .date(LocalDateTime.now())
                .page(request.getPage())
                .text(request.getText())
                .thumbnailImage(EncryptUtil.imageAccessUrl + request.getTitle() + "/" +
                        request.getTitle() + "thumbnail" + request.getPage() + "." + extension)
                .extension(extension)
                .build();
        bookMark.changeBook(book);

        fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
        bookMarkRepository.save(bookMark);
    }

    public List<AllBookMarkResponseDto> showBookMarkList(Long bookId) {
        List<BookMark> bookMarkList = bookMarkRepository.findAllByBookIdOrderByDateAsc(bookId);
        return bookMarkList.stream()
                .map(b -> AllBookMarkResponseDto.from(b))
                .toList();
    }

    public BookMarkResponseDto showBookMark(Long bookMarkId) {
        return bookMarkRepository.findById(bookMarkId)
                .map(b -> BookMarkResponseDto.from(b))
                .get();
    }

    public void modifyBookMark(long bookMarkId, BookMarkModifyRequestdto request, MultipartFile file) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId).get();
        bookMark.changeBookMarkInfo(request);

        if (file != null) {
            String originName = file.getOriginalFilename();
            String extension = originName.substring(0, originName.lastIndexOf("."));
            bookMark.changeExtension(extension);
            fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
            fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
        }

        bookMarkRepository.save(bookMark);
    }

    public void deleteBookMark(long bookMarkId) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId).get();
        bookMarkRepository.delete(bookMark);
        fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
    }

}
