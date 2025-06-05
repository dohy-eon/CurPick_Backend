package com.jobfeed.JobFeed.domain.user.service;

public interface EmailService {
    void sendAuthCode(String email);
    void verifyCode(String email, String authCode);
}