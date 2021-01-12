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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "PessoaEndPoint")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@ApiOperation(value = "Busca todas as Pessoas")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<Pessoa> findAll(){
		return pessoaService.findAll();
	}
	
	@ApiOperation(value = "Busca uma Pessoa pelo id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa findById(@PathVariable("id") String id) {
		return pessoaService.find(Long.parseLong(id));
	}
	
	@ApiOperation(value = "Cadastra uma Pessoa")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa create(@Valid @RequestBody Pessoa pessoa) {
		return pessoaService.insert(pessoa);
	}
	
	@ApiOperation(value = "Atualiza dados de Pessoa")
	@PutMapping(value = "/{id}",  produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Pessoa update(@RequestBody Pessoa pessoa, @PathVariable("id") Long id) {
		return pessoaService.update(pessoa, id);
	}
	
	@ApiOperation(value = "Exclui Pessoa")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		pessoaService.delete(Long.parseLong(id));
		return ResponseEntity.ok().build();
	}
}
