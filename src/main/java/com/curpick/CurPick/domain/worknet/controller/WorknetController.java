package com.curpick.CurPick.domain.worknet.controller;

import com.curpick.CurPick.domain.worknet.dto.WorknetResponseDto;
import com.curpick.CurPick.domain.worknet.service.WorknetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worknet")
@RequiredArgsConstructor
public class WorknetController {

    private final WorknetService service;
    private static final Logger log = LoggerFactory.getLogger(WorknetController.class);

    /**
     * 전체 직업 목록 조회
     */
    @GetMapping("/jobs") // URL도 복수형으로
    public ResponseEntity<WorknetResponseDto> getAllJobs() {
        try {
            log.info("[WorknetController] 전체 직업 목록 요청 시작");
            WorknetResponseDto res = service.fetchAllJobs();
            log.info("[WorknetController] 전체 직업 목록 요청 성공");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("[WorknetController] 전체 직업 목록 조회 실패", e);
            return ResponseEntity.status(500).build();
        }
    }
}