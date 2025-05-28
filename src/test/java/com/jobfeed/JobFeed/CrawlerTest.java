package com.jobfeed.JobFeed;

import com.jobfeed.JobFeed.domain.crawler.JobKoreaCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CrawlerTest {

    @Autowired
    private JobKoreaCrawler jobKoreaCrawler;

    @Test
    void testGetJobCount() {
        Map<String, Object> condition = new HashMap<>();
        condition.put("Keyword", "금융");
        condition.put("Location", "부산");

        int count = jobKoreaCrawler.getJobCount(condition);

        System.out.println("채용 공고 개수: " + count);
        assertTrue(count >= 0, "채용 공고 개수는 0 이상이어야 함");
    }
}