package com.curpick.CurPick.domain.worknet.service;

import com.curpick.CurPick.domain.worknet.dto.WorknetResponseDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WorknetService {

    @Value("${worknet.auth.key}")
    private String authKey;

    private final WebClient webClient = WebClient.create();
    private final XmlMapper xmlMapper = new XmlMapper();

    public WorknetResponseDto fetchJobByCode(String jobCode) throws Exception {
        String url = String.format(
                "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo212L01.do" +
                        "?authKey=%s&returnType=XML&target=JOBCD&jobcd=%s",
                authKey, jobCode
        );

        String xml = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return xmlMapper.readValue(xml, WorknetResponseDto.class);
    }
}