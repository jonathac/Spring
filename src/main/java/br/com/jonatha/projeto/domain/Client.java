package br.com.jonatha.projeto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.jonatha.projeto.domain.enums.Perfil;
import br.com.jonatha.projeto.domain.enums.TipoCliente;

@Entity
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  @Column(unique = true)
  private String email;
  private String cpfOuCnpj;
  private Integer type;

  @JsonIgnore
  private String password;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Address> address = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "TELEFONE")
  private Set<String> phoneNumber = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private Set<Integer> profile = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "client")
  private List<Order> order = new ArrayList<>();

  private Client() {
    addProfile(Perfil.CLIENTE);
  }

  public Client(Integer id, String name, String email, String cpfOuCnpj, TipoCliente type,
      String password) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.cpfOuCnpj = cpfOuCnpj;
    this.type = (type == null) ? null : type.getCod();
    this.password = password;
    addProfile(Perfil.CLIENTE);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCpfOuCnpj() {
    return cpfOuCnpj;
  }

  public void setCpfOuCnpj(String cpfOuCnpj) {
    this.cpfOuCnpj = cpfOuCnpj;
  }

  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = address;
  }

  public Set<String> getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Set<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Set<Perfil> getProfile() {
    return profile.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
  }

  public void addProfile(Perfil profile) {
    this.profile.add(profile.getCod());
  }

  public TipoCliente getType() {
    return TipoCliente.toEnum(type);
  }

  public void setType(TipoCliente type) {
    this.type = type.getCod();
  }

  public List<Order> getOrder() {
    return order;
  }

  public void setOrder(List<Order> order) {
    this.order = order;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
    Client other = (Client) obj;
    return Objects.equals(id, other.id);
  }
}
