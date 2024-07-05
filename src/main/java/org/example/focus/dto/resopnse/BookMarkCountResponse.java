package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookMarkCountResponse {
    private long bookMarkCount;
    private double changeAmount;

    public static BookMarkCountResponse of(long bookMarkCount, long changeAmount) {
        return BookMarkCountResponse.builder()
                .bookMarkCount(bookMarkCount)
                .changeAmount((bookMarkCount - changeAmount) / 100)
                .build();
    }
}
