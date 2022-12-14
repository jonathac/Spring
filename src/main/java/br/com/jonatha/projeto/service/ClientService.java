package br.com.jonatha.projeto.service;

import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.dto.ClienteDTO;
import br.com.jonatha.projeto.dto.ClienteNewDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ClientService {

  Client find(Integer id);

  Client insert(Client obj);

  Client update(Client obj);

  void delete(Integer id);

  List<Client> findAll();

  Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy,
      String direction);

  Client fromDTO(ClienteDTO objDto);

  Client fromDTO(ClienteNewDTO objDto);
}
