package br.unisinos;

public class Tradutor{

private AVLTree AVLTreeDicionario;

//Carrega o arquivo de dicionário(dicionário.dat) para a árvore AVL.
protected void carregaDicionario(String arq){
  try {
	BufferedReader in = new BufferedReader(new FileReader("dicionario.dat"));
	
	String line = in.readLine();
	while (line != null){
		String [] arrayInfo = line.split(" ");
		arrayInfo [0]
		
		line = in.readLine();
	}
	
	
	} catch (IOException e) {
		System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	} 
	
}

//Traduz uma única palavra. Este método recebe como parâmetro a palavra a ser traduzida e retorna a lista das possíveis traduções para esta palavra.
public List traduzPalavra(String palavra){


}

//Insere uma nova definição no dicionário. Recebe como parâmetro a palavra em inglês e lista de possíveis traduções.
public void insereTraducao(String palavra, List definicoes) {

}

//Salva o arquivo de dicionário (dicionário.dat) com as respectivas definições baseado no conteúdo da árvore AVL.
public void salvaDicionario(String arq){

}

}
