package br.com.jonatha.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jonatha.projeto.domain.City;
import br.com.jonatha.projeto.domain.Client;
import br.com.jonatha.projeto.domain.Address;
import br.com.jonatha.projeto.domain.enums.Perfil;
import br.com.jonatha.projeto.domain.enums.TipoCliente;
import br.com.jonatha.projeto.dto.ClienteDTO;
import br.com.jonatha.projeto.dto.ClienteNewDTO;
import br.com.jonatha.projeto.repositories.ClientRepository;
import br.com.jonatha.projeto.repositories.AddressRepository;
import br.com.jonatha.projeto.security.UserSS;
import br.com.jonatha.projeto.service.exceptions.AuthorizationException;
import br.com.jonatha.projeto.service.exceptions.DataIntegratyException;
import br.com.jonatha.projeto.service.exceptions.ObjectNotFoundException;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  BCryptPasswordEncoder pe;

  @Autowired
  ClientRepository repo;

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public Client find(Integer id) {

    UserSS user = UserService.authenticated();
    if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
      throw new AuthorizationException("Acesso negado");
    }

    Optional<Client> client = repo.findById(id);

    return client.orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
  }

  @Transactional
  @Override
  public Client insert(Client obj) {
    obj.setId(null);
    obj = repo.save(obj);
    addressRepository.saveAll(obj.getAddress());
    return obj;
  }

  @Override
  public Client update(Client obj) {
    Client newObj = find(obj.getId());
    updateData(newObj, obj);
    return repo.save(newObj);
  }

  @Override
  public void delete(Integer id) {
    find(id);
    try {
      repo.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegratyException("Não é possível excluir porque há pedidos relacionadas");
    }
  }

  @Override

  public List<Client> findAll() {
    return repo.findAll();
  }

  @Override
  public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy,
      String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
        orderBy);
    return repo.findAll(pageRequest);
  }

  @Override
  public Client fromDTO(ClienteDTO objDto) {
    return new Client(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
  }

  @Override
  public Client fromDTO(ClienteNewDTO objDto) {
    Client cli = new Client(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
        TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
    City cid = new City(objDto.getCidadeId(), null, null);
    Address end = new Address(null, objDto.getLogradouro(), objDto.getNumero(),
        objDto.getComplemento(),
        objDto.getBairro(), objDto.getCep(), cli, cid);
    cli.getAddress().add(end);
    cli.getPhoneNumber().add(objDto.getTelefone1());
    if (objDto.getTelefone2() != null) {
      cli.getPhoneNumber().add(objDto.getTelefone2());
    }
    if (objDto.getTelefone3() != null) {
      cli.getPhoneNumber().add(objDto.getTelefone3());
    }
    return cli;
  }

  private void updateData(Client newObj, Client obj) {
    newObj.setName(obj.getName());
    newObj.setEmail(obj.getEmail());
  }

}
