package com.curpick.CurPick.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, String> authStorage = new HashMap<>();

    @Override
    public void sendAuthCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // 실제로는 Redis 같은 DB에 저장하는 게 안정적
        authStorage.put(email, code);

        String subject = "[CurPick] 이메일 인증 코드입니다.";
        String text = "인증 코드: " + code + "\n5분 내에 입력해 주세요.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("titeotty12@naver.com");

        mailSender.send(message);
        System.out.println("[메일 전송 완료] " + email + " → " + code);
    }

    @Override
    public void verifyCode(String email, String authCode) {
        String storedCode = authStorage.get(email);
        if (storedCode == null || !storedCode.equalsIgnoreCase(authCode)) {
            throw new IllegalArgumentException("인증 코드가 유효하지 않습니다.");
        }
        System.out.println("[인증 성공] " + email);
    }
}