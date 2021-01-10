package br.com.gustavo.contaBancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.contaBancaria.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
