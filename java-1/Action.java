package capstone;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;

public class Action {

	public static void main(String[] args) {
		
		// Creates two Array Lists for managing our Person and Project Objects
		ArrayList<Person> personList = new ArrayList<Person>();
		ArrayList<Project> projectList = new ArrayList<Project>();
		// Creates some Person objects and populates our PersonList
		personList.add(new Person("Barry Allen", "architect", "555-9432"));
		personList.add(new Person("Linda Cohen", "architect", "555-9235"));
		personList.add(new Person("Roger Goode", "architect", "555-3423"));
		personList.add(new Person("Holly Hunter", "customer", "555-9435"));
		personList.add(new Person("Bill Baxter", "customer", "555-6534"));
		personList.add(new Person("Jim Hendrix", "customer", "555-2553"));
		personList.add(new Person("Went worthy", "contractor", "555-2534"));
		personList.add(new Person("Pim Cocktails", "contractor", "555-2565"));
		personList.add(new Person("Gerty Rudd", "contractor", "555-2300"));
		
		// Creates a scanner (will later be passed to our methods)
		Scanner key = new Scanner(System.in);
		// Runs our captureProject method once using our person list and scanner)
		projectList.add(captureProject(personList, key));
		// Displays all projects in our projectList
		for(int i = 0; i < projectList.size(); i ++) {
			System.out.println(projectList.get(i));
		}
		// Starts an infinite loop for our menu
		int option;
		while(true) {
			// Gives user 500 milliSeconds to see displayed data before menu loops
			try {
				Thread.sleep(500);
			} 
			catch (InterruptedException ie) {
			    Thread.currentThread().interrupt();
			}
			specialPrint("1. Change Project Due date");
			specialPrint("2. Change Paid Amount");
			specialPrint("3. Ammend Paid Amount");
			specialPrint("4. Finalize the project");
			specialPrint("5. Update Contractor Details");
			specialPrint("6. Display Project Details");
			specialPrint("7. Show Incomplete Projects");
			specialPrint("8. Show Overdue Projects");
			specialPrint("9. Exit");
			// If input cannot be parsed to integer, loop continues to request input
			while(true) {
				try {
					option = Integer.parseInt(key.nextLine());
					break;
				}
				catch(Exception e) {
					System.out.println("Please enter valid option!");
				}
			}
			if(option == 1) {
				System.out.println("Whats the new due date for the project: ");
				String newDeadline;
				while(true) {
					// Loops if an error is thrown for parsing to LocalDate object
					try {
						newDeadline = key.nextLine();
						System.out.println("Captured date: " + LocalDate.parse(newDeadline));
						break;
					}
					catch(Exception e) {
						System.out.println("Error! Please use the FORMAT yyyy-mm-dd (eg.1990-10-27)");
					}
				}
				// Takes the input String from the user and passes it to the method newDeadline()
				projectList.get(0).setDeadline(newDeadline);
			}
			else if(option == 2) {
				System.out.println("Whats the total paid amount for the project: ");
				// Parsed to avoid Scanner glitch not moving to next line on NextDouble()
				double fullAmount;
				while(true) {
					try {
						fullAmount = Double.parseDouble(key.nextLine());
						break;
					}
					catch(Exception e) {
						System.out.println("Please enter valid input!");
					}
				}
				// sets a new amount
				projectList.get(0).replacePaidAmount(fullAmount);
			}
			else if(option == 3) {
				System.out.println("What new amount to append to the paid amount: ");
				double amendment;
				// catches input that can't be parsed to double
				while(true) {
					try {
						amendment = Double.parseDouble(key.nextLine());
						break;
					}
					catch(Exception e) {
						System.out.println("Please enter valid input!");
					}
				}
				// passes amendment as an argument that it adds to the stored value
				projectList.get(0).amendPaidAmount(amendment);
			}
			else if(option == 4) {
				// Method inserts current date and changes boolean value of .isFinalized
				projectList.get(0).finalizeProject();
			}
			else if(option == 5) {
				System.out.println("Updated Details for contractor: ");
				// Displays a sub menu in which all the matching Persons on file are displayed
				// or the user can choose to create a new instance.
				String latestDetails = key.nextLine();
				projectList.get(0).getContractor().updateDetails(latestDetails);
				System.out.println("--Updated Details--\n" + projectList.get(0).getContractor());
			}
			else if(option == 6) {
				// Prints out the details for all our projects
				for(int i = 0; i < projectList.size(); i ++) {
					System.out.println(projectList.get(i));
				}
			}
			else if(option == 7) {
				// Prints out the details for all our projects
				System.out.println(Project.getIncomplete(projectList) + "\n");
			}
			else if(option == 8) {
				// Prints out the details for all our projects
				System.out.println(Project.getOverdue(projectList) + "\n");
			}
			else if(option == 9) {
				// closes our Scanner and exits our program
				key.close();
				System.exit(0);
			}
			else {
				// Any other options and an error is raised.
				System.out.println("Invalid Choice!");
			}
		}
	}
	
