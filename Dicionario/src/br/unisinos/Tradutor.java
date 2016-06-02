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
	 * Carrega o arquivo de dicion�rio (dicionario.dat) para a �rvore AVL.
	 * @param arq String que cont�m o nome do arquivo 
	 * de onde dever� ser carregada a �rvore..
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

	//Traduz uma única palavra. Este método recebe como parâmetro a palavra a ser traduzida e retorna a lista das possíveis traduções para esta palavra.
	public List<String> traduzPalavra(String palavra){
		return null;
		
	}

	//Insere uma nova definição no dicionário. Recebe como parâmetro a palavra em inglês e lista de possíveis traduções.
	public void insereTraducao(String palavra, List<String> definicoes) {
		AVLTreeDicionario.insert(new Dicionario(palavra, definicoes));
	}

	/**
	 * Salva o arquivo de dicion�rio (dicionario.dat) com as respectivas defini��es 
	 * baseado no conte�do da �rvore AVL.
	 * @param arq String que cont�m o nome do arquivo onde dever� ser salva a �rvore.
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
