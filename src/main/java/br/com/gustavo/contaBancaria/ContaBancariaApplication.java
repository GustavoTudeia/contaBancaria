package br.com.gustavo.contaBancaria;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.gustavo.contaBancaria.entity.Conta;
import br.com.gustavo.contaBancaria.entity.Pessoa;
import br.com.gustavo.contaBancaria.entity.Transacao;
import br.com.gustavo.contaBancaria.entity.enums.TipoConta;
import br.com.gustavo.contaBancaria.entity.enums.TipoTransacao;
import br.com.gustavo.contaBancaria.service.ContaService;
import br.com.gustavo.contaBancaria.service.PessoaService;
import br.com.gustavo.contaBancaria.service.TransacaoService;

@SpringBootApplication
@EnableJpaRepositories
public class ContaBancariaApplication {
	
	@Autowired
    private PessoaService pessoaRepository;
	
	@Autowired
	private ContaService contaRepository;
	
	@Autowired
	private TransacaoService transacaoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ContaBancariaApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner() {
		return args -> {
			Pessoa gustavo = new Pessoa("Gustavo Pereira", "gustavo@gmail.com", "04937959603",new Date(), new Date());
			
			this.pessoaRepository.insert(gustavo);
			
			List<Pessoa> pessoas = this.pessoaRepository.findAll();
            pessoas.forEach(pessoa -> {
            	System.out.println(pessoa);
            	Conta conta = new Conta(100.00, TipoConta.CONTACORRENTE.getDescricao(), new Date(), pessoa);
            	this.contaRepository.insert(conta);
            	
            	Transacao transacao = new Transacao(conta, 20.00, TipoTransacao.SAQUE.getDescricao(), new Date());
            	this.transacaoRepository.cadastrar(transacao);
            } );
            
            List<Transacao> ts = this.transacaoRepository.extratoPorPeriodo(2L, new Date(System.currentTimeMillis()-7*24*60*60*1000), new Date());
            System.out.println("Transacao encontradas " + ts.size());
            
            System.out.println("Pessoas encontradas " + pessoas.size());
            
		};
		
	}

}
