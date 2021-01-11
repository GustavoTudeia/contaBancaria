package br.com.gustavo.contaBancaria.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gustavo.contaBancaria.entity.Transacao;
import br.com.gustavo.contaBancaria.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public List<Transacao> extratoPorPeriodo(Long idConta, Date inicio, Date fim){
		List<Transacao> retorno = transacaoRepository.extratoPorPeriodo(idConta, inicio, fim);
		return retorno;
	}

	@Transactional
	public Transacao cadastrar(Transacao transacao) {
		return this.transacaoRepository.save(transacao);
	}
}
