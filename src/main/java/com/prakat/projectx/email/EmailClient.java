package com.prakat.projectx.email;

import com.prakat.projectx.dto.EmailDTO;
import com.prakat.projectx.entity.User;
import com.prakat.projectx.exception.CustomException;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static jakarta.mail.Transport.send;

@Controller
public class EmailClient {
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    private static final Logger logger = LoggerFactory.getLogger(EmailClient.class);
    final String username = "timesheet@prakat.com";
    final String password = "Pr@katSh3et";

    Properties props = new Properties();

    User user;
    Session session;

    public EmailClient() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props, new Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public Boolean sendEmail(EmailDTO emailDTO) throws CustomException {
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDTO.getTo()));
            mimeMessage.setFrom(new InternetAddress("timesheet@prakat.com"));

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String templateContent = FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerConfig.getConfiguration()
                                    .getTemplate(emailDTO.getTemplate()),
                            emailDTO.getEmailData());
            mimeMessage.setSubject(emailDTO.getSubject());
            helper.setText(templateContent,true);
            send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("error sending mail", e);
            return false;
        }

    }

}