package capstone;

public class Person {
	
	// Creates attributes for the Person class
	private String name;
	private String role;
	private String details;
	
	// Creates the objects from the arguments given
	public Person(
			String name,
			String role,
			String contactDetails
			) {
		this.name = name;
		this.role = role;
		this.details = contactDetails;
	}
	
	// replaces our native toString() response
	public String toString() {
		String info = new String();
		info += ("Name: " + name + "\n");
		info += ("Role: " + role + "\n");
		info += ("Contact Details: " + details + "\n");
		return info;
	}
	
	// replaces the object details with the given argument
	public void updateDetails(String latestDetails) {
		details = latestDetails;
	}
	
	// returns the object name
	public String getName() {
		return name;
	}
	
	// returns the object details
	public String getDetails() {
		return details;
	}
	
	// returns the object role
	public String getRole() {
		return role;
	}
	
}
