package br.unisinos;

public class AVLTree <T extends Comparable<T>> {
	private AVLNode<T> root; //Raiz da árvore
	
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
		//Retorna true se árvore vazia e false caso contrário.
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
		//Se árvore está vazia já retorna null.
		if (isEmpty())
			return null;
		//Se chave se encontra no nó atual, retorna ele.
		if (key.compareTo(node.getKey()) == 0)
			return node; 
		//Se não, e se chave é menor e filho a esquerda não é nulo, 
		//chama novamente o metodo passando o filho a esquerda.
		else if (key.compareTo(node.getKey()) < 0 && node.getRight() != null)
			return search( node.getLeft(), key);
		//Se não, e se chave é maior e filho a direita não é nulo,
		//chama o novamente o método passando o filho a direita.
		else if (key.compareTo(node.getKey()) > 0 && node.getLeft() != null)
			return search(node.getRight(), key);
		//Se não, chave não se encontra na árvore: retorna null.
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
		//Verifica se não é o primeiro elemento da árvore.
		if ( isEmpty() ){
			this.setRoot(new AVLNode<T>(key));
			return;
		}
		
		//Compara se a chave que esta sendo inserida já não existe.
		if ( key.compareTo(node.getKey()) == 0 )
			return;
		
		//Se a chave é menor que o nó atual.
		if ( key.compareTo(node.getKey()) < 0 ){
			//E se o filho da esqueda for nulo, insere nele.
			if ( node.getLeft() == null ) {
				node.setLeft(new AVLNode<T>(key));
			}
			//Se não chama o metodo insert novamente passando o filho a esquerda.
			else {
				insert(node.getLeft(), key);
			}
			//Atualiza FB.
			node.upBalanceFactor();
			//Se o FB for maior ou igual 2.
			if (node.getBalanceFactor() > 1)
				//E se o filho da esquerda tiver FB negativo, executa uma dupla rotação a direita
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se não executa apenas uma rotação a direita
				else
					rightRotation(node);	
		}
		
		//Se a chave é maior que o nó atual.
		if (key.compareTo(node.getKey()) > 0){
			//E se o filho da direita for nulo, insere nele.
			if ( node.getRight() == null ) {
				node.setRight(new AVLNode<T>(key));
			}
			//Se não chama o metodo insert novamente passando o filho a direita.
			else{
				insert(node.getRight(), key);
			}
			//Atualiza FB.
			node.upBalanceFactor();
			//Se FB for menor ou igual a -2.
			if (node.getBalanceFactor() < -1)
				//E se o filho da direita tiver FB positivo, executa uma dupla rotação a esquerda.
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se não executa apenas uma rotação a esquerda.
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
		//Se árvore vazia, não faz nada.
		if (isEmpty())
			return;
		
		//Se nó atual conter a chave a ser deletada.
		if (key.compareTo(node.getKey()) == 0){
			//Se for uma folha
			if (node.isLeaf()){
				//E se for raiz da árvore, destrói ela.
				if(isRoot(node))
					this.clear();
				//Se não, verifica se é filho a esquerda ou direita do pai e atribui com null.
				else if (node.getParent().getLeft() == node)
					 	node.getParent().setLeft(null);
					 else
						node.getParent().setRight(null);		
			} 
			//Se não for uma folha.
			else{
				//Procura o maior elemento da sub-árvore da esquerda.
				AVLNode<T> higherLeft = getHigherOfLeftSubTree(node);
				//Remove ele da árvore	 
				if (higherLeft.getParent().getLeft() == higherLeft)
					higherLeft.getParent().setLeft(null);
				else
					higherLeft.getParent().setRight(null);
				//E atribui a chave do nó atual com a chave do maior
				//elemento da subarvore da esquerda.
				node.setKey(higherLeft.getKey());  
			}
			//Atualiza o FB.
			node.upBalanceFactor();
			return;
		}
		
		//Se não e se chave for menor que a chave do nó.
		else if (key.compareTo(node.getKey()) < 0){
			//E se filho a esquerda do nó for nulo, 
			//a chave procurada não se encontra na árvore: 
			//Termina a operação.
			if (node.getLeft() == null)
				return;
			//Se não, chama o método passando a sub-árvore a esquerda.
			else
				remove(node.getLeft(), key);
		}
		
		//Se não e se chave for maior que a chave do nó.
		else if (key.compareTo(node.getKey()) > 0){
			//E se filho a direita do nó for nulo, 
			//a chave procurada não se encontra na árvore:
			//Termina a operação.
			if (node.getRight() == null)
				return;
			//Se não, chama o método passando a sub-árvore a direita.
			else
				remove(node.getRight(), key);
		}
		
		//Verificando se alteração gerou um desbalanceamento na árvore:
		//Se FB menor que -1 ou maior que 1.
		if (node.getBalanceFactor() < -1 || node.getBalanceFactor() > 1){
			//Se FB menor -1
			if(node.getBalanceFactor() < -1){
				//E se o filho da direita tiver FB positivo, 
				//executa uma dupla rotação a esquerda.
				if (node.getRight().getBalanceFactor() > 0)
					doubleLeftRotation(node);
				//Se não executa apenas uma rotação a esquerda.
				else
					leftRotation(node);
			}
			//Se FB maior 1
			if (node.getBalanceFactor() > 1){
				//E se o filho da esquerda tiver FB negativo, 
				//executa uma dupla rotação a direita.
				if (node.getLeft().getBalanceFactor() < 0)
					doubleRightRotation(node);
				//Se não executa apenas uma rotação a direita.
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
	 * Realiza uma rotação a direita no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void rightRotation(AVLNode<T> node){
		//Node = nó central da rotação.
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do nó central.
		AVLNode<T> nodeLeft = node.getLeft(); //Armazena o filho a esquerda do nó central.
		
		//Altera o filho a esquerda do nó central
		//para o filho a direita de seu filho a esquerda (nodeLeft).
		node.setLeft(nodeLeft.getRight()); 
		//Altera o filho a direita do filho da esquerda do nó central (nodeLeft)
		//para o próprio nó central.
		nodeLeft.setRight(node);
		
		//Verifica se o pai do nó central era nulo.
		if (nodeParent == null){
			//Define o novo nó central como raiz.
			this.setRoot(nodeLeft);
		}
		//Se não verifica se era filho a esquerda ou a direita do pai, 
		//e faz a atribuição correta com seu novo filho.
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeLeft);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeLeft);
		}
		
		//Atualiza o FB dos nós envolvidos: 
		//antigos nó central e seu filho esquerda.
		node.upBalanceFactor();
		nodeLeft.upBalanceFactor();
	}

