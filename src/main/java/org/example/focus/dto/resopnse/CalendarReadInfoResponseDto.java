package org.example.focus.dto.resopnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CalendarReadInfoResponseDto {
    private List<ReadData> readData;

    public static CalendarReadInfoResponseDto of(List<LocalDate> readDateList, int year, int month) {
        List<LocalDate> dates = new ArrayList<>();
        List<ReadData> readData = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            dates.add(LocalDate.of(year, month, i));
        }

        for (LocalDate date : dates) {
            if (readDateList.contains(date)) {
                readData.add(new ReadData(date, true));
                continue;
            }
            readData.add(new ReadData(date, false));
        }

        return CalendarReadInfoResponseDto.builder()
                .readData(readData)
                .build();
    }

    @AllArgsConstructor
    @Getter
    static class ReadData {
        private LocalDate date;
        private boolean isFilled;
    }
}
