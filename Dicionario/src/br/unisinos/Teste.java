package br.unisinos;

import java.util.LinkedList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
			
			AVLTree<Dicionario> avl = new AVLTree<Dicionario>();
			List <String> list = new LinkedList<>();
	 		
			list.add("elemento1");
	 		list.add("elemento2");
	 		list.add("elemento3");
	 		list.add("elemento4");
	 		list.add("elemento5");
	 		
			avl.insert(new Dicionario("selo", list));
			avl.insert(new Dicionario("celular", list));
			avl.insert(new Dicionario("c", list));
			avl.insert(new Dicionario("cellar", null));
			avl.insert(new Dicionario("cella", null));
			avl.insert(new Dicionario("cell", null));
			avl.insert(new Dicionario("cel", null));
			avl.insert(new Dicionario("ce", null));
			
			
			avl.inOrder();

	}

}
