package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.entity.Book;
import org.example.focus.repsitory.BookRepository;
import org.example.focus.util.EncryptUtil;
import org.example.focus.util.FileRequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final FileRequestService fileRequestService;

    public BaseResponse<CalendarReadInfoResponseDto> showCalendarData(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.withDayOfMonth(startDate.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        // 책을 읽은 날짜 추축
        List<LocalDate> readDateList = bookRepository.findByRegisteredDateBetween(startDate, endDate)
                .stream().map(a -> a.getRegisteredDate().toLocalDate())
                .distinct()
                .collect(Collectors.toList());
        return BaseResponse.success(CalendarReadInfoResponseDto.of(readDateList, year, month));
    }

    public void processBook(BookCoverRequestDto request, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
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
                .modifiedDate(LocalDateTime.now())
                .registeredDate(LocalDateTime.now())
                .build();

        fileRequestService.sendBookImageReqeust(ImageRequestDto.of(book), file);
        bookRepository.save(book);
    }

    public BaseResponse<List<BookListResponseDto>> showBookList() {
        List<Book> bookList = bookRepository.findAllByOrderByModifiedDateDesc();
        return BaseResponse.success(
                bookList.stream()
                        .map(b -> BookListResponseDto.from(b))
                        .toList());
    }
}
