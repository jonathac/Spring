package br.com.jonatha.projeto.controller;

import br.com.jonatha.projeto.domain.Order;
import br.com.jonatha.projeto.service.OrderService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

  @Autowired
  OrderService orderService;

  @ApiOperation("Realiza busca por ID")
  @GetMapping("/{id}")
  public ResponseEntity<Order> find(@PathVariable Integer id) {
    Order order = orderService.find(id);
    return ResponseEntity.ok().body(order);
  }

  @ApiOperation("Realiza inserimento de um novo pedido")
  @PostMapping()
  public ResponseEntity<Void> insert(@Valid @RequestBody Order obj) {
    obj = orderService.insert(obj);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @ApiOperation("Realiza busca dos pedidos cadastrados com paginação")
  @GetMapping()
  public ResponseEntity<Page<Order>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "instanteDate") String orderBy,
      @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
    Page<Order> list = orderService.findPage(page, linesPerPage, orderBy, direction);
    return ResponseEntity.ok().body(list);
  }

}
