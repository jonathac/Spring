package br.com.jonatha.projeto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Double price;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "PRODUTO_CATEGORIA",
      joinColumns = @JoinColumn(name = "produto_id"),
      inverseJoinColumns = @JoinColumn(name = "categoria_id"))
  private List<Category> categories = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "id.product")
  private Set<ItemOrder> itens = new HashSet<>();

  public Product() {
  }

  public Product(Integer id, String name, Double price) {
    super();
    this.id = id;
    this.name = name;
    this.price = price;
  }

  @JsonIgnore
  public List<Order> getOrder() {
    List<Order> list = new ArrayList<>();
    for (ItemOrder x : itens) {
      list.add(x.getOrder());
    }
    return list;
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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public List<Category> getcategories() {
    return categories;
  }

  public void setcategories(List<Category> categories) {
    this.categories = categories;
  }

  public Set<ItemOrder> getItens() {
    return itens;
  }

  public void setItens(Set<ItemOrder> itens) {
    this.itens = itens;
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
    Product other = (Product) obj;
    return Objects.equals(id, other.id);
  }


}
