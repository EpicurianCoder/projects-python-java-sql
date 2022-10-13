package capstone;

import java.util.Scanner;

/**
* Contains five non-static variables and represent the different
* individuals assigned to Project objects
*/
public class Person{
	
	/**
	* Sets non-static attribute name for the Person class
	*/
	private String name;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String role;
	/**
	* Sets non-static attribute details for the Person class
	*/
	private String phone;
	/**
	* Sets non-static attribute details for the Person class
	*/
	private String email;
	/**
	* Sets non-static attribute details for the Person class
	*/
	private String adress;
	
	/**
	* Constructor for Person class
	* 
	* @param name the first and last name for our object
	* @param role string containing the role the object takes
	* @param phone a string containing the phone details
	* @param email a string containing the email details
	* @param adress a string containing the adress details
	*/
	public Person(
			String name,
			String role,
			String phone,
			String email,
			String adress
			) {
		this.name = name;
		this.role = role;
		this.phone = phone;
		this.email = email;
		this.adress = adress;
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
		info += ("Phone: " + phone + "\n");
		info += ("Adress: " + adress + "\n");
		info += ("Email: " + email + "\n");
		return info;
	}
	
	/**
	* Sets phone number using user input
	* 
	* @param key the scanner object being passed to our method
	*/
	public void setPhone(Scanner key) {
		printDetailsUpdated();
		String latestDetails = key.nextLine();
		phone = latestDetails;
		System.out.println("--Updated Details--\n" + phone + "\n");
	}

	/**
	* Prints a simple updated details string
	* 
	*/
	private void printDetailsUpdated() {
		System.out.println("Updated Details for contractor: ");
	}
	
	/**
	* Sets phone number using user input
	* 
	* @param key the scanner object being passed to our method
	*/
	public void setEmail(Scanner key) {
		printDetailsUpdated();
		String latestDetails = key.nextLine();
		email = latestDetails;
		System.out.println("--Updated Details--\n" + email + "\n");
	}
	
	/**
	* Sets phone number using user input
	* 
	* @param key the scanner object being passed to our method
	*/
	public void setAdress(Scanner key) {
		printDetailsUpdated();
		String latestDetails = key.nextLine();
		adress = latestDetails;
		System.out.println("--Updated Details--\n" + email + "\n");
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
	public String getEmail() {
		return email;
	}
	
	/**
	* Gets role attribute
	* 
	* @return a string with object's role attribute
	*/
	public String getAdress() {
		return adress;
	}
	
	/**
	* Gets role attribute
	* 
	* @return a string with object's role attribute
	*/
	public String getPhone() {
		return phone;
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
