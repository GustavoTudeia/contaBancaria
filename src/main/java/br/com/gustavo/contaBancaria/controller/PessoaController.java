package br.com.gustavo.contaBancaria.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.com.gustavo.contaBancaria.entity.Pessoa;
import br.com.gustavo.contaBancaria.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<Pessoa> findAll(){
		return pessoaService.findAll();
	}
	
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa findById(@PathVariable("id") String id) {
		return pessoaService.find(Long.parseLong(id));
	}
	
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa create(@Valid @RequestBody Pessoa pessoa) {
		return pessoaService.insert(pessoa);
	}
	
	@PutMapping(value = "/{id}",  produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa update(@RequestBody Pessoa pessoa, @PathVariable("id") Long id) {
		return pessoaService.update(pessoa, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		pessoaService.delete(Long.parseLong(id));
		return ResponseEntity.ok().build();
	}
}
