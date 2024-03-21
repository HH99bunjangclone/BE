package com.sparta.hhztclone.domain.member.service;

import com.sparta.hhztclone.domain.member.repository.EmailRedisRepository;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final EmailRedisRepository emailRedisRepository;

    // 이메일 인증 코드 보내기
    public String emailSend(String email) {
        // 6자리 랜덤 숫자 생성
        String certificationNumber = createCertificationNumber();

        try {
            // redis에 Key 존재 여부 확인 후 저장
            // email : certificationNumber
            if (emailRedisRepository.hasKey(email)) {
                emailRedisRepository.removeCertificationNumber(email);
            }
            emailRedisRepository.saveCertificationNumber(email, certificationNumber);
            
            // 인증 코드 이메일 전송
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("항해마켓 회원가입 인증번호"); // 메일 제목
            mimeMessageHelper.setText(certificationNumber); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }

        return certificationNumber;
    }

    // 이메일 인증 코드 일치 여부 확인
    public boolean emailAuthCheck(String email, String emailCode) {
        String certificationNumber = emailRedisRepository.getCertificationNumber(email);

        if (certificationNumber.equals(emailCode)) {
            emailRedisRepository.removeCertificationNumber(email);
            return true;
        }

        return false;
    }

    // 6자리 랜덤 숫자 만들기
    private String createCertificationNumber()  {
        Random random = new Random();
        int num = random.nextInt(888888) + 111111;
        return String.valueOf(num);
    }

}
