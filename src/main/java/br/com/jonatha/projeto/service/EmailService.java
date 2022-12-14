package br.com.jonatha.projeto.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order obj);

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Client client, String newPass);
}