	// Method for capturing a project
	// Requests each argument necessary and stores as relevant name
	public static Project captureProject(ArrayList<Person> personList, Scanner input) {
		// Variables for exception statements
		double totalFee;
		double totalPaid;
		specialPrint("Project Number: ");
		String projectNumber = input.nextLine();
		specialPrint("Project Name: ");
		String projectName = input.nextLine();
		specialPrint("Building Type: ");
		String buildingType = input.nextLine();
		specialPrint("Adress of site: ");
		String adress = input.nextLine();
		specialPrint("ERF reference: ");
		String erf = input.nextLine();
		specialPrint("Total Contract Fee: R ");
		// Exception for potential parsing of incorrect input
		while(true) {
			try {
				totalFee = Double.parseDouble(input.nextLine());
				break;
			}
			catch(Exception e) {
				System.out.println("Please enter valid input!");
			}
		}
		specialPrint("Amount paid to date: R ");
		// Exception for potential parsing of incorrect input
		while(true) {
			try {
				totalPaid = Double.parseDouble(input.nextLine());
				break;
			}
			catch(Exception e) {
				System.out.println("Please enter valid input!");
			}
		}
		specialPrint("Project deadline (FORMAT 1990-10-27): ");
		String projectDeadline;
		while(true) {
			try {
				projectDeadline = input.nextLine();
				System.out.println("Captured date: " + LocalDate.parse(projectDeadline));
				break;
			}
			catch(Exception e) {
				System.out.println("Error! Please use the FORMAT yyyy-mm-dd (eg.1990-10-27)");
			}
		}
		// These Methods all the user to choose from existing Person objects
		// or to create their own.
		// Second argument chooses the role which is searched for in object.
		Person architect= displayChoices(personList, "architect", input);
		Person contractor = displayChoices(personList, "contractor", input);
		Person customer = displayChoices(personList, "customer", input);
		
		// Creates a Project Object from the parameters acquired.
		Project newProject = new Project(
				projectNumber,
				projectName,
				buildingType,
				adress,
				erf,
				totalFee,
				totalPaid,
				projectDeadline,
				architect,
				contractor,
				customer
				);
		// Returns our newly created Projects Object
		return newProject;
	}
	
	// Prints the given input, but in a more appealing visual way.
	// Sleep method requires exception
	public static void specialPrint(String word) {
		for(int i = 0; i < word.length(); i++) {
			try {
				Thread.sleep(25);
			} 
			catch (InterruptedException ie) {
			    Thread.currentThread().interrupt();
			}
			System.out.print(word.charAt(i));
		}
		System.out.print("\n");
	}
	
	// This method asks a user whether they would like to choose an edisiting objects
	//or whether they would like to create a new Person object.
	public static Person displayChoices(ArrayList<Person> personList, String role, Scanner key) {
		// Creates our more accessible variables for our user input
		int choice;
		int roleChoice;
		// Creates menu loop
		while (true) {
			System.out.println("1. Choose existing " + role);
			System.out.println("2. Create new " + role);
			while(true) {
				// Loops if there is an exception while parsing
				try {
					choice = Integer.parseInt(key.nextLine());
					break;
				}
				catch(Exception e) {
					System.out.println("Please enter valid option!");
				}
			}
			if (choice == 1) {
				System.out.println("Please choose your " + role + ", using the reference number: ");
				// Prints out a list of all the objects which match the given "role" attribute
				int count = 1;
				// Creates a temporary list so we can access smaller list numerically.
				// Count defines the index of the new list
				ArrayList<Person> temp = new ArrayList<Person>();
				for(int i = 0; i < personList.size(); i++) {
					if(personList.get(i).getRole() == role) {
						specialPrint((count) + ": " +  personList.get(i).getName());
						temp.add(personList.get(i));
						count += 1;
					}
				}
				while(true) {
					// Loops if there is an exception while parsing
					try {
						roleChoice = Integer.parseInt(key.nextLine());
						break;
					}
					catch(Exception e) {
						System.out.println("Please enter valid option!");
					}
				}
				// our user input is used to return the index of the new list of specified roles
				return temp.get(roleChoice-1);
			}
			// takes name and details and creates a new object using the "role" as 3rd argument
			else if (choice == 2) {
				System.out.print(role + " Name: ");
				String name = key.nextLine();
				
				System.out.print(role + " Details: ");
				String details = key.nextLine();
				return (new Person(name, role, details));
			}
			else {
				System.out.print("Please provide Valid option!");
			}
		}
	}
	
}
		