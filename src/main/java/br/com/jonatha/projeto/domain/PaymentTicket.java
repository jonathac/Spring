package br.com.jonatha.projeto.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.jonatha.projeto.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PaymentTicket extends Payment {

  private static final long serialVersionUID = 1L;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataVencimento;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPagamento;

  public PaymentTicket() {
  }

  public PaymentTicket(Integer id, EstadoPagamento estado, Order order, Date dataVencimento,
      Date dataPagamento) {
    super(id, estado, order);
    this.dataPagamento = dataPagamento;
    this.dataVencimento = dataVencimento;

  }

  public Date getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(Date dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public Date getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }


}
