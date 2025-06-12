package com.curpick.CurPick.domain.review.repository;

import com.curpick.CurPick.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}