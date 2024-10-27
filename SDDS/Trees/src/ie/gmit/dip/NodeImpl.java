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
		return new ArrayList<Node<T>>(this.children);
	}

	public void addChild(Node<T> child) {
		if (indexOf(child) == -1) children.add(child);
	}

	public void removeChild(Node<T> child) {
		int index = indexOf(child);
		if (index >= 0)
			children.remove(index);

	}
	
	private int indexOf(Node<T> child) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getData().equals(child.getData()))
				return i;
		}
		return  -1;
	}

	public boolean hasChild(Node<T> child) {
		return indexOf(child) >= 0;
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
