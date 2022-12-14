package br.com.jonatha.projeto.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.jonatha.projeto.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PaymentCard extends Payment {

  private static final long serialVersionUID = 1L;

  private Integer numeroDeParcelas;

  public PaymentCard() {
  }

  public PaymentCard(Integer id, EstadoPagamento estado, Order order, Integer numeroDeParcelas) {
    super(id, estado, order);
    this.numeroDeParcelas = numeroDeParcelas;
  }

  public Integer getNumeroDeParcelas() {
    return numeroDeParcelas;
  }

  public void setNumeroDeParcelas(Integer numeroDeParcelas) {
    this.numeroDeParcelas = numeroDeParcelas;
  }


}
