package com.curpick.CurPick.domain.worknet.service;

import com.curpick.CurPick.domain.worknet.dto.WorknetResponseDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class WorknetService {

    @Value("${worknet.auth.key}")
    private String authKey;

    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    public WorknetService() {
        this.webClient = WebClient.builder().build();
        this.xmlMapper = new XmlMapper(); // 직접 생성
    }

    public WorknetResponseDto fetchAllJobs() throws Exception {
        String url = String.format(
                "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo212L01.do" +
                        "?authKey=%s&returnType=XML&target=JOBCD",
                authKey
        );

        try {
            log.info("[WorknetService] 전체 직업 목록 요청: {}", url);

            String xml = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return xmlMapper.readValue(xml, WorknetResponseDto.class);
        } catch (Exception e) {
            log.error("[WorknetService] 전체 직업 목록 조회 실패", e);
            throw e;
        }
    }
}