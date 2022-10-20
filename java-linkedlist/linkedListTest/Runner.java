package linkedListTest;

class Runner {
	
	// Creates a LinkedList, populates, uses functions and prints.
	public static void main(String[] args) {
		MyLinkedList listLinked = new MyLinkedList();
		listLinked.insert("546");
		listLinked.insert("256");
		listLinked.insert("889");
		listLinked.insert("1782");
		MyLinkedList.printLinkedList(listLinked);
		MyLinkedList.reverseMyLinkedList(listLinked);
		MyLinkedList.printLinkedList(listLinked);
		// Deletes Node with value "555"
		listLinked.deleletByKey("555");
		MyLinkedList.printLinkedList(listLinked);
		// removes item at position (not index) number 2
		listLinked.deleteByPosition(2);
		MyLinkedList.printLinkedList(listLinked);
	}
	
}