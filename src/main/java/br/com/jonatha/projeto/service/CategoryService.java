package br.com.jonatha.projeto.service;

import br.com.jonatha.projeto.domain.Category;
import br.com.jonatha.projeto.dto.CategoriaDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CategoryService {

  Category find(Integer id);

  Category insert(Category obj);

  Category update(Category obj);

  void delete(Integer id);

  List<Category> findAll();

  Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

  Category fromDTO(CategoriaDTO objDto);

}
