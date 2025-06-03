package com.jobfeed.JobFeed.domain.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobfeed.JobFeed.global.exception.CustomException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class JobKoreaCrawler {

    private static final String API_URL = "https://www.jobkorea.co.kr/Recruit/Home/_SearchCount/";
    private static final String TOTAL_COUNT_KEY = "TotalCount";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public int getJobCount(Map<String, Object> conditionDataMap) {
        HttpHeaders headers = buildHeaders();
        MultiValueMap<String, String> body = buildRequestBody(conditionDataMap);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseTotalCount(response.getBody());
            } else {
                throw new CustomException("API_REQUEST_FAILED",
                        "JobKorea API 요청 실패 - 상태 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new CustomException("API_COMMUNICATION_ERROR",
                    "JobKorea API 통신 중 오류: " + e.getMessage());
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.add("Referer", "https://www.jobkorea.co.kr/Search/");
        headers.add("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.add("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private MultiValueMap<String, String> buildRequestBody(Map<String, Object> data) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("dkwrd", getString(data, "dkwrd"));
        body.add("local", getString(data, "local"));
        body.add("PageType", "Recruit");
        body.add("Count", "20");
        body.add("StartNo", "1");
        body.add("Ord", "RegDtAsc");
        body.add("TabType", "All");
        body.add("IsFront", "true");
        body.add("Sort", "RegDate");

        return body;
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }

    private int parseTotalCount(String responseBody) {
        // 1. 응답이 단순 숫자인 경우 먼저 처리
        try {
            return Integer.parseInt(responseBody.trim());
        } catch (NumberFormatException e) {
            // 2. JSON 객체인 경우 처리
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode countNode = root.path(TOTAL_COUNT_KEY);
                if (countNode.isMissingNode() || !countNode.isInt()) {
                    throw new CustomException("INVALID_RESPONSE_FORMAT",
                            "응답 JSON에서 'TotalCount'를 찾을 수 없거나 형식이 잘못됨: " + responseBody);
                }
                return countNode.asInt();
            } catch (Exception ex) {
                throw new CustomException("INVALID_RESPONSE_FORMAT",
                        "응답 JSON 파싱 실패: " + ex.getMessage() + " / 원본 응답: " + responseBody);
            }
        }
    }
}
