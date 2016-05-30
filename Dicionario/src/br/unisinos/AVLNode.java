package br.unisinos;


public class AVLNode <T extends Comparable<T>> implements Comparable<AVLNode<T>>{
	private AVLNode<T> left, right; //Filho a esquerda e filho a direita
	private AVLNode<T> parent; //Pai do nó
	private T key; //Chave
	private int bf; //Fator de balanceamento
	
	/**
	 * Construtor: Cria um novo objeto AVLNode.
	 * @param key Objeto T que será chave do nó.
	 */
	public AVLNode(T key){
		this.key = key;
		this.bf = 0;
	}
	
	/**
	 * Retorna o filho a esqueda do nó.
	 * @return Objeto AVLNode(T).
	 */
	public AVLNode<T> getLeft() {
		return left;
	}
	
	/**
	 * Define um novo filho a esqueda do nó, ja apontando-o como pai.
	 * @param left Objeto AVLNode(T) que será o novo filho a esquerda do nó.
	 */
	public void setLeft(AVLNode<T> left) {
		if(left != null)
			left.setParent(this);
		this.left = left;
	}
	
	/**
	 * Retorna o filho a direita do nó.
	 * @return Objeto AVLNode(T).
	 */
	public AVLNode<T> getRight() {
		return right;
	}
	
	/**
	 * Define um novo filho a direita do nó, ja apontando-o como pai.
	 * @param right Objeto AVLNode(T) que será o novo filho a direita do nó.
	 */
	public void setRight(AVLNode<T> right) {
		if (right != null)
			right.setParent(this);
		this.right = right;
	}
	
	/**
	 * Retorna o pai do nó.
	 * @return Objeto AVLNode(T).
	 */
	public AVLNode<T> getParent() {
		return parent;
	}
	
	/**
	 * Define um novo pai ao nó.
	 * @param parent Objeto AVLNode(T) que será o novo pai do nó.
	 */
	public void setParent(AVLNode<T> parent) {
		this.parent = parent;
	}
	
	/**
	 * Retorna a chave do nó.
	 * @return Objeto (T).
	 */
	public T getKey() {
		return key;
	}
	
	/**
	 * Define uma nova chave ao nó.
	 * @param key Objeto T que será a nova chave do nó.
	 */
	public void setKey(T key) {
		this.key = key;
	}
	
	/**
	 * Retorna o fator de balanceamento do nó.
	 * @return Inteiro.
	 */
	public int getBalanceFactor() {
		return bf;
	}
	
	/**
	 * Define um novo fator de balanceamento ao nó.
	 * @param balanceFactor Inteiro que será o novo fator de balancemento.
	 */
	public void setBalanceFactor(int balanceFactor) {
		this.bf = balanceFactor;
	}
	
	/**
	 * Verifica se o nó é uma folha.
	 * @return Booleano: true se o nó é uma folha, false caso contrário.
	 */
	public boolean isLeaf(){
		if (this.left == null && this.right == null)
			return true;
		return false;
	}
	
	/**
	 * Retorna a altura do nó.
	 * @return Inteiro.
	 */
	public int getHeight(){
		if (isLeaf())
			return 1;
		else if (left == null)
			return right.getHeight()+1;
		else if (right == null)
			return left.getHeight()+1;
		return Math.max(left.getHeight(),right.getHeight()+1);
	}
	
	/**
	 * Atualiza o fator de balanceamento do nó.
	 */
	public void upBalanceFactor(){
		if (left != null && right != null)
			bf = left.getHeight() - right.getHeight();
		else if (left == null && right != null)
			bf = Math.negateExact(right.getHeight()); 
		else if (right == null && left != null)
			bf = left.getHeight();
		else
			bf = 0;
	}
	
	@Override
	public int compareTo(AVLNode<T> o) {
		return this.getKey().compareTo(o.getKey());
	}
	
}