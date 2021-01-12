package br.com.gustavo.contaBancaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavo.contaBancaria.entity.Conta;
import br.com.gustavo.contaBancaria.entity.Transacao;
import br.com.gustavo.contaBancaria.service.ContaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ContaEndpoint")
@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	private ContaService constaService;
	
	@ApiOperation(value = "Busca todas as Contas")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<Conta> findAll(){
		return this.constaService.findAll();
	}
	
	@ApiOperation(value = "Busca a Conta pelo id")
	@GetMapping(value = "/{id}",  consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta findById(@PathVariable("id") Long id) {
		return this.constaService.find(id);
	}
	
	@ApiOperation(value = "Cria uma Conta")
	@PostMapping(consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta create(@RequestBody Conta conta) {
		return this.constaService.insert(conta);
	}
	
	@ApiOperation(value = "Atualiza uma Conta")
	@PutMapping(value = "/{id}", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta update(@RequestBody Conta conta, @PathVariable("id") Long id) {
		return this.constaService.update(conta, id);
	}
	
	@ApiOperation(value = "Exclui uma Conta")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		this.constaService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Realiza deposito em uma Conta")
	@PostMapping(value = "/depositar", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta depositar(@RequestBody Transacao transacao) {
		return this.constaService.depositar(transacao);
	}
	
	@ApiOperation(value = "Realiza saque em uma Conta")
	@PostMapping(value = "/sacar", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta sacar(@RequestBody Transacao transacao) {
		return this.constaService.sacar(transacao);
	}
	
	@ApiOperation(value = "Realiza Tranferencia entre contas")
	@PostMapping(value = "/transferir", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta transferir(@RequestBody Transacao transacao) {
		return this.constaService.transferir(transacao);
	}
	
}
