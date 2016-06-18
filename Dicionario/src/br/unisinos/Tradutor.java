package br.unisinos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
			
			System.out.println("Dicion�rio carregado com sucesso.\n");
			
			file.close();
		} catch (IOException e) {
			System.err.println("Dicion�rio n�o encontrado, arquivo ser� criado ao fim da execu��o.\n");
		} 
	
	}

	/**
	 * Traduz uma �nica palavra.
	 * Este m�todo recebe como par�metro a palavra a ser traduzida 
	 * e retorna a lista das possíveis tradu��es para esta palavra.
	 * @param palavra String que ser� traduzida.
	 * @return List(String) com todas as tradu��es da palavra.
	 */
	public List<String> traduzPalavra(String palavra){
		AVLNode<Dicionario> el = AVLTreeDicionario.search(new Dicionario(palavra, null));
		if (el == null)
			return null;
		return el.getKey().getDefinicoes();
	}

	/**
	 * Insere uma nova defini��o no dicion�rio. 
	 * Recebe como par�metro a palavra em ingl�s e lista de poss�veis tradu��es.
	 * @param palavra String que ser� a palavra em ingl�s.
	 * @param definicoes List (String) que cont�m as tradu��es.
	 */
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
			PrintWriter in = new PrintWriter(file);  
		
			in.write(AVLTreeDicionario.toString());
		
			System.out.println("Dicion�rio salvo com sucesso.");
			
			file.close();
			
		} catch (IOException e) {
			System.err.printf("Erro ao salvar arquivo: %s.\n\n", e.getMessage());
		} 
	}
	
	public AVLTree<Dicionario> getAVLTreeDicionario() {
		return AVLTreeDicionario;
	}

	public void setAVLTreeDicionario(AVLTree<Dicionario> aVLTreeDicionario) {
		AVLTreeDicionario = aVLTreeDicionario;
	}

	public static void main (String [] args){
		Tradutor tradutor = new Tradutor();
		Scanner s = new Scanner(System.in);
		
		System.out.println("=============== Tradutor Ingl�s-Portugu�s 1.0 ===============\n\n");
		
		tradutor.carregaDicionario("dicionario.dat");
		
		int n;
		
		do {
			
		System.out.println("Digite o n�mero da op��o desejada: \n");
		System.out.println("1 - Listar todas as palavra");
		System.out.println("2 - Procurar uma palavra");
		System.out.println("3 - Adicionar uma palavra e suas defini��es");
		System.out.println("0 - Salva arquivo e encerra execu��o do programa\n" );
		
		n = s.nextInt();	
		
		switch (n) {
		
		case 0:
			tradutor.salvaDicionario("dicionario.dat");
			break;
		
		case 1:
			System.out.println("Palavras:\n");
			tradutor.getAVLTreeDicionario().inOrder();
			System.out.println();
			break;
		
		case 2:
			System.out.println("Digite a palavra a ser traduzida: \n");
			String palavra = s.next();
			List<String> list = tradutor.traduzPalavra(palavra);
			if (list != null){
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i)+"\n");
				}
			}
			else {
				System.out.println("Palavra n�o encontrada.\n");
			}
			break;
		
		case 3:
			System.out.println("Digite a palavra a ser inserida: ");
			String novaPalavra = s.next();
			if (tradutor.getAVLTreeDicionario().search(new Dicionario(novaPalavra, null)) == null){
				System.out.println("Digite as tradu��es/defini��es da palavra (ou digite sair para sair): ");
				List<String> novasDef = new LinkedList<String>();
				String def = s.next();
				do {
					novasDef.add(def);
				
					def = s.next();
				
				} while (!def.equals("sair"));
				
				tradutor.insereTraducao(novaPalavra, novasDef);	
			
			}
			else{
				AVLNode<Dicionario> aux =  tradutor.getAVLTreeDicionario().search(new Dicionario(novaPalavra, null));
				System.out.println("A palavra j� existe no dicion�rio");
				System.out.println("Digite as suas novas tradu��es/defini��es da palavra (ou digite sair para sair): ");
				String def = s.next();
				do {
					aux.getKey().getDefinicoes().add(def);
	
					def = s.next();
				
				} while (!def.equals("sair"));
			}
			
			break;	
		}
		
		} while (n != 0);
		
		s.close();
	}
	
}
