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
			throw new DataIntegrityException("Não possível excluir cliente com pedido(s) associado(s)! ", e.getCause());
		}
		
	}

	public List<Conta> findAll() {
		return contaRepository.findAll();
	}
	
	@Transactional
	public Conta sacar(Long id , double valor) {
		Conta conta = find(id);
		
		if(conta != null && conta.getSaldo() >= valor) {
			conta.setSaldo(conta.getSaldo() - valor);
		}else {
			throw new ResourceNotFoundException("Saldo Insuficiente!");
		}
		
		Conta newObj = update(conta);
		
		CadastraTransacao(new Transacao(newObj, valor, TipoTransacao.SAQUE.getDescricao(), new Date()));
		
		return newObj;
	}
	
	@Transactional
	public Conta Transferir(Long idContaOrigem, Long idContaDestino , double valor) {
		Conta contaOrigem = find(idContaOrigem);
		
		Conta contaDestino;
		
		if(contaOrigem != null && contaOrigem.getSaldo() >= valor) {
			contaDestino = find(idContaDestino);
			
			//Subtrai da conta Origem
			contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
			
			contaOrigem = update(contaOrigem);
			
			CadastraTransacao(new Transacao(contaOrigem, valor, TipoTransacao.TRANSFERENCIA.getDescricao(), new Date()));
			
			//Adiciona valor na conta Destino
			contaDestino.setSaldo(contaDestino.getSaldo() + valor);
			
			contaDestino = update(contaDestino);
			
			CadastraTransacao(new Transacao(contaDestino, valor, TipoTransacao.DEPOSITO.getDescricao(), new Date()));
		}else {
			throw new ResourceNotFoundException("Saldo Insuficiente!");
		}
		
		return contaOrigem;
	}
	
	@Transactional
	public Conta depositar(Long id, double valor) {
		Conta conta = find(id);
		
		conta.setSaldo(conta.getSaldo() + valor);
		
		update(conta);
		
		CadastraTransacao(new Transacao(conta, valor, TipoTransacao.DEPOSITO.getDescricao(), new Date()));
		
		return conta;
	}
	
	private void CadastraTransacao(Transacao transacao) {
		transacaoRepository.save(transacao);
	}

}
