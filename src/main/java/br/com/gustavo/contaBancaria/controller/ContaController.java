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

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	private ContaService constaService;
	
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<Conta> findAll(){
		return this.constaService.findAll();
	}
	
	@GetMapping(value = "/{id}",  consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta findById(@PathVariable("id") Long id) {
		return this.constaService.find(id);
	}
	
	@PostMapping(consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta create(@RequestBody Conta conta) {
		return this.constaService.insert(conta);
	}
	
	@PutMapping(value = "/{id}", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta update(@RequestBody Conta conta, @PathVariable("id") Long id) {
		return this.constaService.update(conta, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		this.constaService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/depositar", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta depositar(@RequestBody Transacao transacao) {
		return this.constaService.depositar(transacao);
	}
	
	@PostMapping(value = "/sacar", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta sacar(@RequestBody Transacao transacao) {
		return this.constaService.sacar(transacao);
	}
	
	@PostMapping(value = "/transferir", consumes = { "application/json", "application/xml", "application/x-yaml" },
			produces = { "application/json", "application/xml", "application/x-yaml" })
	public Conta transferir(@RequestBody Transacao transacao) {
		return this.constaService.transferir(transacao);
	}
	
}
