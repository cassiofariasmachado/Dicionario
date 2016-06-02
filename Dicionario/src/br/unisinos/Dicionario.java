package br.unisinos;

import java.util.LinkedList;
import java.util.List;

public class Dicionario implements Comparable<Dicionario> {
	private String palavra; //Palavra em inglês
	private List <String> definicoes; //Traduções/definições em português
	
	/**
	 * Construtor: Cria um novo objeto Dicionario.
	 * @param palavra String que será a palavra a ser traduzida.
	 * @param definicoes LinkedList(String) que será as traduções da palavra.
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
	public List <String> getDefinicoes() {
		return definicoes;
	}

	/**
	 * Define uma nova lista de traduções ao Objeto Dicionário.
	 * @param definicoes LinkedList(String) que será a nova lista de traduções.
	 */
	public void setDefinicoes(LinkedList<String> definicoes) {
		this.definicoes = definicoes;
	}

	//Sobre-escrita do método compareTo.
	@Override
	public int compareTo(Dicionario o) {
		return this.palavra.compareTo(o.palavra);
	}
	
	//Sobre-escrita do método toString.
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
