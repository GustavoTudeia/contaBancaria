package br.com.gustavo.contaBancaria.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExtratoParamVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "O id da Conta Obrigatorio!")
	private Long idConta;
	
	@NotNull(message = "Data inicio Obrigatorio!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", locale = "pt-BR", timezone = "UTC-03")
	private Date inicio;
	
	@NotNull(message = "Data fim Obrigatorio!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", locale = "pt-BR", timezone = "UTC-03")
	private Date fim;
	
	public Long getIdConta() {
		return idConta;
	}
	
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	
	public Date getInicio() {
		return inicio;
	}
	
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public Date getFim() {
		return fim;
	}
	
	public void setFim(Date fim) {
		this.fim = fim;
	}
}
