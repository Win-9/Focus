package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookMarkCountResponse {
    private long bookMarkCount;
    private double changeAmount;

    public static BookMarkCountResponse of(long bookMarkCount, long thisMonthBookMakrCount, long oneMonthBeforeCount) {
        return BookMarkCountResponse.builder()
                .bookMarkCount(bookMarkCount)
                .changeAmount((double) (thisMonthBookMakrCount - oneMonthBeforeCount) / oneMonthBeforeCount * 100)
                .build();
    }
}
