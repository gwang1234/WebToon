//package org.example.webtoonepics.question.service;
//
//import jakarta.mail.Message;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.example.webtoonepics.config.MailConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Random;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Log4j2
//public class MailService {
//    private final JavaMailSender javaMailSender;
//
//    private String key;
//    @Value("${naver.id}")
//    private String id;
//
//    // 코드 생성 시간을 나타내는 Instant 객체입니다.
//    private Instant codeGenerationTime;
//    // Duration 클래스는 두 시간 간의 차이를 나타내기 위한 클래스입니다.(3분)
//    private Duration validityDuration = Duration.ofMinutes(3);
//
//    public String sendSimpleMessage(String to) throws Exception {
//        key = createKey(); // 랜덤 인증코드 생성
//        log.info("********생성된 랜덤 인증코드******** => " + key);
//
//        MimeMessage message = creatMessage(to); // "to" 로 메일 발송
//        log.info("********생성된 메시지******** => " + message);
//        // 예외처리
//        try {
//            // 이게 메일로 보내주는 메소드
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException();
//        }
//        // 메일로 사용자에게 보낸 인증코드를 서버로 반환! 인증코드 일치여부를 확인하기 위함
//        return key;
//    }
//
//    // 랜덤 인증코드 생성
//    private String createKey() throws Exception {
//        int length = 6;
//        try {
//            // SecureRandom.getInstanceStrong()을 호출하여 강력한 (strong) 알고리즘을 사용하는 SecureRandom 인스턴스를 가져옵니다.
//            // 이는 예측하기 어려운 안전한 랜덤 값을 제공합니다.
//            Random random = SecureRandom.getInstanceStrong();
//            // StringBuilder는 가변적인 문자열을 효율적으로 다루기 위한 클래스입니다.
//            // 여기서는 생성된 랜덤 값을 문자열로 변환하여 저장하기 위해 StringBuilder를 사용합니다.
//            StringBuilder builder = new StringBuilder();
//            for (int i = 0; i < length; i++) {
//                builder.append(random.nextInt(10));
//            }
//            return builder.toString();
//        } catch (NoSuchAlgorithmException e) {
//            log.debug("MemberService.createCode() exception occur");
//            throw new Exception(e.getMessage());
//        }
//    }
//
//
//}
