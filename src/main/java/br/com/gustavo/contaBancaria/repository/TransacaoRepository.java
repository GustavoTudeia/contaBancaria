package br.com.gustavo.contaBancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gustavo.contaBancaria.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

}
