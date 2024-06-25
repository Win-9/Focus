package org.example.focus.dto.resopnse;

import lombok.Builder;

@Builder
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
