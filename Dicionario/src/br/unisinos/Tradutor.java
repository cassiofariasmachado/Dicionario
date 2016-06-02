package br.unisinos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Tradutor{

	private AVLTree<Dicionario> AVLTreeDicionario;

	public Tradutor(){
		this.AVLTreeDicionario = new AVLTree<Dicionario>();
	}

	/**
	 * Carrega o arquivo de dicionário (dicionario.dat) para a árvore AVL.
	 * @param arq String que contém o nome do arquivo 
	 * de onde deverá ser carregada a árvore..
	 */
	protected void carregaDicionario(String arq){
	
		try {
			FileReader file =  new FileReader(arq);
			BufferedReader in = new BufferedReader(file);  
			String line = in.readLine();
			while (line != null){
				String [] arrayInfo = line.split(" ");
		
				String palavra = arrayInfo[0];
				LinkedList<String> definicoes = new LinkedList<>();
		
		
				for (int i = 0; i < arrayInfo.length; i++) {
					definicoes.add(arrayInfo[i]);
		
				}
		
				AVLTreeDicionario.insert(new Dicionario(palavra, definicoes));
		
				line = in.readLine();
			}
		
			file.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		} 
	
	}

	//Traduz uma Ãºnica palavra. Este mÃ©todo recebe como parÃ¢metro a palavra a ser traduzida e retorna a lista das possÃ­veis traduÃ§Ãµes para esta palavra.
	public List<String> traduzPalavra(String palavra){
		return null;
		
	}

	//Insere uma nova definiÃ§Ã£o no dicionÃ¡rio. Recebe como parÃ¢metro a palavra em inglÃªs e lista de possÃ­veis traduÃ§Ãµes.
	public void insereTraducao(String palavra, List<String> definicoes) {
		AVLTreeDicionario.insert(new Dicionario(palavra, definicoes));
	}

	/**
	 * Salva o arquivo de dicionário (dicionario.dat) com as respectivas definições 
	 * baseado no conteúdo da Árvore AVL.
	 * @param arq String que contém o nome do arquivo onde deverá ser salva a árvore.
	 */
	public void salvaDicionario(String arq){
		try {
			FileWriter file =  new FileWriter(arq);
			BufferedWriter in = new BufferedWriter(file);  
		
			in.write(AVLTreeDicionario.toString());
		
			file.close();
		
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		} 
	
	}
	
}
