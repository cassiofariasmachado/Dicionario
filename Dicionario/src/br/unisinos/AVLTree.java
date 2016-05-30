package br.unisinos;

public class AVLTree <T extends Comparable<T>> {
	private AVLNode<T> root;
	
	/**
	 * Construtur: Cria uma árvore AVL vazia.
	 */
	public AVLTree (){
		this.root = null;
	}

	/**
	 * Destrói a árvore. Cuidado, aprecie com moderação!!!
	 */
	public void clear(){
		this.root = null;
	}
	
	/**
	 * Verifica se a árvore esta vazia.
	 * @return Booleano: true se vazia, false caso contrário.
	 */
	public boolean isEmpty(){
		//Retorna true se árvore vazia e false caso contrário
		return root == null;
	}
	
	/**
	 * Verifica se o nó é raiz da árvore.
	 * @param node Objeto AVLNode(T) que será comparado com raiz da árvore. 
	 * @return Booleano: true se nó é raiz, false caso contrário. 
	 */
	public boolean isRoot (AVLNode<T> node){
		return this.root == node;
	}
	
	/**
	 * Procura se a chave encontra-se na árvore recursivamente (método público). 
	 * @param key Objeto T a ser procurado.
	 * @return Objeto AVLNode(T) em que a chave se encontra. 
	 */
	public AVLNode<T> search ( T key ){
		return search(root, key);
	}
	
	/**
	 * Procura se a chave encontra-se na árvore recursivamente (método privado).
	 * @param node AVLNode(T) de onde deve começar a busca.
	 * @param key Objeto T a ser procurado.
	 * @return Objeto AVLNode(T) em que a chave se encontra, ou null caso ela não seja encontrada. 
	 */
	private AVLNode<T> search ( AVLNode<T> node, T key ){
		if (isEmpty())
			return null;
		if (key.compareTo(node.getKey()) == 0)
			return node; 
		else if (key.compareTo(node.getKey()) < 0 && node.getRight() != null)
			return search( node.getLeft(), key);
		else if (key.compareTo(node.getKey()) > 0 && node.getLeft() != null)
			return search(node.getRight(), key);
		return null;
	}
	
	/**
	 * Insere um novo nó na árvore recursivamente (método público).
	 * @param key Objeto T que chave desse novo nó.
	 */
	public void insert(T key){
		insert(root, key);
	}
	
	/**
	 * Insere um novo nó na árvore recursivamente (método privado).
	 * @param node Objeto AVLNode(T) de onde deve começar a inserção.
	 * @param key Objeto T que será a chave desse novo nó.
	 */
	private void insert(AVLNode<T> node, T key){
		//Verifica se nao é o primeiro elemento da árvore
		if ( isEmpty() ){
			this.setRoot(new AVLNode<T>(key));
			return;
		}
		
		//Compara se a chave que esta sendo inserida já não existe
		if ( key.compareTo(node.getKey()) == 0 )
			return;
		
		//Se a chave é menor que o nó node
		if ( key.compareTo(node.getKey()) < 0 ){
			//E se o filho da esqueda for nulo, insere nele
			if ( node.getLeft() == null ) {
				node.setLeft(new AVLNode<T>(key));
			}
			//Se não chama o metodo insert novamente passando o filho a direita
			else {
				insert(node.getLeft(), key);
			}
			
			//Atualiza FB
			node.upBalanceFactor();
			
			//Se o FB for maior ou igual 2
			if (node.getBalanceFactor() > 1)
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rotação a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se não executa apenas uma rotação a direita
				else
					rightRotation(node);	
		}
		
		//Se a chave é maior que o nó node
		if (key.compareTo(node.getKey()) > 0){
			//E se o filho da direita for nulo, insere nele
			if ( node.getRight() == null ) {
				node.setRight(new AVLNode<T>(key));
			}
			//Se não chama o metodo insert novamente passando o filho a direita
			else{
				insert(node.getRight(), key);
			}
			
			//Atualiza FB
			node.upBalanceFactor();
			
			//Se FB for menor ou igual a -2
			if (node.getBalanceFactor() < -1)
				//E se o filho da direita tiver FB positivo, executa uma dupla rotação a esquerda
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se não executa apenas uma rotação a esquerda
				else
					leftRotation(node);
		}
		
	}
	
