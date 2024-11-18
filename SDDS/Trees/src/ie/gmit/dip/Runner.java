package ie.gmit.dip;

public class Runner {
	public static void main(String[] args) {
		Node<String> root = new NodeImpl<String>(null, "John");
		
		Node<String> paul = new NodeImpl<String>(root, "Paul");
		Node<String> anne = new NodeImpl<String>(root, "Anne");
		
		Node<String> mary = new NodeImpl<String>(paul, "Mary");
		Node<String> pat = new NodeImpl<String>(paul, "Pat");
		
		Node<String> mike = new NodeImpl<String>(anne, "Mike");
		Node<String> cathy = new NodeImpl<String>(anne, "Cathy");
		Node<String> emer = new NodeImpl<String>(anne, "Emer");
		
	}
}
