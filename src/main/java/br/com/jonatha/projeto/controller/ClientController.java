package br.com.jonatha.projeto.controller;

import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.dto.ClienteDTO;
import br.com.jonatha.projeto.dto.ClienteNewDTO;
import br.com.jonatha.projeto.service.ClientService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class ClientController {

  @Autowired
  ClientService service;

  @ApiOperation("Faz a busca por ID")
  @GetMapping("/{id}")
  public ResponseEntity<Client> find(@PathVariable Integer id) {
    Client client = service.find(id);
    return ResponseEntity.ok().body(client);
  }

  @ApiOperation("Inserir novo cliente")
  @PostMapping()
  public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
    Client obj = service.fromDTO(objDto);
    obj = service.insert(obj);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @ApiOperation("Atualiza cliente de acordo com ID")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto,
      @PathVariable Integer id) {
    Client obj = service.fromDTO(objDto);
    obj.setId(id);
    obj = service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Deleção do cliente por ID")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Client> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Lista todos clientes")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<ClienteDTO>> findAll() {
    List<Client> list = service.findAll();
    List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj))
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @ApiOperation("Realiza listagem com paginação")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/page")
  public ResponseEntity<Page<ClienteDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
    Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
    return ResponseEntity.ok().body(listDto);
  }

}
