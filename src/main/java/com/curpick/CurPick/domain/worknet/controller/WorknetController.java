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
    private static final Logger log = LoggerFactory.getLogger(WorknetController.class); // 로그 추가

    @GetMapping("/job")
    public ResponseEntity<WorknetResponseDto> getJobInfoByCode(@RequestParam String code) {
        try {
            WorknetResponseDto res = service.fetchJobByCode(code);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("[WorknetController] 직업 코드 기반 API 호출 오류", e);
            return ResponseEntity.status(500).build();
        }
    }
}