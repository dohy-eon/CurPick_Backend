package com.curpick.CurPick.domain.review.service;

import com.curpick.CurPick.domain.review.dto.ReviewRequestDto;
import com.curpick.CurPick.domain.review.dto.ReviewResponseDto;
import com.curpick.CurPick.domain.review.entity.Review;
import com.curpick.CurPick.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 🔹 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        Review review = Review.builder()
                .company(dto.getCompany())
                .interviewerCount(dto.getInterviewerCount())
                .review(dto.getReview())
                .difficulty(dto.getDifficulty())
                .mood(dto.getMood())
                .build();

        Review saved = reviewRepository.save(review);
        return toDto(saved);
    }

    // 🔹 전체 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 🔹 단일 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 리뷰를 찾을 수 없습니다."));
        return toDto(review);
    }

    // 🔹 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 리뷰가 존재하지 않습니다."));

        review.setCompany(dto.getCompany());
        review.setInterviewerCount(dto.getInterviewerCount());
        review.setReview(dto.getReview());
        review.setDifficulty(dto.getDifficulty());
        review.setMood(dto.getMood());

        return toDto(review);
    }

    // 🔹 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("삭제할 리뷰가 존재하지 않습니다.");
        }
        reviewRepository.deleteById(id);
    }

    // 🔹 Entity → DTO 변환
    private ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .company(review.getCompany())
                .interviewerCount(review.getInterviewerCount())
                .review(review.getReview())
                .difficulty(review.getDifficulty())
                .mood(review.getMood())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}