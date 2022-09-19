package com.springprojects.springredditclone.service;

import com.springprojects.springredditclone.exceptions.SpringRedditException;
import com.springprojects.springredditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    public void sendMail(NotificationEmail notificationEmail)
    {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springreddit@email.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try{
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!!");
        }
        catch (MailException e)
        {
            throw new SpringRedditException("Exception occured while sending mail to "+notificationEmail.getRecipient());
        }
        }

}