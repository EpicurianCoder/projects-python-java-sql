package linkedListTest;

class MyLinkedList {

	MyNode head;

	// Static nested class for individual nodes in Linked List
	static class MyNode {

		String dataPoint;
		MyNode next;
		
		// Constructor for for the nested class
		public MyNode (String dataPoint) {
			this.dataPoint = dataPoint;
			this.next = null;
		}
	}
	
	// Searches list and deletes by string value
	public void deleletByKey(String key) {
		MyNode headStorage = head;
		if (head == null) {
			System.out.println("list empty");
			return;
		}
		else if (head.dataPoint == key) {
			head = head.next;
			return;
		}
		else {
			while (head.next != null) {
				// If the next item has the key, make the 'next next' item next instead
				if (head.next.dataPoint == key) {
					head.next = head.next.next;
					// restores our heads
					head = headStorage;
					return;
				}
			head = head.next;
			}
		}
		head = headStorage;
		System.out.println("item not found!");
	}
	
	// Searches list and deletes by string position (index +1)
	public void deleteByPosition(int position) {
		MyNode headStorage = head;
		if (position == 1) {
			head = head.next;
			return;
		}
		int counter = 2;
		// Continues moving head while its not empty or our index
		while (head.next != null && counter != position) {
			head = head.next;
			counter += 1;
		}
		// skips the indexed head if it exists
		if (counter == position) {
			head.next = head.next.next;
		}
		else {
			System.out.println("invalid input");
		}
		head = headStorage;
		}
	
	// inserts at end of list
	public void insert(String insertion) {
		MyNode insertNode = new MyNode(insertion);
		if (head == null) {
			head = insertNode;
		}
		else {
			MyNode endpoint = head;
			// Moves to endpoint
			while (endpoint.next != null) {
				endpoint = endpoint.next;
			}
			endpoint.next = insertNode;
		}
		System.out.println("successfully added!");
	}
	
	public static void printLinkedList(MyLinkedList listLinked) {
		MyNode headStorage = listLinked.head;
		System.out.println("-------");
		while (listLinked.head != null) {
			System.out.println(listLinked.head.dataPoint);
			listLinked.head = listLinked.head.next;
		}
		System.out.println("-------");
		listLinked.head = headStorage;
	}
	
	// Swaps the link direction on each node
	public static void reverseMyLinkedList(MyLinkedList listLinked) {
		MyNode previousNode = null;
		MyNode currentNode = listLinked.head;
		MyNode nextNode = null;
		while (currentNode != null) {
			nextNode = currentNode.next; 		// Stores the "next node" value 
			currentNode.next = previousNode; 	// Changes the "next node" to previous iteration remnants
			previousNode = currentNode 	;		// Places the current head of the list in previous
			currentNode = nextNode; 			// makes the current node the previous "next" value
		}
		listLinked.head = previousNode;
	}
}