package stackTest;

public class Runner {

	// Simple program creating stack, displays methods and printing with markers
	public static void main(String[] args) {
		Stack ourStack = new Stack(5);
		ourStack.push(15);
		ourStack.push(21);
		ourStack.push(44);
		ourStack.push(2);
		ourStack.push(34);
		ourStack.display();
		System.out.println("--------------");
		ourStack.push(14);
		ourStack.display();
		System.out.println("--------------");
		System.out.println("Pop >> " + ourStack.pop());
		System.out.println("--------------");
		ourStack.display();
		System.out.println("--------------");
		System.out.println("Pop >> " + ourStack.pop());
		System.out.println("Pop >> " + ourStack.pop());
		System.out.println("Peek >> Shows (" + ourStack.peek() + ")");
		System.out.println("--------------");
		ourStack.display();
	}
}