	/**
	 * Remove recursivamente um elemento da árvore (método público).
	 * @param key Objeto T que será removido.
	 */
	public void remove (T key){
		remove (root, key);
	}
	
	/**
	 * Remove recursivamente um elemento da árvore (método privado).
	 * @param node Objeto AVLNode(T) de onde deve partir a remoção.
	 * @param key Objeto T que será removido.
	 */
	private void remove (AVLNode<T> node, T key){
		if (isEmpty())
			return;
		
		if (key.compareTo(node.getKey()) == 0){
			//Se for uma folha
			if (node.isLeaf()){
				//E se for raiz da árvore, destrói ela
				if(isRoot(node))
					this.clear();
				//Se não, verifica se é filho a esquerda ou direita do pai e atribui com null
				else if (node.getParent().getLeft() == node)
					 	node.getParent().setLeft(null);
					 else
						node.getParent().setRight(null);		
			} 
			//Se não for uma folha
			else{
				//Procura o maior elemento da subarvore da esquerda
				AVLNode<T> higherLeft = getHigherOfLeftSubtree(node);
				//Remove ele	 
				if (higherLeft.getParent().getLeft() == higherLeft)
					higherLeft.getParent().setLeft(null);
				else
					higherLeft.getParent().setRight(null);
				//E atribui a chave do nodo coma chave do maior elemento da subarvore da esquerda	
				node.setKey(higherLeft.getKey());  
			}
			//Atualiza o FB
			node.upBalanceFactor();
			return;
		}
		//Se não e se chave for menor que a chave do nó
		else if (key.compareTo(node.getKey()) < 0){
			//E se filho a esquerda do nó for nulo, a chave procurada não se encontra na árvore
			if (node.getLeft() == null)
				return;
			//Se não chama o método passando a sub-árvore a esquerda
			else
				remove(node.getLeft(), key);
		}
		//Se não e se chave for maior que a chave do nó
		else if (key.compareTo(node.getKey()) > 0){
			//E se filho a direita do nó for nulo, a chave procurada não se encontra na árvore
			if (node.getRight() == null)
				return;
			//Se não chama o método passando a sub-árvore a direita
			else
				remove(node.getRight(), key);
		}
		
		//Verificando se gerou desbalanceamento na árvore:
		//Se FB menor que -1 ou maior que 1 
		if (node.getBalanceFactor() < -1 || node.getBalanceFactor() > 1){
			//Se FB menor -1
			if(node.getBalanceFactor() < -1){
				//E se o filho da direita tiver FB positivo, executa uma dupla rotação a esquerda
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se não executa apenas uma rotação a esquerda
				else
					leftRotation(node);
			}
			//Se FB maior 1
			if (node.getBalanceFactor() > 1){
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rotação a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se não executa apenas uma rotação a direita
				else
					rightRotation(node);
			}
		}
	}
	
	/**
	 * Retorna o maior elemento da sub-árvore da esquerda ou nulo caso não exista sub-árvore a esquerda.
	 * @param node Objeto AVLNode(T) de onde deve partir a busca, ou seja, raiz que deve ser considerada. 
	 * @return Objeto AVLNode(T).
	 */
	public AVLNode<T> getHigherOfLeftSubtree(AVLNode<T> node){
		AVLNode<T> current = node.getLeft(), prev = null;
		while (current != null){
			prev = current;
			if (current.getRight() == null){ 
				if(current.getLeft() == null)
					return prev;
				else 
					current = current.getLeft();
			}
			else
				current = current.getRight();
		}
		return prev;
	}
	
