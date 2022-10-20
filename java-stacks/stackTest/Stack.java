package stackTest;

import java.util.Arrays;
import java.util.Objects;

public class Stack {

	private int top;
	// Sets initial array size for our dynamic array (min size 5)
	private int[] stackData = new int[5];
	
	// Constructor for the Stack Class
	public Stack(int size) {}

	private Boolean isEmpty() {
		return (top == 0);
	}

	
	// Inserts at 'top' and moves top marker
	public void push(int newEntry) {
		checkSize();
		stackData[top] = newEntry;
		top += 1;
	}
	
	// Returns top and moves top marker

	public int pop() {
		if (isEmpty()) {
			System.out.println("Stack Empty!");
			return 0;
		}
		int target = stackData[top - 1];
		stackData[top -1] = 0;
		checkIfOversize();
		top -= 1;
		return target;
	}

	
	// Returns top item
	public int peek() {
		return stackData[top - 1];
	}
	
	// Doubles the array size if it is full.
	private void checkSize() {
		if (top == stackData.length) {
			int length = stackData.length;
			int[] newStackData = new int[length * 2];
			System.arraycopy(stackData, 0, newStackData, 0, length);
			stackData = newStackData;
		}
	}
	
	
	// Halves the array size if its less than half full.
	private void checkIfOversize() {
		if (top - 1 <= stackData.length/2 && top >5) {
			int length = stackData.length;
			int[] newStackData = new int[length / 2];
			System.arraycopy(Arrays.copyOfRange(stackData, 0, length / 2), 0, newStackData, 0, length/2);
			stackData = newStackData;
		}
	}

	
	// Prints full array with marker details
	public void display() {
		for (int item: stackData) {
			if(Objects.equals(item, stackData[top - 1])) {
				System.out.println(item + "  << CURRENT TOP");
			}
			else {
				System.out.println(item);
			}
		}
	}
}