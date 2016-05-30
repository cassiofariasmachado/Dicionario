package br.unisinos;

public class AVLTree <T extends Comparable<T>> {
	private AVLNode<T> root;
	
	/**
	 * Construtur: Cria uma �rvore AVL vazia.
	 */
	public AVLTree (){
		this.root = null;
	}

	/**
	 * Destr�i a �rvore. Cuidado, aprecie com modera��o!!!
	 */
	public void clear(){
		this.root = null;
	}
	
	/**
	 * Verifica se a �rvore esta vazia.
	 * @return Booleano: true se vazia, false caso contr�rio.
	 */
	public boolean isEmpty(){
		//Retorna true se �rvore vazia e false caso contr�rio
		return root == null;
	}
	
	/**
	 * Verifica se o n� � raiz da �rvore.
	 * @param node Objeto AVLNode(T) que ser� comparado com raiz da �rvore. 
	 * @return Booleano: true se n� � raiz, false caso contr�rio. 
	 */
	public boolean isRoot (AVLNode<T> node){
		return this.root == node;
	}
	
	/**
	 * Procura se a chave encontra-se na �rvore recursivamente (m�todo p�blico). 
	 * @param key Objeto T a ser procurado.
	 * @return Objeto AVLNode(T) em que a chave se encontra. 
	 */
	public AVLNode<T> search ( T key ){
		return search(root, key);
	}
	
	/**
	 * Procura se a chave encontra-se na �rvore recursivamente (m�todo privado).
	 * @param node AVLNode(T) de onde deve come�ar a busca.
	 * @param key Objeto T a ser procurado.
	 * @return Objeto AVLNode(T) em que a chave se encontra, ou null caso ela n�o seja encontrada. 
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
	 * Insere um novo n� na �rvore recursivamente (m�todo p�blico).
	 * @param key Objeto T que chave desse novo n�.
	 */
	public void insert(T key){
		insert(root, key);
	}
	
	/**
	 * Insere um novo n� na �rvore recursivamente (m�todo privado).
	 * @param node Objeto AVLNode(T) de onde deve come�ar a inser��o.
	 * @param key Objeto T que ser� a chave desse novo n�.
	 */
	private void insert(AVLNode<T> node, T key){
		//Verifica se nao � o primeiro elemento da �rvore
		if ( isEmpty() ){
			this.setRoot(new AVLNode<T>(key));
			return;
		}
		
		//Compara se a chave que esta sendo inserida j� n�o existe
		if ( key.compareTo(node.getKey()) == 0 )
			return;
		
		//Se a chave � menor que o n� node
		if ( key.compareTo(node.getKey()) < 0 ){
			//E se o filho da esqueda for nulo, insere nele
			if ( node.getLeft() == null ) {
				node.setLeft(new AVLNode<T>(key));
			}
			//Se n�o chama o metodo insert novamente passando o filho a direita
			else {
				insert(node.getLeft(), key);
			}
			
			//Atualiza FB
			node.upBalanceFactor();
			
			//Se o FB for maior ou igual 2
			if (node.getBalanceFactor() > 1)
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rota��o a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se n�o executa apenas uma rota��o a direita
				else
					rightRotation(node);	
		}
		
		//Se a chave � maior que o n� node
		if (key.compareTo(node.getKey()) > 0){
			//E se o filho da direita for nulo, insere nele
			if ( node.getRight() == null ) {
				node.setRight(new AVLNode<T>(key));
			}
			//Se n�o chama o metodo insert novamente passando o filho a direita
			else{
				insert(node.getRight(), key);
			}
			
			//Atualiza FB
			node.upBalanceFactor();
			
			//Se FB for menor ou igual a -2
			if (node.getBalanceFactor() < -1)
				//E se o filho da direita tiver FB positivo, executa uma dupla rota��o a esquerda
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se n�o executa apenas uma rota��o a esquerda
				else
					leftRotation(node);
		}
		
	}
	
	/**
	 * Remove recursivamente um elemento da �rvore (m�todo p�blico).
	 * @param key Objeto T que ser� removido.
	 */
	public void remove (T key){
		remove (root, key);
	}
	
