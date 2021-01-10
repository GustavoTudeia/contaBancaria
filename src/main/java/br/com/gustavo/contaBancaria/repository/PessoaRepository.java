package br.com.gustavo.contaBancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.gustavo.contaBancaria.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Transactional(readOnly = true)
	Pessoa findByEmail(String email);
	
	@Transactional(readOnly = true)
	Pessoa findByCpf(String cpf);
}
