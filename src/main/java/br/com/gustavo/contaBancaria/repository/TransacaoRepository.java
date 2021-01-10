package br.com.gustavo.contaBancaria.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gustavo.contaBancaria.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	@Query(value = "SELECT t FROM Transacao t t.conta.id = :idConta and t.dataCadastro BETWEEN :inicio AND :fim")
	List<Transacao> extratoPorPeriodo(@Param("idConta") Long idConta, @Param("inicio") Date inicio, @Param("fim") Date fim);

}