	/**
	 * Realiza uma rotação a direita no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void rightRotation(AVLNode<T> node){
		//Node é o nó central da rotação
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do node
		AVLNode<T> nodeLeft = node.getLeft(); //Armazena o filho a esquerda do node
		
		//Altera o filho a esquerda do node com o filho a direita do filho a esquerda do node
		node.setLeft(nodeLeft.getRight()); 
		//Altera o filho a direita do filho da esquerda de node pro proprio node
		nodeLeft.setRight(node);
		
		//Verifica se o pai do node era nulo
		if (nodeParent == null){
			//Define o novo nó central como raiz
			this.setRoot(nodeLeft);
		}
		//Se não verifica se era filho a esquerda ou a direita do pai, e faz a atribuição correta com seu novo filho
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeLeft);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeLeft);
		}
		
		//Atualiza o FB dos nós envolvidos(antigos nó central e filho esquerda).
		node.upBalanceFactor();
		nodeLeft.upBalanceFactor();
	}

	/**
	 * Realiza uma rotação a esquerda no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void leftRotation(AVLNode<T> node){
		//Node é o nó central da rotação
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do node
		AVLNode<T> nodeRight = node.getRight(); //Armazena o filho a esquerda do node
		
		//Altera o filho a direita do node com o filho a esquerda do filho a direita do node
		node.setRight(nodeRight.getLeft());
		//Altera o filho a esquerda do filho da direita de node pro proprio node
		nodeRight.setLeft(node);
		
		//Verifica se o pai do node era nulo
		if (nodeParent == null){
			//Define o novo nó central como raiz
			this.setRoot(nodeRight);
		}
		//Se não verifica se era filho a esquerda ou a direita do pai, e faz a atribuição correta com seu novo filho
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeRight);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeRight);
		}
		
		//Atualiza o FB dos nós envolvidos(antigos nó central e filho a direita).
		node.upBalanceFactor();
		nodeRight.upBalanceFactor();;	
	}
	
	/**
	 * Realiza uma dupla rotação a direita no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void doubleRightRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rotação a esquerda no filho a esquerda do nó central
		leftRotation(node.getLeft());
		//Após uma rotação a direita no nó central
		rightRotation(node);
	}
	
	/**
	 * Realiza uma dupla rotação a esquerda no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void doubleLeftRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rotação a direita no filho a direita do nó central
		rightRotation(node.getRight());
		//Após uma rotação a esquerda no nó central
		leftRotation(node);
	}
	
	/**
	 * Retorna a raiz da árvore.
	 * @return AVLNode(T).
	 */
	public AVLNode<T> getRoot() {
		return root;
	}

	/**
	 * Define um nova raiz para a árvore.
	 * @param node Objeto AVLNode(T) que será a nova raiz da árvore.
	 */
	public void setRoot(AVLNode<T> node){
		this.root = node; //Define a nova raiz da árvore
		this.root.setParent(null); //Define o pai da raiz como nulo
	}
	
	/**
	 * Percorre a árvore em pré-ordem recursivamente (método público).
	 */
	public void preOrder(){
		preOrder(root);
	}
	
	/**
	 * Percorre a árvore em pré-ordem recursivamente (método privado).
	 * @param node Objeto AVLNode(T) de onde deve partir o percorrimento.
	 */
	private void preOrder(AVLNode <T> node){
		if (node != null){
			System.out.print(node.getKey()+ " ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}
	}
	
	/**
	 * Percorre a árvore em pos-ordem recursivamente (método público).
	 */
	public void postOrder(){
		postOrder(root);
	}
	
	/**
	 * Percorre a árvore em pos-ordem recursivamente (método privado).
	 * @param node Objeto AVLNode(T) de onde deve partir o percorrimento.
	 */
	private void postOrder(AVLNode<T> node){
		if (node != null){
			postOrder(node.getLeft());
			postOrder(node.getRight());
			System.out.print(node.getKey()+ " ");
		}
	}
	
	/**
	 * Percorre a árvore em-ordem recursivamente (método público).
	 */
	public void inOrder(){
		inOrder(root);
	}
	
	/**
	 * Percorre a árvore em-ordem recursivamente (método privado).
	 * @param node Objeto AVLNode(T) de onde deve partir o percorrimento.
	 */
	private void inOrder(AVLNode<T> node){
		if (node != null){
			inOrder(node.getLeft());
			System.out.print(node.getKey()+ " ");
			inOrder(node.getRight());
		}
	}
	
	//TESTE
	public static void main (String args[]){
		AVLTree <Integer> dic = new AVLTree<>();
		dic.insert(12);
		dic.insert(10);
		dic.insert(13);
		dic.insert(8);
		dic.insert(20);
		dic.insert(11);
		dic.insert(15);
		
		System.out.println();
		dic.inOrder();
		
		dic.remove(20);
		
		System.out.println();
		dic.inOrder();
	}
	
}