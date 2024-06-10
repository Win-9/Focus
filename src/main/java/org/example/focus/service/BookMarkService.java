package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.BookMark;
import org.example.focus.repsitory.BookMarkRepository;
import org.example.focus.util.FileRequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final FileRequestService fileRequestService;

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

    public void modifyBookMark(long bookMarkId, BookMarkRequestDto request, MultipartFile file) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId).get();
        bookMark.changeBookMarkInfo(request);

        if (file != null) {
            fileRequestService.deleteBookImage(ImageRequestDto.of(bookMark));
            fileRequestService.sendBookImageReqeust(ImageRequestDto.of(bookMark), file);
        }

        bookMarkRepository.save(bookMark);
    }
//
//    public void deleteBookMark(long bookMarkId) {
//        BookMark bookMark = bookMarkRepository.findById(bookMarkId).get();
//        bookMarkRepository.delete(bookMark);
//    }
}
