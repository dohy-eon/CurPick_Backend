package com.curpick.CurPick.domain.worknet.controller;

import com.curpick.CurPick.domain.worknet.dto.WorknetResponseDto;
import com.curpick.CurPick.domain.worknet.service.WorknetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worknet")
@RequiredArgsConstructor
public class WorknetController {

    private final WorknetService worknetService;

    @GetMapping("/jobs")
    public ResponseEntity<List<WorknetResponseDto.Job>> getJobs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) throws Exception {

        List<WorknetResponseDto.Job> jobs = worknetService.getJobsByPage(page, size);

        return ResponseEntity.ok(jobs);
    }
}