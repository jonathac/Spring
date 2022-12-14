package br.com.jonatha.projeto.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Table;

@Entity
@Table(name="PEDIDO")
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private Date instantDate;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
  private Payment payment;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "endereco_de_entrega_id")
  private Address address;

  @OneToMany(mappedBy = "id.order")
  private Set<ItemOrder> itens = new HashSet<>();

  public Order() {
  }

  public Order(Integer id, Date instantDate, Client client, Address address) {
    super();
    this.id = id;
    this.instantDate = instantDate;
    this.client = client;
    this.address = address;
  }

  //IR PARA CLASSE SERVICE
  public double getValorTotal() {
    double soma = 0.0;
    for (ItemOrder ip : itens) {
      soma = soma + ip.getSubTotal();
    }
    return soma;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getInstantDate() {
    return instantDate;
  }

  public void setInstantDate(Date instantDate) {
    this.instantDate = instantDate;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
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
    Order other = (Order) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    StringBuilder builder = new StringBuilder();
    builder.append("Pedido número: ");
    builder.append(getId());
    builder.append(", Instante: ");
    builder.append(sdf.format(getInstantDate()));
    builder.append(", Cliente: ");
    builder.append(getClient().getName());
    builder.append(", Situação do pagamento: ");
    builder.append(getPayment().getEstado().getDescricao());
    builder.append("\nDetalhes:\n");
    for (ItemOrder ip : getItens()) {
      builder.append(ip.toString());
    }
    builder.append("Valor total: ");
    builder.append(nf.format(getValorTotal()));
    return builder.toString();
  }

}
