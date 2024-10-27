package ie.gmit.dip;

import java.util.*;

public class NodeImpl<T> implements Node<T> {
	private Node<T> parent;
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private T data;

	public NodeImpl(Node<T> parent, T data) {
		super();
		this.parent = parent;
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	public Node<T> getParent() {
		return this.parent;
	}

	public List<Node<T>> children() {
		return this.children;
	}

	public void addChild(Node<T> child) {
		children.add(child);
	}

	public void removeChild(Node<T> child) {
		children.remove(child);
	}

	public boolean hasChild(Node<T> child) {
		return  children.contains(child);
	}

	public boolean hasSibling() {
		if (!isRoot()) {
			return siblings().size() > 0;
		}
		return false;
	}

	public List<Node<T>> siblings() {
		List<Node<T>> siblings = parent.children();
		siblings.remove(this);
		
		return siblings;
	}

}
