package br.com.jonatha.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.Category;
import br.com.jonatha.projeto.domain.Product;
import br.com.jonatha.projeto.repositories.CategoryRepository;
import br.com.jonatha.projeto.repositories.ProductRepository;
import br.com.jonatha.projeto.service.exceptions.ObjectNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product find(Integer id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}

	@Override
	public Page<Product> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage,
			String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categories, pageRequest);
	}
}
