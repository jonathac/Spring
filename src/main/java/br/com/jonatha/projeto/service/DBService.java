package br.com.jonatha.projeto.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.Category;
import br.com.jonatha.projeto.domain.City;
import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.domain.Address;
import br.com.jonatha.projeto.domain.Stat;
import br.com.jonatha.projeto.domain.ItemOrder;
import br.com.jonatha.projeto.domain.Payment;
import br.com.jonatha.projeto.domain.PaymentTicket;
import br.com.jonatha.projeto.domain.PaymentCard;
import br.com.jonatha.projeto.domain.Order;
import br.com.jonatha.projeto.domain.Product;
import br.com.jonatha.projeto.domain.enums.EstadoPagamento;
import br.com.jonatha.projeto.domain.enums.Perfil;
import br.com.jonatha.projeto.domain.enums.TipoCliente;
import br.com.jonatha.projeto.repositories.CategoryRepository;
import br.com.jonatha.projeto.repositories.CityRepository;
import br.com.jonatha.projeto.repositories.ClientRepository;
import br.com.jonatha.projeto.repositories.AddressRepository;
import br.com.jonatha.projeto.repositories.StatRepository;
import br.com.jonatha.projeto.repositories.ItemOrderRepository;
import br.com.jonatha.projeto.repositories.PaymentRepository;
import br.com.jonatha.projeto.repositories.OrderRepository;
import br.com.jonatha.projeto.repositories.ProductRepository;

@Service
public class DBService {

	@Autowired
	BCryptPasswordEncoder pe;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StatRepository statRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public void instantiateTestDatabase() throws ParseException {

		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		p1.getcategories().addAll(Arrays.asList(cat1, cat4));
		p2.getcategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getcategories().addAll(Arrays.asList(cat1, cat4));
		p4.getcategories().addAll(Arrays.asList(cat2));
		p5.getcategories().addAll(Arrays.asList(cat3));
		p6.getcategories().addAll(Arrays.asList(cat3));
		p7.getcategories().addAll(Arrays.asList(cat4));
		p8.getcategories().addAll(Arrays.asList(cat5));
		p9.getcategories().addAll(Arrays.asList(cat6));
		p10.getcategories().addAll(Arrays.asList(cat6));
		p11.getcategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Stat est1 = new Stat(null, "Minas Gerais");
		Stat est2 = new Stat(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", est1);
		City c2 = new City(null, "São Paulo", est2);
		City c3 = new City(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		statRepository.saveAll(Arrays.asList(est1, est2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "jonathacassio@gmail.com", "36378912377", TipoCliente.PESSOAFISICA,pe.encode("123"));

		cli1.getPhoneNumber().addAll(Arrays.asList("27363323", "93838393"));

		Client cli2 = new Client(null, "Ana Costa", "email@gmail.com", "31628382740", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.getPhoneNumber().addAll(Arrays.asList("93883321", "34252625"));
		cli2.addProfile(Perfil.ADMIN);
		
		Address e1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address e2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Address e3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "281777012", cli2, c2);

		cli1.getAddress().addAll(Arrays.asList(e1, e2));
		cli2.getAddress().addAll(Arrays.asList(e3));

		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		addressRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Order ped1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Order ped2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Payment pagto1 = new PaymentCard(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPayment(pagto1);

		Payment pagto2 = new PaymentTicket(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPayment(pagto2);

		cli1.getOrder().addAll(Arrays.asList(ped1, ped2));

		orderRepository.saveAll(Arrays.asList(ped1, ped2));
		paymentRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemOrder ip1 = new ItemOrder(ped1, p1, 0.00, 1, 2000.00);
		ItemOrder ip2 = new ItemOrder(ped1, p3, 0.00, 2, 80.00);
		ItemOrder ip3 = new ItemOrder(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemOrderRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}