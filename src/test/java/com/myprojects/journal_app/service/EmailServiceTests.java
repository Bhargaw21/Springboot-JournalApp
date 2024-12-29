package com.myprojects.journal_app.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceTests {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    public EmailServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        // Arrange
        SimpleMailMessage mockMessage = new SimpleMailMessage();
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendEmail("privatework950@gmail.com", "Test Subject", "Test Body");

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
