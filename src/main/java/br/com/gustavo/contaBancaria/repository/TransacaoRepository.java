package br.com.gustavo.contaBancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.contaBancaria.entity.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

}
