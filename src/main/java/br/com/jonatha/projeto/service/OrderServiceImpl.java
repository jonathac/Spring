package br.com.jonatha.projeto.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.domain.ItemOrder;
import br.com.jonatha.projeto.domain.PaymentTicket;
import br.com.jonatha.projeto.domain.Order;
import br.com.jonatha.projeto.domain.enums.EstadoPagamento;
import br.com.jonatha.projeto.repositories.ItemOrderRepository;
import br.com.jonatha.projeto.repositories.PaymentRepository;
import br.com.jonatha.projeto.repositories.OrderRepository;
import br.com.jonatha.projeto.security.UserSS;
import br.com.jonatha.projeto.service.exceptions.AuthorizationException;
import br.com.jonatha.projeto.service.exceptions.ObjectNotFoundException;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderRepository repo;

	@Autowired
	private TicketServiceImpl ticketServiceImpl;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Autowired
	private ClientServiceImpl clientServiceImpl;
	
	@Autowired
	private EmailService emailService;

	@Override
	public Order find(Integer id) {
		Optional<Order> pedido = repo.findById(id);
		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}

	@Override
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstantDate(new Date());
		obj.setClient(clientServiceImpl.find(obj.getClient().getId()));
		obj.getPayment().setEstado(EstadoPagamento.PENDENTE);
		obj.getPayment().setPedido(obj);
		if (obj.getPayment() instanceof PaymentTicket) {
			PaymentTicket pagto = (PaymentTicket) obj.getPayment();
			ticketServiceImpl.preencherPagamentoComBoleto(pagto, obj.getInstantDate());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		for (ItemOrder ip : obj.getItens()) {
			ip.setDiscount(0.0);
			ip.setProduct(productServiceImpl.find(ip.getProduct().getId()));
			ip.setPrice(ip.getProduct().getPrice());
			ip.setPedido(obj);
		}
		itemOrderRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}

	@Override
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Client client =  clientServiceImpl.find(user.getId());
		return repo.findByClient(client, pageRequest);
	}

}
