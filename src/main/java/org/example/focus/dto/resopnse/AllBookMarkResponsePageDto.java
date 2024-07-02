package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

@Builder
@Getter
public class AllBookMarkResponsePageDto {
    private LinkedHashMap<LocalDate, List<AllBookMarkResponseDto>> elements;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;

    public static AllBookMarkResponsePageDto from(LinkedHashMap<LocalDate, List<AllBookMarkResponseDto>> elements,
                                                  Page<AllBookMarkResponseDto> pageResult) {
        return AllBookMarkResponsePageDto.builder()
                .elements(elements)
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .build();
    }
}
