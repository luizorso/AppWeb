package br.com.appweb.model;

public class Produto {
	private Integer idProduto;
	private String nome;
	private Double preco;
	private Integer estoque;
	private Integer estoqueMin;
	private Integer estoqueMax;
	private Categoria categoria;
	
	public Produto() {
		
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Integer getEstoqueMin() {
		return estoqueMin;
	}

	public void setEstoqueMin(Integer estoqueMin) {
		this.estoqueMin = estoqueMin;
	}

	public Integer getEstoqueMax() {
		return estoqueMax;
	}

	public void setEstoqueMax(Integer estoqueMax) {
		this.estoqueMax = estoqueMax;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public String toString() {
		return "Código: " + this.getIdProduto() + 
				"\nNome: " + this.getNome() +
				"\nPreço: " + this.getPreco() +
				"\nEstoque: " + this.getEstoque() +
				"\nEstoque Mínimo: " + this.getEstoqueMin() +
				"\nEstoque Máximo: " + this.getEstoqueMax() +
				"\nCategoria: " + this.getCategoria().getNome();
	}

}
