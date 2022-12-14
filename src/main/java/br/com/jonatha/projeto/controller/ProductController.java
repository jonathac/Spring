package br.com.jonatha.projeto.controller;

import br.com.jonatha.projeto.controller.utils.URL;
import br.com.jonatha.projeto.domain.Product;
import br.com.jonatha.projeto.dto.ProdutoDTO;
import br.com.jonatha.projeto.service.ProductService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProductController {

  @Autowired
  private ProductService service;

  @ApiOperation("Realiza busca por ID")
  @GetMapping("/{id}")
  public ResponseEntity<Product> find(@PathVariable Integer id) {
    Product obj = service.find(id);
    return ResponseEntity.ok().body(obj);
  }

  @ApiOperation("Realiza busca dos pedidos com paginação")
  @GetMapping
  public ResponseEntity<Page<ProdutoDTO>> findPage(
      @RequestParam(value = "nome", defaultValue = "") String nome,
      @RequestParam(value = "categorias", defaultValue = "") String categorias,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    String nomeDecoded = URL.decodeParam(nome);
    List<Integer> ids = URL.decodeIntList(categorias);
    Page<Product> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
    Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
    return ResponseEntity.ok().body(listDto);
  }

}
