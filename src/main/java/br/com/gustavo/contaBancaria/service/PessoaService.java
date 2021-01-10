package br.com.gustavo.contaBancaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.gustavo.contaBancaria.entity.Pessoa;
import br.com.gustavo.contaBancaria.repository.PessoaRepository;
import br.com.gustavo.contaBancaria.service.exceptions.DataIntegrityException;
import br.com.gustavo.contaBancaria.service.exceptions.ResourceNotFoundException;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaReposytory;
	
	
	public Pessoa find(Long id) {
		Optional<Pessoa> pessoa = pessoaReposytory.findById(id);
		return pessoa.orElseThrow(()-> new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
	}	

	public Pessoa insert(Pessoa pessoa) {
		validarCpfEmailDuplicado(pessoa);
		pessoa = pessoaReposytory.save(pessoa);
		return pessoa;
	}

	public Pessoa update(Pessoa obj) {
		Pessoa newObj = find(obj.getId());
		updateData(newObj, obj);
		return pessoaReposytory.save(newObj);
	}

	private void updateData(Pessoa newObj, Pessoa obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpf(obj.getCpf());
		newObj.setDataNascimento(obj.getDataNascimento());
	}

	public void delete(Long id) {
		find(id);
		try {
			pessoaReposytory.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não possível excluir Pessoa com conta(s) associada(s)! ", e.getCause());
		}
		
	}

	public List<Pessoa> findAll() {
		return pessoaReposytory.findAll();
	}
	
	private  void validarCpfEmailDuplicado(Pessoa pessoa) {
		
		Pessoa ps = pessoaReposytory.findByCpf(pessoa.getCpf());
		
		if(ps != null) {
			throw new ResourceNotFoundException("CPF ja cadastrado!");
		}
		 
		ps = pessoaReposytory.findByEmail(pessoa.getEmail());
		
		if(ps != null) {
			throw new ResourceNotFoundException("Email ja cadastrado!");
		}
		
	}
}
