package br.com.jonatha.projeto.service;

import br.com.jonatha.projeto.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductService {

  Product find(Integer id);

  Page<Product> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage,
      String orderBy, String direction);
}
