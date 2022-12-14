package br.com.jonatha.projeto.service;

import br.com.jonatha.projeto.domain.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

  Order find(Integer id);

  Order insert(Order obj);

  Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

}
