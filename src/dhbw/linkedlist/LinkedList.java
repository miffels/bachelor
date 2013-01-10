package dhbw.linkedlist;

public class LinkedList<T> {
	
	private LinkedListNode<T> elements;
	private int count;
	
	private static class LinkedListNode<T> {
		T content;
		LinkedListNode<T> next;
		
		public LinkedListNode(T content) {
			this.content = content;
		}
	}
	
	public LinkedList() {
		this.elements = null;
		this.count = 0;
	}
	
	public void add(T element) {
		LinkedListNode<T> node = new LinkedListNode<T>(element);
		if(this.elements == null) {
			this.elements = node;
		} else {
			this.elements.next = node;
		}
		
		this.count++;
	}
	
	public int getCount() {
		return this.count;
	}
	
}
