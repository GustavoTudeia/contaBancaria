package br.com.gustavo.contaBancaria.entity.enums;

public enum TipoTransacao {
	
	DEPOSITO(1, "Deposito"),
	SAQUE(2, "Saque"),
	TRANSFERENCIA(1, "Transferencia"),;
	
	private int cod;
	private String descricao;

	TipoTransacao(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoTransacao toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoTransacao x : TipoTransacao.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Argumento Invalido " + cod);
	}
}
