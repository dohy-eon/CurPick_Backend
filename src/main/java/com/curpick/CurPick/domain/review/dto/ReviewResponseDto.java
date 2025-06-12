package com.curpick.CurPick.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "면접 후기 응답 DTO")
public class ReviewResponseDto {

    @Schema(description = "리뷰 ID", example = "1")
    private Long id;

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

    @Schema(description = "등록일시", example = "2025-06-12T15:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시", example = "2025-06-12T15:05:00")
    private LocalDateTime updatedAt;
}