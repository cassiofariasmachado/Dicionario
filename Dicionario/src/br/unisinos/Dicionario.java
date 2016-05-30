package br.unisinos;

import java.util.LinkedList;

public class Dicionario implements Comparable<Dicionario> {
	private String palavra; //Palavra em ingl�s
	private LinkedList<String> definicoes; //Tradu��es/defini��es em portugu�s
	
	/**
	 * Construtor: Cria um novo objeto Dicionario.
	 * @param palavra String que ser� a palavra buscada no dicion�rio.
	 * @param definicoes LinkedList<String> que ser� as tradu��es da palavra.
	 */
	public Dicionario(String palavra, LinkedList<String> definicoes){
		this.palavra = palavra;
		this.definicoes = definicoes;
	}
	
	/**
	 * Retorna a palavra.
	 * @return String.
	 */
	public String getPalavra() {
		return palavra;
	}
	
	/**
	 * Defini uma nova palavra ao Objeto Dicion�rio.
	 * @param palavra String que ser� a nova palavra.
	 */
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	/**
	 * Retorna a lista de tradu��es.
	 * @return LinkedList(String).
	 */
	public LinkedList<String> getDefinicoes() {
		return definicoes;
	}

	/**
	 * Define uma nova lista de tradu��es ao Objeto Dicion�rio.
	 * @param definicoes LinkedList(String) que ser� a nova lista de tradu��es.
	 */
	public void setDefinicoes(LinkedList<String> definicoes) {
		this.definicoes = definicoes;
	}

	@Override
	public int compareTo(Dicionario o) {
		return this.palavra.compareTo(o.palavra);
	}
	
}
