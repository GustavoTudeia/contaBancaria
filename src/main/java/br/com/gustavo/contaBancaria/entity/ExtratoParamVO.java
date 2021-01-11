package br.com.gustavo.contaBancaria.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExtratoParamVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idConta;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date inicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
