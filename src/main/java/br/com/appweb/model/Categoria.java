package br.com.appweb.model;

public class Categoria {
	
	private Integer idCategoria;
	private String nome;
	
	public Categoria() {
		
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Código: " + this.getIdCategoria() + " Nome: " + this.getNome();
	}
	
	

	
}
