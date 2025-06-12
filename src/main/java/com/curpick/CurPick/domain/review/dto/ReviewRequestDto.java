package com.curpick.CurPick.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "면접 후기 등록/수정 요청 DTO")
public class ReviewRequestDto {

    @Schema(description = "회사명", example = "카카오")
    private String company;

    @Schema(description = "면접관 수", example = "2")
    private Integer interviewerCount;

    @Schema(description = "면접 후기 내용", example = "분위기는 편안했지만 질문은 다소 날카로웠어요.")
    private String review;

    @Schema(description = "난이도 (1~5)", example = "3")
    private Integer difficulty;

    @Schema(description = "분위기 (1~5)", example = "4")
    private Integer mood;
}
