package br.com.jonatha.projeto.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.jonatha.projeto.domain.Cliente;
import br.com.jonatha.projeto.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
