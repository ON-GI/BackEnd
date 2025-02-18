package com.ongi.backend.domain.center.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.service.EmailService;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.exception.CenterErrorCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterEmailService {

    private final EmailService emailService;

    /**
     * 센터 인증 요청 이메일 (관리자에게)
     */
    public void sendCenterVerificationRequestMail(Center center, String documentUrl) {
        String subject = "센터 인증 요청";
        String htmlContent = "<h3>📢 센터 인증 요청</h3>"
                + "<p>센터 인증 요청이 접수되었습니다.</p>"
                + "<ul>"
                + "<li><strong>센터 이름:</strong> " + center.getName() + "</li>"
                + "<li><strong>센터 ID:</strong> " + center.getId() + "</li>"
                + "<li><strong>증빙 자료 URL:</strong> <a href='" + documentUrl + "'>클릭하여 확인</a></li>"
                + "<li><strong>센터 이메일:</strong> " + center.getEmail() + "</li>"
                + "<li><strong>센터 주소:</strong> " + center.getAddress() + "</li>"
                + "</ul>"
                + "<p>관리자 페이지에서 확인 후 승인해 주세요.</p>";

        emailService.sendHtmlMailToAdmin(subject, htmlContent);
    }

    /**
     * 센터 인증 승인 이메일 (센터에게)
     */
    public void sendCenterApprovalMail(Center center) {
        if (center.getEmail() != null) {
            String subject = "센터 인증이 완료되었습니다";
            String htmlContent = "<h3>✅ 센터 인증 완료 안내</h3>"
                    + "<p>안녕하세요, <strong>" + center.getName() + "</strong>님!</p>"
                    + "<p>귀하의 센터가 성공적으로 인증되었습니다.</p>"
                    + "<ul>"
                    + "<li><strong>센터 코드:</strong> " + center.getCenterCode() + "</li>"
                    + "<li><strong>센터 이름:</strong> " + center.getName() + "</li>"
                    + "</ul>"
                    + "<p>센터 직원들은 위의 센터 코드를 입력하여 가입할 수 있습니다.</p>"
                    + "<p>감사합니다.</p>";

            emailService.sendHtmlMail(center.getEmail(), subject, htmlContent);
        } else{
            throw new ApplicationException(CenterErrorCase.CENTER_EMAIL_NOT_EXIST);
        }
    }
}
