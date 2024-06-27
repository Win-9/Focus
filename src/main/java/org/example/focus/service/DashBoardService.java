package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.BookMarkCountResponse;
import org.example.focus.dto.resopnse.ContinuousReadDateResponse;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.repository.BookMarkRepository;
import org.example.focus.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashBoardService {
    private final BookRepository bookRepository;
    private final BookMarkRepository bookMarkRepository;

    public ContinuousReadDateResponse getCount() {
        // 최고기록
        List<LocalDate> readDateList = getReadDate();
        int maxConsecutiveDays = getMaxConsecutiveDays(readDateList);

        // 연속일
        LocalDate current = LocalDate.now();
        int continueousDays = getContinueousDays(readDateList, current);

        return ContinuousReadDateResponse.of(continueousDays, maxConsecutiveDays);
    }

    private static int getContinueousDays(List<LocalDate> readDateList, LocalDate current) {
        int continueousDays = 0;
        for (int i = readDateList.size() - 1; i >= 0; i--) {
            if (!current.equals(readDateList.get(i))) {
                break;
            }
            continueousDays++;
            current = readDateList.get(i);
        }
        return continueousDays;
    }

    private static int getMaxConsecutiveDays(List<LocalDate> readDateList) {
        int currentStack = 1;
        int maxConsecutiveDays = 0;
        for (int i = 1; i < readDateList.size(); i++) {
            if (readDateList.get(i).equals(readDateList.get(i - 1).plusDays(1))) {
                currentStack++;
            } else {
                maxConsecutiveDays = Math.max(maxConsecutiveDays, currentStack);
                currentStack = 1;
            }
        }
        return maxConsecutiveDays;
    }

    private List<LocalDate> getReadDate() {
        // 책 수정 날짜 추출
        List<LocalDate> bookReadDateList = bookRepository.findAll()
                .stream().map(Book::getRegisteredDate)
                .collect(Collectors.toList());

        // 북마크 수정 날짜 추출
        List<LocalDate> bookMarkReadDateList = bookMarkRepository.findAll()
                .stream().map(BookMark::getModifiedDate)
                .collect(Collectors.toList());

        List<LocalDate> readDateList = Stream.of(bookReadDateList, bookMarkReadDateList)
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .toList();
        return readDateList;
    }

    public BookMarkCountResponse getBookMarkCount() {
        long bookMarkCount = bookMarkRepository.findBookMarkCountByLocalDate(LocalDate.now());
        long oneMonthBeforeCount = bookMarkRepository.findBookMarkCountByLocalDate(LocalDate.now().minusMonths(1));

        return BookMarkCountResponse.of(bookMarkCount, oneMonthBeforeCount);
    }
}
