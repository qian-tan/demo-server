package com.jojoreading.demo.server.common.email;

import com.jojoreading.demo.server.test.ServerBaseApplicationTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailSenderServiceImplTest extends ServerBaseApplicationTestBase {

    @InjectMocks
	@Autowired
	private EmailSenderServiceImpl emailSenderService;

    @Mock
    private JavaMailSender javaMailSender;

    @Override
    public void initBeforeMethod() {
		super.initBeforeMethod();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMail() {
		doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
		Assert.assertTrue(emailSenderService.sendMail("t@t.t","title","content"));
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
