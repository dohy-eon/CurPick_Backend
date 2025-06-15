package com.curpick.CurPick.domain.worknet.service;

import com.curpick.CurPick.domain.worknet.dto.WorknetResponseDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorknetService {

    @Value("${worknet.auth.key}")
    private String authKey;

    private final WebClient webClient = WebClient.create();
    private final XmlMapper xmlMapper = new XmlMapper();

    // 전체 직업 목록 한 번에 가져오는 메서드
    public WorknetResponseDto fetchAllJobs() throws Exception {
        String url = String.format(
                "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo212L01.do" +
                        "?authKey=%s&returnType=XML&target=JOBCD",
                authKey
        );

        String xml = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return xmlMapper.readValue(xml, WorknetResponseDto.class);
    }

    // 페이징 처리된 직업 목록 반환
    public List<WorknetResponseDto.Job> getJobsByPage(int pageNo, int pageSize) throws Exception {
        WorknetResponseDto fullResponse = fetchAllJobs();
        List<WorknetResponseDto.Job> jobs = fullResponse.getJobList();

        if (jobs == null || jobs.isEmpty()) {
            return Collections.emptyList();
        }

        int start = (pageNo - 1) * pageSize;
        if (start >= jobs.size()) {
            return Collections.emptyList();
        }

        int end = Math.min(start + pageSize, jobs.size());

        return jobs.subList(start, end);
    }
}