	/**
	 * Remove recursivamente um elemento da �rvore (m�todo privado).
	 * @param node Objeto AVLNode(T) de onde deve partir a remo��o.
	 * @param key Objeto T que ser� removido.
	 */
	private void remove (AVLNode<T> node, T key){
		if (isEmpty())
			return;
		
		if (key.compareTo(node.getKey()) == 0){
			//Se for uma folha
			if (node.isLeaf()){
				//E se for raiz da �rvore, destr�i ela
				if(isRoot(node))
					this.clear();
				//Se n�o, verifica se � filho a esquerda ou direita do pai e atribui com null
				else if (node.getParent().getLeft() == node)
					 	node.getParent().setLeft(null);
					 else
						node.getParent().setRight(null);		
			} 
			//Se n�o for uma folha
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
		//Se n�o e se chave for menor que a chave do n�
		else if (key.compareTo(node.getKey()) < 0){
			//E se filho a esquerda do n� for nulo, a chave procurada n�o se encontra na �rvore
			if (node.getLeft() == null)
				return;
			//Se n�o chama o m�todo passando a sub-�rvore a esquerda
			else
				remove(node.getLeft(), key);
		}
		//Se n�o e se chave for maior que a chave do n�
		else if (key.compareTo(node.getKey()) > 0){
			//E se filho a direita do n� for nulo, a chave procurada n�o se encontra na �rvore
			if (node.getRight() == null)
				return;
			//Se n�o chama o m�todo passando a sub-�rvore a direita
			else
				remove(node.getRight(), key);
		}
		
		//Verificando se gerou desbalanceamento na �rvore:
		//Se FB menor que -1 ou maior que 1 
		if (node.getBalanceFactor() < -1 || node.getBalanceFactor() > 1){
			//Se FB menor -1
			if(node.getBalanceFactor() < -1){
				//E se o filho da direita tiver FB positivo, executa uma dupla rota��o a esquerda
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se n�o executa apenas uma rota��o a esquerda
				else
					leftRotation(node);
			}
			//Se FB maior 1
			if (node.getBalanceFactor() > 1){
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rota��o a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se n�o executa apenas uma rota��o a direita
				else
					rightRotation(node);
			}
		}
	}
	
	/**
	 * Retorna o maior elemento da sub-�rvore da esquerda ou nulo caso n�o exista sub-�rvore a esquerda.
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
	 * Realiza uma rota��o a direita no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void rightRotation(AVLNode<T> node){
		//Node � o n� central da rota��o
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do node
		AVLNode<T> nodeLeft = node.getLeft(); //Armazena o filho a esquerda do node
		
		//Altera o filho a esquerda do node com o filho a direita do filho a esquerda do node
		node.setLeft(nodeLeft.getRight()); 
		//Altera o filho a direita do filho da esquerda de node pro proprio node
		nodeLeft.setRight(node);
		
		//Verifica se o pai do node era nulo
		if (nodeParent == null){
			//Define o novo n� central como raiz
			this.setRoot(nodeLeft);
		}
		//Se n�o verifica se era filho a esquerda ou a direita do pai, e faz a atribui��o correta com seu novo filho
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeLeft);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeLeft);
		}
		
		//Atualiza o FB dos n�s envolvidos(antigos n� central e filho esquerda).
		node.upBalanceFactor();
		nodeLeft.upBalanceFactor();
	}

	/**
	 * Realiza uma rota��o a esquerda no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void leftRotation(AVLNode<T> node){
		//Node � o n� central da rota��o
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do node
		AVLNode<T> nodeRight = node.getRight(); //Armazena o filho a esquerda do node
		
		//Altera o filho a direita do node com o filho a esquerda do filho a direita do node
		node.setRight(nodeRight.getLeft());
		//Altera o filho a esquerda do filho da direita de node pro proprio node
		nodeRight.setLeft(node);
		
		//Verifica se o pai do node era nulo
		if (nodeParent == null){
			//Define o novo n� central como raiz
			this.setRoot(nodeRight);
		}
		//Se n�o verifica se era filho a esquerda ou a direita do pai, e faz a atribui��o correta com seu novo filho
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeRight);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeRight);
		}
		
		//Atualiza o FB dos n�s envolvidos(antigos n� central e filho a direita).
		node.upBalanceFactor();
		nodeRight.upBalanceFactor();;	
	}
	
	/**
	 * Realiza uma dupla rota��o a direita no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void doubleRightRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rota��o a esquerda no filho a esquerda do n� central
		leftRotation(node.getLeft());
		//Ap�s uma rota��o a direita no n� central
		rightRotation(node);
	}
	
	/**
	 * Realiza uma dupla rota��o a esquerda no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void doubleLeftRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rota��o a direita no filho a direita do n� central
		rightRotation(node.getRight());
		//Ap�s uma rota��o a esquerda no n� central
		leftRotation(node);
	}
	
	/**
	 * Retorna a raiz da �rvore.
	 * @return AVLNode(T).
	 */
	public AVLNode<T> getRoot() {
		return root;
	}

	/**
	 * Define um nova raiz para a �rvore.
	 * @param node Objeto AVLNode(T) que ser� a nova raiz da �rvore.
	 */
	public void setRoot(AVLNode<T> node){
		this.root = node; //Define a nova raiz da �rvore
		this.root.setParent(null); //Define o pai da raiz como nulo
	}
	
	/**
	 * Percorre a �rvore em pr�-ordem recursivamente (m�todo p�blico).
	 */
	public void preOrder(){
		preOrder(root);
	}
	
	/**
	 * Percorre a �rvore em pr�-ordem recursivamente (m�todo privado).
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
	 * Percorre a �rvore em pos-ordem recursivamente (m�todo p�blico).
	 */
	public void postOrder(){
		postOrder(root);
	}
	
	/**
	 * Percorre a �rvore em pos-ordem recursivamente (m�todo privado).
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
	 * Percorre a �rvore em-ordem recursivamente (m�todo p�blico).
	 */
	public void inOrder(){
		inOrder(root);
	}
	
	/**
	 * Percorre a �rvore em-ordem recursivamente (m�todo privado).
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