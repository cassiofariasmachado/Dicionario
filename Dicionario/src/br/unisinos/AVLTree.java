package br.unisinos;

public class AVLTree <T extends Comparable<T>> {
	private AVLNode<T> root; //Raiz da �rvore
	
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
		//Retorna true se �rvore vazia e false caso contr�rio.
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
		//Se �rvore est� vazia j� retorna null.
		if (isEmpty())
			return null;
		//Se chave se encontra no n� atual, retorna ele.
		if (key.compareTo(node.getKey()) == 0)
			return node; 
		//Se n�o, e se chave � menor e filho a esquerda n�o � nulo, 
		//chama novamente o metodo passando o filho a esquerda.
		else if (key.compareTo(node.getKey()) < 0 && node.getRight() != null)
			return search( node.getLeft(), key);
		//Se n�o, e se chave � maior e filho a direita n�o � nulo,
		//chama o novamente o m�todo passando o filho a direita.
		else if (key.compareTo(node.getKey()) > 0 && node.getLeft() != null)
			return search(node.getRight(), key);
		//Se n�o, chave n�o se encontra na �rvore: retorna null.
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
		//Verifica se n�o � o primeiro elemento da �rvore.
		if ( isEmpty() ){
			this.setRoot(new AVLNode<T>(key));
			return;
		}
		
		//Compara se a chave que esta sendo inserida j� n�o existe.
		if ( key.compareTo(node.getKey()) == 0 )
			return;
		
		//Se a chave � menor que o n� atual.
		if ( key.compareTo(node.getKey()) < 0 ){
			//E se o filho da esqueda for nulo, insere nele.
			if ( node.getLeft() == null ) {
				node.setLeft(new AVLNode<T>(key));
			}
			//Se n�o chama o metodo insert novamente passando o filho a esquerda.
			else {
				insert(node.getLeft(), key);
			}
			//Atualiza FB.
			node.upBalanceFactor();
			//Se o FB for maior ou igual 2.
			if (node.getBalanceFactor() > 1)
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rota��o a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se n�o executa apenas uma rota��o a direita
				else
					rightRotation(node);	
		}
		
		//Se a chave � maior que o n� atual.
		if (key.compareTo(node.getKey()) > 0){
			//E se o filho da direita for nulo, insere nele.
			if ( node.getRight() == null ) {
				node.setRight(new AVLNode<T>(key));
			}
			//Se n�o chama o metodo insert novamente passando o filho a direita.
			else{
				insert(node.getRight(), key);
			}
			//Atualiza FB.
			node.upBalanceFactor();
			//Se FB for menor ou igual a -2.
			if (node.getBalanceFactor() < -1)
				//E se o filho da direita tiver FB positivo, executa uma dupla rota��o a esquerda.
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se n�o executa apenas uma rota��o a esquerda.
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
		//Se �rvore vazia, n�o faz nada.
		if (isEmpty())
			return;
		
		//Se n� atual conter a chave a ser deletada.
		if (key.compareTo(node.getKey()) == 0){
			//Se for uma folha
			if (node.isLeaf()){
				//E se for raiz da �rvore, destr�i ela.
				if(isRoot(node))
					this.clear();
				//Se n�o, verifica se � filho a esquerda ou direita do pai e atribui com null.
				else if (node.getParent().getLeft() == node)
					 	node.getParent().setLeft(null);
					 else
						node.getParent().setRight(null);		
			} 
			//Se n�o for uma folha.
			else{
				//Procura o maior elemento da sub-�rvore da esquerda.
				AVLNode<T> higherLeft = getHigherOfLeftSubTree(node);
				//Remove ele da �rvore	 
				if (higherLeft.getParent().getLeft() == higherLeft)
					higherLeft.getParent().setLeft(null);
				else
					higherLeft.getParent().setRight(null);
				//E atribui a chave do n� atual com a chave do maior
				//elemento da subarvore da esquerda.
				node.setKey(higherLeft.getKey());  
			}
			//Atualiza o FB.
			node.upBalanceFactor();
			return;
		}
		
		//Se n�o e se chave for menor que a chave do n�.
		else if (key.compareTo(node.getKey()) < 0){
			//E se filho a esquerda do n� for nulo, 
			//a chave procurada n�o se encontra na �rvore: 
			//Termina a opera��o.
			if (node.getLeft() == null)
				return;
			//Se n�o, chama o m�todo passando a sub-�rvore a esquerda.
			else
				remove(node.getLeft(), key);
		}
		
		//Se n�o e se chave for maior que a chave do n�.
		else if (key.compareTo(node.getKey()) > 0){
			//E se filho a direita do n� for nulo, 
			//a chave procurada n�o se encontra na �rvore:
			//Termina a opera��o.
			if (node.getRight() == null)
				return;
			//Se n�o, chama o m�todo passando a sub-�rvore a direita.
			else
				remove(node.getRight(), key);
		}
		
		//Verificando se altera��o gerou um desbalanceamento na �rvore:
		//Se FB menor que -1 ou maior que 1.
		if (node.getBalanceFactor() < -1 || node.getBalanceFactor() > 1){
			//Se FB menor -1
			if(node.getBalanceFactor() < -1){
				//E se o filho da direita tiver FB positivo, 
				//executa uma dupla rota��o a esquerda.
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se n�o executa apenas uma rota��o a esquerda.
				else
					leftRotation(node);
			}
			//Se FB maior 1
			if (node.getBalanceFactor() > 1){
				//E se o filho da esquerda tiver FB negativo, 
				//executa uma dupla rota��o a direita.
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se n�o executa apenas uma rota��o a direita.
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
	public AVLNode<T> getHigherOfLeftSubTree(AVLNode<T> node){
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
		//Node = n� central da rota��o.
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do n� central.
		AVLNode<T> nodeLeft = node.getLeft(); //Armazena o filho a esquerda do n� central.
		
		//Altera o filho a esquerda do n� central
		//para o filho a direita de seu filho a esquerda (nodeLeft).
		node.setLeft(nodeLeft.getRight()); 
		//Altera o filho a direita do filho da esquerda do n� central (nodeLeft)
		//para o pr�prio n� central.
		nodeLeft.setRight(node);
		
		//Verifica se o pai do n� central era nulo.
		if (nodeParent == null){
			//Define o novo n� central como raiz.
			this.setRoot(nodeLeft);
		}
		//Se n�o verifica se era filho a esquerda ou a direita do pai, 
		//e faz a atribui��o correta com seu novo filho.
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeLeft);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeLeft);
		}
		
		//Atualiza o FB dos n�s envolvidos: 
		//antigos n� central e seu filho esquerda.
		node.upBalanceFactor();
		nodeLeft.upBalanceFactor();
	}

