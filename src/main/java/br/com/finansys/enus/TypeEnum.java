package br.com.finansys.enus;

public enum TypeEnum {
    EXPENSE(1, "Despesa"),
    REVENUE(2, "Receita");

    private int cod;
	private String descricao;
	
	private TypeEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static TypeEnum toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (TypeEnum x : TypeEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}