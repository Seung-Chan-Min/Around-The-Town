package com.prgm.aroundthetown.leisure.dto;

import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LeisureDto {
    private LeisureCategory leisureCategory;
    private String leisureInfomation;
    private String usecase;
    private String leisureNotice;
    private LocalDateTime expirationDate;
}