	/**
	 * Realiza uma rota��o a esquerda no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void leftRotation(AVLNode<T> node){
		//Node = n� central da rota��o.
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do n� central.
		AVLNode<T> nodeRight = node.getRight(); //Armazena o filho a esquerda do n� central.
		
		//Altera o filho a direita do n� central
		//para o filho a esquerda de seu filho a direita (nodeRight).
		node.setRight(nodeRight.getLeft());
		//Altera o filho a esquerda do filho da direita do n� central (nodeRight)
		//para o proprio n� central.
		nodeRight.setLeft(node);
		
		//Verifica se o pai do n� central era nulo.
		if (nodeParent == null){
			//Define o novo n� central como raiz.
			this.setRoot(nodeRight);
		}
		//Se n�o verifica se era filho a esquerda ou a direita do pai, 
		//e faz a atribui��o correta com seu novo filho.
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeRight);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeRight);
		}
		
		//Atualiza o FB dos n�s envolvidos:
		//antigos n� central e filho a direita.
		node.upBalanceFactor();
		nodeRight.upBalanceFactor();;	
	}
	
	/**
	 * Realiza uma dupla rota��o a direita no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void doubleRightRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rota��o a esquerda 
		//no filho a esquerda do n� central.
		leftRotation(node.getLeft());
		//Ap�s uma rota��o a direita no n� central.
		rightRotation(node);
	}
	
	/**
	 * Realiza uma dupla rota��o a esquerda no n� central passado por par�metro.
	 * @param node Objeto AVLNode(T) que � o n� central da rota��o.
	 */
	public void doubleLeftRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rota��o a direita 
		//no filho a direita do n� central.
		rightRotation(node.getRight());
		//Ap�s uma rota��o a esquerda no n� central.
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
	
	//Sobre-escrita recursiva do m�todo toString (m�todo p�blico).
	@Override
	public String toString() {
		return toString(root, "");
	}
	
	//Sobre-escrita recursiva do m�todo toString (m�todo privado).
	private String toString(AVLNode<T> node, String str) {
		if(node != null){
			str += node.getKey() + "\n" + toString(node.getLeft(), str) + toString(node.getRight(), str);
		}
		return str;
	}
	
}