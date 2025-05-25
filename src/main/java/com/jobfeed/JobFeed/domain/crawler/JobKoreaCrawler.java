package com.jobfeed.JobFeed.domain.crawler;

import com.jobfeed.JobFeed.global.exception.CustomException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class JobKoreaCrawler {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://www.jobkorea.co.kr/Recruit/Home/_SearchCount/";

    /**
     * conditionDataMap: 검색 조건 데이터 (예: 키워드, 지역 등)
     */
    public int getJobCount(Map<String, Object> conditionDataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        Map<String, Object> requestBody = Map.of("condition", conditionDataMap);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            try {
                return Integer.parseInt(responseBody.trim());
            } catch (NumberFormatException e) {
                throw new CustomException("INVALID_RESPONSE_FORMAT", "API 응답이 숫자가 아님: " + responseBody);
            }
        } else {
            throw new CustomException("API_REQUEST_FAILED",
                    "API 요청 실패, 상태 코드: " + response.getStatusCode() + ", 응답: " + response.getBody());
        }
    }
}