package org.github.waldemberg.estoqueapp.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.github.waldemberg.estoqueapp.model.mail.EmailModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailApp;
    @Value("${spring.application.name}")
    private String nomeApp;
    private final Configuration configuration;

    public EmailServiceImpl(JavaMailSender mailSender, Configuration configuration) {
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @Override
    public boolean enviarEmail(EmailModel email) {

        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            helper.setFrom(emailApp, nomeApp);
            helper.setSubject(email.getAssunto());

            if (!email.getDestinatarios().isEmpty()) {
                helper.setTo(email.getDestinatarios().toArray(String[]::new));
            }

            helper.setText(getEmailContent(email), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | TemplateException | IOException e) {
            return false;
        }

        return true;
    }

    private String getEmailContent(EmailModel emailModel) throws IOException, TemplateException {
        var stringWriter = new StringWriter();
        configuration.getTemplate(emailModel.getTipo() + ".ftlh").process(emailModel.getDados(), stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
