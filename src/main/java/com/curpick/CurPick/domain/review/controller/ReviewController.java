package com.curpick.CurPick.domain.review.controller;

import com.curpick.CurPick.domain.review.dto.ReviewRequestDto;
import com.curpick.CurPick.domain.review.dto.ReviewResponseDto;
import com.curpick.CurPick.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 🔹 후기 등록
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto dto) {
        ReviewResponseDto created = reviewService.createReview(dto);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.getId()))
                .body(created);
    }

    // 🔹 전체 후기 목록
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // 🔹 단일 후기 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        ReviewResponseDto review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // 🔹 후기 수정
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id,
                                                          @RequestBody ReviewRequestDto dto) {
        ReviewResponseDto updated = reviewService.updateReview(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 후기 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
