package com.isees.services;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.isees.entities.StudentExam;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;

@Service
public class ResultEmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public void sendExamResultMail(String toEmail, String studentName, int score, int total, List<StudentExam> answers) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Map<String, Object> model = new HashMap<>();
            model.put("studentName", studentName);
            model.put("score", score);
            model.put("total", total);
            model.put("year", Year.now().toString());

            // Prepare answer list for email
            List<Map<String, Object>> formattedAnswers = answers.stream().map(a -> {
                Map<String, Object> m = new HashMap<>();
                m.put("question", a.getQuestion());
                m.put("selected", a.getAnswer());
                m.put("correctAnswer", a.getCorrectAnswer());
                m.put("correct", a.getStatus().equalsIgnoreCase("Correct"));
                return m;
            }).toList();

            model.put("answers", formattedAnswers);

            Template template = config.getTemplate("email-exam-result.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(toEmail);
            helper.setSubject("ISEES Online Exam Result");
            helper.setText(html, true);

            sender.send(message);
            System.out.println("✅ Exam result email sent to: " + toEmail);

        } catch (Exception e) {
            System.err.println("❌ Failed to send exam result email: " + e.getMessage());
        }
    }
}
