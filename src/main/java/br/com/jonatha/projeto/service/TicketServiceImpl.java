package br.com.jonatha.projeto.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.PaymentTicket;

@Service
public class TicketServiceImpl {

	public void preencherPagamentoComBoleto(PaymentTicket pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}