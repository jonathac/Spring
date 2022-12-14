package br.com.jonatha.projeto.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @EmbeddedId
  private ItemOrderPK id = new ItemOrderPK();

  private Double discount;
  private Integer quantity;
  private Double price;

  private ItemOrder() {
  }

  public ItemOrder(Order order, Product product, Double discount, Integer quantity, Double price) {
    super();
    id.setPedido(order);
    id.setProduto(product);
    this.discount = discount;
    this.quantity = quantity;
    this.price = price;
  }

  public double getSubTotal() {
    return (price - discount) * quantity;
  }

  @JsonIgnore
  public Order getOrder() {
    return id.getPedido();
  }

  public void setPedido(Order order) {
    id.setPedido(order);
  }

  public Product getProduct() {
    return id.getProduto();
  }

  public void setProduct(Product product) {
    id.setProduto(product);
  }

  public ItemOrderPK getId() {
    return id;
  }

  public void setId(ItemOrderPK id) {
    this.id = id;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
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
    ItemOrder other = (ItemOrder) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    StringBuilder builder = new StringBuilder();
    builder.append(getProduct().getName());
    builder.append(", Qte: ");
    builder.append(getQuantity());
    builder.append(", Preço unitário: ");
    builder.append(nf.format(getPrice()));
    builder.append(", Subtotal: ");
    builder.append(nf.format(getSubTotal()));
    builder.append("\n");
    return builder.toString();
  }

}