	/**
	 * Realiza uma rotação a esquerda no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void leftRotation(AVLNode<T> node){
		//Node = nó central da rotação.
		AVLNode<T> nodeParent = node.getParent(); //Armazena o pai do nó central.
		AVLNode<T> nodeRight = node.getRight(); //Armazena o filho a esquerda do nó central.
		
		//Altera o filho a direita do nó central
		//para o filho a esquerda de seu filho a direita (nodeRight).
		node.setRight(nodeRight.getLeft());
		//Altera o filho a esquerda do filho da direita do nó central (nodeRight)
		//para o proprio nó central.
		nodeRight.setLeft(node);
		
		//Verifica se o pai do nó central era nulo.
		if (nodeParent == null){
			//Define o novo nó central como raiz.
			this.setRoot(nodeRight);
		}
		//Se não verifica se era filho a esquerda ou a direita do pai, 
		//e faz a atribuição correta com seu novo filho.
		else{
			if(nodeParent.getLeft() == node)
				nodeParent.setLeft(nodeRight);
			else if (nodeParent.getRight() == node)
					nodeParent.setRight(nodeRight);
		}
		
		//Atualiza o FB dos nós envolvidos:
		//antigos nó central e filho a direita.
		node.upBalanceFactor();
		nodeRight.upBalanceFactor();;	
	}
	
	/**
	 * Realiza uma dupla rotação a direita no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void doubleRightRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rotação a esquerda 
		//no filho a esquerda do nó central.
		leftRotation(node.getLeft());
		//Após uma rotação a direita no nó central.
		rightRotation(node);
	}
	
	/**
	 * Realiza uma dupla rotação a esquerda no nó central passado por parâmetro.
	 * @param node Objeto AVLNode(T) que é o nó central da rotação.
	 */
	public void doubleLeftRotation(AVLNode<T> node){
		//Primeiro realiza-se uma rotação a direita 
		//no filho a direita do nó central.
		rightRotation(node.getRight());
		//Após uma rotação a esquerda no nó central.
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
	
	//Sobre-escrita recursiva do método toString (método público).
	@Override
	public String toString() {
		return toString(root, "");
	}
	
	//Sobre-escrita recursiva do método toString (método privado).
	private String toString(AVLNode<T> node, String str) {
		if(node != null){
			str += node.getKey() + "\n" + toString(node.getLeft(), str) + toString(node.getRight(), str);
		}
		return str;
	}
	
}