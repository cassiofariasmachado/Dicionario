package br.unisinos;

import java.util.LinkedList;
import java.util.List;

public class Dicionario implements Comparable<Dicionario> {
	private String palavra; //Palavra em ingl�s
	private List <String> definicoes; //Tradu��es/defini��es em portugu�s
	
	/**
	 * Construtor: Cria um novo objeto Dicionario.
	 * @param palavra String que ser� a palavra a ser traduzida.
	 * @param definicoes LinkedList(String) que ser� as tradu��es da palavra.
	 */
	public Dicionario(String palavra, List <String> definicoes){
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
	public List <String> getDefinicoes() {
		return definicoes;
	}

	/**
	 * Define uma nova lista de tradu��es ao Objeto Dicion�rio.
	 * @param definicoes LinkedList(String) que ser� a nova lista de tradu��es.
	 */
	public void setDefinicoes(LinkedList<String> definicoes) {
		this.definicoes = definicoes;
	}

	//Sobre-escrita do m�todo compareTo.
	@Override
	public int compareTo(Dicionario o) {
		return this.palavra.compareTo(o.palavra);
	}
	
	//Sobre-escrita do m�todo toString.
	@Override
	public String toString() {
		String str = palavra + " ";
		if(definicoes != null){
			for (int i = 0; i < definicoes.size(); i++) {
				str += definicoes.get(i) + " ";
			}
		}
		return str;
	}
	
}
