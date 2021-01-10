package br.com.gustavo.contaBancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gustavo.contaBancaria.entity.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

}
