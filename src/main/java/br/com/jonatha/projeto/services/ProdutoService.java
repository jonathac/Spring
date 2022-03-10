package br.com.jonatha.projeto.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jonatha.projeto.domain.Produto;
import br.com.jonatha.projeto.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository ProdutoRepository;
	
	public Produto buscar(Integer Id) {
		Optional<Produto> Produto = ProdutoRepository.findById(Id);
		return Produto.get();
	}
	
}
