package com.curpick.CurPick.domain.review.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;               // 회사명
    private int interviewerCount;         // 면접 인원

    @Lob
    @Column(columnDefinition = "TEXT")
    private String review;                // 면접 평가
    private int difficulty;               // 면접 난이도 (1~5)
    private int mood;                     // 면접 분위기 (1~5)

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
