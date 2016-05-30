package br.unisinos;

import java.util.LinkedList;

public class Dicionario implements Comparable<Dicionario> {
	private String palavra; //Palavra em inglês
	private LinkedList<String> definicoes; //Traduções/definições em português
	
	/**
	 * Construtor: Cria um novo objeto Dicionario.
	 * @param palavra String que será a palavra buscada no dicionário.
	 * @param definicoes LinkedList<String> que será as traduções da palavra.
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
	 * Defini uma nova palavra ao Objeto Dicionário.
	 * @param palavra String que será a nova palavra.
	 */
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	/**
	 * Retorna a lista de traduções.
	 * @return LinkedList(String).
	 */
	public LinkedList<String> getDefinicoes() {
		return definicoes;
	}

	/**
	 * Define uma nova lista de traduções ao Objeto Dicionário.
	 * @param definicoes LinkedList(String) que será a nova lista de traduções.
	 */
	public void setDefinicoes(LinkedList<String> definicoes) {
		this.definicoes = definicoes;
	}

	@Override
	public int compareTo(Dicionario o) {
		return this.palavra.compareTo(o.palavra);
	}
	
}
