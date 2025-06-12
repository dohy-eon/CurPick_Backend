package com.curpick.CurPick.domain.review.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private String company;
    private int interviewerCount;
    private String review;
    private int difficulty;
    private int mood;
}