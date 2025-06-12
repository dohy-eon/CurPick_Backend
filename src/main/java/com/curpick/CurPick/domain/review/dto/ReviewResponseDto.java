package com.curpick.CurPick.domain.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long id;
    private String company;
    private int interviewerCount;
    private String review;
    private int difficulty;
    private int mood;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}