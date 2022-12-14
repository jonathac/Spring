package br.com.jonatha.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.Category;
import br.com.jonatha.projeto.dto.CategoriaDTO;
import br.com.jonatha.projeto.repositories.CategoryRepository;
import br.com.jonatha.projeto.service.exceptions.DataIntegratyException;
import br.com.jonatha.projeto.service.exceptions.ObjectNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  @Override

  public Category find(Integer id) {
    Optional<Category> categoria = categoryRepository.findById(id);

    return categoria.orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
  }

  @Override
  public Category insert(Category obj) {
    obj.setId(null);
    return categoryRepository.save(obj);
  }

  @Override
  public Category update(Category obj) {
    Category newObj = find(obj.getId());
    updateData(newObj, obj);
    return categoryRepository.save(newObj);
  }

  @Override
  public void delete(Integer id) {
    find(id);

    try {
      categoryRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegratyException("Não é possível excluir uma categoria que possui produtos");
    }

  }

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy,
      String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
        orderBy);
    return categoryRepository.findAll(pageRequest);
  }

  @Override
  public Category fromDTO(CategoriaDTO objDto) {
    return new Category(objDto.getId(), objDto.getNome());
  }

  private void updateData(Category newObj, Category obj) {
    newObj.setName(obj.getName());
  }
}
