package br.com.gustavo.contaBancaria.entity.enums;

public enum TipoConta {

	CONTACORRENTE(1, "Conta Corrente"),
	CONTAPOUPANCA(2, "Conta Poupança");
	
	private int cod;
	private String descricao;

	TipoConta(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoConta toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoConta x : TipoConta.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Argumento Invalido " + cod);
	}
}
