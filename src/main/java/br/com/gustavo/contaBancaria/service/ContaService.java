package br.com.gustavo.contaBancaria.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gustavo.contaBancaria.entity.Conta;
import br.com.gustavo.contaBancaria.entity.Transacao;
import br.com.gustavo.contaBancaria.entity.enums.TipoTransacao;
import br.com.gustavo.contaBancaria.repository.ContaRepository;
import br.com.gustavo.contaBancaria.repository.TransacaoRepository;
import br.com.gustavo.contaBancaria.service.exceptions.DataIntegrityException;
import br.com.gustavo.contaBancaria.service.exceptions.ResourceNotFoundException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta find(Long id) {
		Optional<Conta> conta = contaRepository.findById(id);
		return conta.orElseThrow(()-> new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
	}	

	@Transactional
	public Conta insert(Conta conta) {
		conta = contaRepository.save(conta);
		return conta;
	}

	public Conta update(Conta obj) {
		Conta newObj = find(obj.getId());
		updateData(newObj, obj);
		return contaRepository.save(newObj);
	}
	
	public Conta update(Conta obj, Long id) {
		Conta newObj = find(id);
		updateData(newObj, obj);
		return contaRepository.save(newObj);
	}

	private void updateData(Conta newObj, Conta obj) {
		newObj.setSaldo(obj.getSaldo());
		
		if(obj.getTipo() != null) {
			newObj.setTipo(obj.getTipo());
		}
		
		if(obj.getPessoa() != null) {
			newObj.setPessoa(obj.getPessoa());
		}
		
	}

	public void delete(Long id) {
		find(id);
		try {
			contaRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não possível excluir conta com transacao(s) associado(s)! ", e.getCause());
		}
		
	}

	public List<Conta> findAll() {
		return contaRepository.findAll();
	}
	
	@Transactional
	public Conta sacar(Transacao transacao) {
		Conta conta = find(transacao != null && transacao.getConta() != null ? transacao.getConta().getId() : null);
		
		if(conta != null && conta.getSaldo() >= transacao.getValor()) {
			conta.setSaldo(conta.getSaldo() - transacao.getValor());
		}else {
			throw new ResourceNotFoundException("Saldo Insuficiente!");
		}
		
		Conta newObj = update(conta);
		
		transacao.setTipoTransacao(TipoTransacao.SAQUE.getDescricao());
		transacao.setDataTransacao(new Date());
		
		CadastraTransacao(transacao);
		
		return newObj;
	}
	
	@Transactional
	public Conta transferir(Transacao transacao) {
		Conta contaOrigem = find(transacao != null && transacao.getConta() != null ? transacao.getConta().getId() : null);
		
		Conta contaDestino;
		
		if(contaOrigem != null && contaOrigem.getSaldo() >= transacao.getValor()) {
			contaDestino = find(transacao != null && transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : null);
			
			//Subtrai da conta Origem
			contaOrigem.setSaldo(contaOrigem.getSaldo() - transacao.getValor());
			
			contaOrigem = update(contaOrigem);
			
			transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA.getDescricao());
			transacao.setDataTransacao(new Date());
			
			CadastraTransacao(transacao);
			
			//Adiciona valor na conta Destino
			contaDestino.setSaldo(contaDestino.getSaldo() + transacao.getValor());
			
			contaDestino = update(contaDestino);
			
			transacao.setTipoTransacao(TipoTransacao.DEPOSITO.getDescricao());
			transacao.setDataTransacao(new Date());
			
			CadastraTransacao(transacao);
		}else {
			throw new ResourceNotFoundException("Saldo Insuficiente!");
		}
		
		return contaOrigem;
	}
	
	@Transactional
	public Conta depositar(Transacao transacao) {
		Conta conta = find(transacao != null && transacao.getConta() != null ? transacao.getConta().getId() : null);
		
		conta.setSaldo(conta.getSaldo() + transacao.getValor());
		
		update(conta);
		
		transacao.setTipoTransacao(TipoTransacao.DEPOSITO.getDescricao());
		transacao.setDataTransacao(new Date());
		
		CadastraTransacao(transacao);
		
		return conta;
	}
	
	private void CadastraTransacao(Transacao transacao) {
		transacaoRepository.save(transacao);
	}

}
