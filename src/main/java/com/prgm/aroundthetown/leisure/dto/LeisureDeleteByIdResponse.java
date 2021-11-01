package com.prgm.aroundthetown.leisure.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LeisureDeleteByIdResponse {
    private Long id;
    private String leisureInfomation;
    private String usecase;
    private String leisureNotice;
    private LocalDateTime expirationDate;
    private String category;
}
