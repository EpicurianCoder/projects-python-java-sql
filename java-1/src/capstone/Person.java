package capstone;

import java.util.Scanner;

/**
* Contains three non-static variables and represent the different
* individuals assigned to Project objects
*/
public class Person{
	
	/**
	* Sets non-static attribute name for the Person class
	*/
	private String name;
	/**
	* Sets non-static attribute role for the Person class
	*/
	private String role;
	/**
	* Sets non-static attribute details for the Person class
	*/
	private String details;
	
	/**
	* Constructor for Person class
	* 
	* @param name the first and last name for our object
	* @param role string containing the role the object takes
	* @param contactDetails a string containing the contact details
	*/
	public Person(
			String name,
			String role,
			String contactDetails
			) {
		this.name = name;
		this.role = role;
		this.details = contactDetails;
	}
	
	/**
	* Replaces our native toString() response
	* 
	* @return a string to visually display our object data clearly
	*/
	public String toString() {
		String info = "";
		info += ("Name: " + name + "\n");
		info += ("Role: " + role + "\n");
		info += ("Contact Details: " + details + "\n");
		return info;
	}
	
	/**
	* Sets details using user input
	* 
	* @param key the scanner object being passed to our method
	*/
	public void updateDetails(Scanner key) {
		System.out.println("Updated Details for contractor: ");
		String latestDetails = key.nextLine();
		details = latestDetails;
		System.out.println("--Updated Details--\n" + details + "\n");
	}
	
	/**
	* Gets name attribute
	* 
	* @return a string with object's Name attribute
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Gets details attribute
	* 
	* @return a string with object's details attribute
	*/
	public String getDetails() {
		return details;
	}
	
	/**
	* Gets role attribute
	* 
	* @return a string with object's role attribute
	*/
	public String getRole() {
		return role;
	}
	
}
