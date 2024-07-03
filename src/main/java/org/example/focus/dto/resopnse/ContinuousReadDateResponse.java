package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContinuousReadDateResponse {
    private int continuousReadDate;
    private int maxDate;

    public static ContinuousReadDateResponse of(int continuousReadDate, int maxDate) {
        return ContinuousReadDateResponse.builder()
                .continuousReadDate(continuousReadDate)
                .maxDate(maxDate)
                .build();
    }
}
