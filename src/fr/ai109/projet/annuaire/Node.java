package fr.ai109.projet.annuaire;

public class Node {
	
	private Trainee[] trainee;
	private Node left, right;
	
	public Node() {
		
	}

	public Node(Trainee[] trainee, Node left, Node right) {
		super();
		this.trainee = trainee;
		this.left = left;
		this.right = right;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
}
