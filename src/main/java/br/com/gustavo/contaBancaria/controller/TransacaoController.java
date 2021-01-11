package br.com.gustavo.contaBancaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavo.contaBancaria.entity.ExtratoParamVO;
import br.com.gustavo.contaBancaria.entity.Transacao;
import br.com.gustavo.contaBancaria.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

	@Autowired
	private TransacaoService  transacaoService;
	
	@PostMapping(path = "/extrato", consumes = "application/json", produces = "application/json")
	public List<Transacao> extrato(@RequestBody ExtratoParamVO vo){
		List<Transacao> list = transacaoService.extratoPorPeriodo(vo.getIdConta(), vo.getInicio(), vo.getFim());
		 return list;
	}
}
