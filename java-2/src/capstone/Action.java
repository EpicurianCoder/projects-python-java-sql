package capstone;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;

/**
* Contains our main program. Executes the menu and contains numerous functions
* for manipulating Project and Person object data stored in files
*/
public class Action {

	/**
	* String values for the fixed storage location of Project data.
	*/
	public static final String PROJECTFILE = "src/resources/projects.txt";
	/**
	* String values for the fixed storage location of Person data.
	*/
	public static final String PERSONFILE = "src/resources/persons.txt";
	/**
	* String values for the fixed storage location for complete projects data.
	*/
	public static final String COMPLETEDFILE = "src/resources/completed.txt";
	
	/**
	* Loops through a menu that interacts with out list of Projects
	*
	* @param args standard arguments for main()
	*/
	public static void main(String[] args) {
		// Creates two Array Lists for managing our Person and Project Objects
		ArrayList<Person> personList;
		ArrayList<Project> projectList;
		// Creates a scanner (will later be passed to our methods)
		Scanner key = new Scanner(System.in);
		personList = pullPeople();
		projectList = pullProjects();
		// Starts an infinite loop for our menu
		boolean x = true;
		while(x) {
			// Gives user 500 milliSeconds to see displayed data before menu loops
			pauseTemp(500);
			specialPrint("1. View all projects");
			specialPrint("2. Show Incomplete Projects");
			specialPrint("3. Show Overdue Projects");
			specialPrint("4. Capture New Project");
			specialPrint("5. Search Project Name or Project Number");
			specialPrint("6. Exit");
			// If input cannot be parsed to integer, loop continues to request input
			int option = getValidInt(key);
			switch(option) {
				case 1:
					if(projectList.isEmpty()) {
						System.out.println("Currently no projects available!\n");
						break;
					}
					// Displays all projects in stored data
					for(int i = 0; i < projectList.size(); i ++) {
						System.out.println(
											(i + 1) + ": " 
											+ projectList.get(i).getSimpleDetails()
											);
					}
					// Gets a valid integer from our user
					int projectSelected = getValidInt(key) - 1;
					// Stops an attempt to choose a project outside of the range
					if((projectSelected + 1) > projectList.size()) {
						badInput();
						break;
					}
					Project current = projectList.get(projectSelected);
					// Takes our user into the menu that deals with each project
					secondaryMenu(personList, projectList, key, current);
					updateProjects(projectList);
				break;
				case 2:
					System.out.println(Project.getIncomplete(projectList) + "\n");
					break;
				case 3:
					System.out.println(Project.getOverdue(projectList) + "\n");
					break;
				case 4:
					projectList.add(captureProject(personList, key));
					updateProjects(projectList);
					break;
				case 5:
					System.out.println("1. Search by Project Name: ");
					System.out.println("2. Search by Project Number: ");
					System.out.println("3. Exit ");
					int selection = getValidInt(key);
					Project chosenProject = null;
					if(Objects.equals(1, selection)) {
						chosenProject = searchByName(projectList, key);
					}
					else if(Objects.equals(2, selection)) {
						chosenProject = searchByNumber(projectList, key);
					}
					else {
						break;
					}
					if(Objects.equals(null, chosenProject)) {
						break;
					}
					System.out.println(
										"You have selected the Project > " 
										+ chosenProject.getSimpleDetails()
										);
					secondaryMenu(personList, projectList, key, chosenProject);
					break;
				case 6:
					// closes our Scanner and exits our program
					key.close();
					x = false;
					break;
				default:
					badInput();
					break;
			}
		}
	}
	
	/**
	* Searches argument list, returns projects with name matching user input
	*
	* @param projectList all of our projects stored as a list.
	* @param key the scanner object being used
	* @return the Project instance it finds in the list
	*/
	private static Project searchByName(ArrayList<Project> projectList, Scanner key) {
			System.out.println("Enter Project Name: ");
			String search = key.nextLine();
			for(int i = 0; i < projectList.size(); i++) {
				if(Objects.equals(search, projectList.get(i).getName())) {
					return projectList.get(i);
				}
			}
			System.out.println("Item not found!");
			return null;
	}
	
	/**
	* Searches argument list, returns projects with number matching user input
	*
	* @param projectList all of our projects stored as a list.
	* @param key the scanner object being used
	* @return the Project instance it finds in the list
	*/
	private static Project searchByNumber(ArrayList<Project> projectList, Scanner key) {
			System.out.println("Enter Project Number: ");
			String search = key.nextLine();
			for(int i = 0; i < projectList.size(); i++) {
				if(Objects.equals(search, projectList.get(i).getNumber())) {
					return projectList.get(i);
				}
			}
			System.out.println("Item not found!");
			return null;
	}
	
	/**
	* Loops through a menu performing actions on individual project objects
	* 
	* @param projectList all of our Project objects stored as a list
	* @param personList all of our Person objects stored as a list
	* @param key the scanner object being used
	* @param current Project instance we wish to perform actions on
	* @throws IOException if saveCompleted() method fails to open 
	* or save to file
	*/
	private static void secondaryMenu(
										ArrayList<Person> personList, 
										ArrayList<Project> projectList, 
										Scanner key, Project current
										) {
		specialPrint("1. Change Project Due date");
		specialPrint("2. Change Paid Amount");
		specialPrint("3. Ammend Paid Amount");
		specialPrint("4. Finalize the project");
		specialPrint("5. Update Contractor Details");
		specialPrint("6. Display Project Details");
		specialPrint("7. Previous Menu");
		int choice = getValidInt(key);
		switch(choice) {
			case 1:
				System.out.println("Whats the new due date for the project: ");
				String newDeadline = getValidDate(key);
				// Replaces the projects deadline with the newDeadline string
				current.setDeadline(newDeadline);
				updateProjects(projectList);
				break;
			case 2:
				System.out.println("Whats the total paid amount for the project: ");
				double fullAmount = getDoubleInput(key);
				// Replaces the projects PaidAmount value with the fullAmount string
				current.replacePaidAmount(fullAmount);
				updateProjects(projectList);
				break;
			case 3:
				System.out.println("What new amount to append to the paid amount: ");
				double amendment = getDoubleInput(key);
				// Argument passed is amended to PaidAmount value stored in object
				current.amendPaidAmount(amendment);
				updateProjects(projectList);
				break;
			case 4:
				// If project has an outstanding amount, method will generate an invoice
				current.finalizeProject();
				updateProjects(projectList);
			try {
				saveCompleted(current);
			} catch (IOException e) {
				System.out.println("Error occured while saving file!");
			}
				break;
			case 5:
				current.getContractor().updateDetails(key);
				updatePeople(personList);
				break;
			case 6:
				// Prints out the details for project
				System.out.println(current);
				break;
			case 7:
				break;
			default:
				badInput();
				break;
		}
	}
	
	/**
	* Saves all of the projects in the argument to the PROJECTFILE
	*
	* @param projectList all of our projects stored as a list.
	* @throws IOException if there's an error creating the formatter
	*/
	private static void updateProjects(ArrayList<Project> projectList) {
		try (Formatter f = new Formatter(PROJECTFILE)) {
			for(int i = 0; i < projectList.size(); i++) {
				String[] projectsArgs = projectList.get(i).formatData();
				// separates each element using a comma
				f.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s%n",
						projectsArgs[0],
						projectsArgs[1],
						projectsArgs[2],
						projectsArgs[3],
						projectsArgs[4],
						projectsArgs[5],
						projectsArgs[6],
						projectsArgs[7],
						projectsArgs[8],
						projectsArgs[9],
						projectsArgs[10],
						projectsArgs[11],
						projectsArgs[12]
								);
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	* Saves all of the Person objects in the argument to the PERSONFILE
	*
	* @param personList all of our Person objects stored as a list
	* @throws IOException if there's an error creating the formatter
	*/
	private static void updatePeople(List<Person> personList) {
		try (Formatter f = new Formatter(PERSONFILE)) {
			for(int i = 0; i < personList.size(); i++) {
				Person current = personList.get(i);
				f.format(
						"%s, %s, %s%n",
						current.getName(),
						current.getRole(),
						current.getDetails()
						);
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	* Gets all the data from the PROJECTFILE and returns list of created objects
	*
	* @throws IOException if there's an error creating the formatter
	* @return a list of Project objects
	*/
	private static ArrayList<Project> pullProjects() {
		ArrayList<Person> personList = pullPeople();
		ArrayList<Project> projectsAll =  new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(PROJECTFILE))) {
			String project = "";
			while((project = reader.readLine()) != null) {
				String[] projectSplit = project.split(", ");
				projectsAll.add(new Project(
						projectSplit[0],
						projectSplit[1],
						projectSplit[2],
						projectSplit[3],
						projectSplit[4],
						Double.parseDouble(projectSplit[5]),
						Double.parseDouble(projectSplit[6]),
						projectSplit[7],
						toPerson(projectSplit[8], personList),
						toPerson(projectSplit[9], personList),
						toPerson(projectSplit[10], personList),
						Boolean.parseBoolean(projectSplit[11]),
						projectSplit[12]
				));
			}
			return projectsAll;
		}
		catch (IOException e) {
			System.out.println("- Project Object Directory is empty\n");
			return new ArrayList<>();
		}
	}
	
	/**
	* Appends a detailed view of Project objects to COMLPLETEDFILE
	* 
	* @param project a Project instance we wish to save
	* @throws IOException if there's an error creating or writing
	* the file
	*/
	private static void saveCompleted(Project project) throws IOException {
			File saveFile = new File(COMPLETEDFILE);
			if(saveFile.createNewFile()) {
				System.out.println("NEW File Created for completed projects!");
			}
			FileWriter file = new FileWriter(saveFile, true);
		    BufferedWriter writer = new BufferedWriter(file);
		    writer.write((project.toString() + "\n"));
			writer.close();
			System.out.println("Save successful!");
	}
	
	/**
	* Reads the PERSONFILE and creates an array of objects
	* 
	* @return an ArratList of Person objects from file
	* @throws IOException if there's an error reading
	* the file or data
	*/
	private static ArrayList<Person> pullPeople() {
		ArrayList<Person> peopleAll =  new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(PERSONFILE))) {
			String person = "";
			while((person = reader.readLine()) != null) {
				String[] personSplit = person.split(", ");
				peopleAll.add(new Person(
						personSplit[0],
						personSplit[1],
						personSplit[2]
											));
			}
			return peopleAll;
		}
		catch (IOException e) {
			System.out.println("- Person Object Directory is empty\n");
			return new ArrayList<>();
		}
	}
	
	/**
	* Takes string argument and returns corresponding object from list passed
	* 
	* @param personList all of our Person objects stored as a list
	* @param name a string taken from our Project object data
	* @return an instance of the Person class found within list
	*/
	private static Person toPerson(String name, ArrayList<Person> personList) {
		for(int i = 0; i < personList.size(); i++) {
			// Compares string value to the Name attribute in Person object
			if(Objects.equals(name, personList.get(i).getName())) { 
				return personList.get(i);
			}
		}
		System.out.println("Error in sync between Project and Person files");
		return new Person(name, "unknown", "unknown");
	}
	
	/**
	* Requests input as string until the value can be parsed to an integer
	* 
	* @param key the scanner object being used
	* @throws NumberFormatException if the wrong type of variable is received
	* @return the integer value of the input from user
	*/
	public static int getValidInt(Scanner key) {
		int option;
		while(true) {
			try {
				option = Integer.parseInt(key.nextLine());
				break;
			}
			catch(NumberFormatException e) {
				badInput();
			}
		}
		return option;
	}
	
	/**
	* User sleep method to delay our program temporarily
	* 
	* @param time an integer value for milliseconds
	* @throws InterruptedException in the event input is received
	* during pause
	*/
	public static void pauseTemp(int time) {
		try {
			Thread.sleep(time);
		} 
		catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	* Requests the date until the value can happily be parsed to a LocalDate object
	* 
	* @param key the scanner object being used
	* @throws Exception if the string received can't be parsed to LocalDate object
	* @return String that conforms to LocalDate formatting
	*/
	public static String getValidDate(Scanner key) {
		String newDeadline;
		while(true) {
			// Loops if an error is thrown for parsing to LocalDate object
			try {
				newDeadline = key.nextLine();
				// Parsing attempted but value not stored.
				System.out.println("Captured date: " + LocalDate.parse(newDeadline));
				break;
			}
			catch(Exception e) {
				System.out.println("Please use the FORMAT yyyy-mm-dd (eg.1990-10-27)");
			}
		}
		return newDeadline;
	}
	
	/**
	* Requests input as string until the value can happily be parsed to an double
	* 
	* @param key the scanner object being used
	* @throws Exception if the string can't be parsed to a double
	* @return a valid variable of type double
	*/
	public static double getDoubleInput(Scanner key) {
		double fullAmount;
		while(true) {
			try {
				fullAmount = Double.parseDouble(key.nextLine());
				break;
			}
			catch(Exception e) {
				badInput();
			}
		}
		return fullAmount;
	}
	
	/**
	* Prints a standard response to input not matching the requested criteria
	*/
	public static void badInput() {
		System.out.println("Please enter valid input!");
	}
	
	/**
	* Requests each variable required and returns Project object
	* 
	* @param input the scanner object being used
	* @param personList all of our Person objects stored as a list
	* @return a Projects object using the captured data
	*/
	private static Project captureProject(
										List<Person> personList,
										Scanner input
										) {
		specialPrint("Project Number: ");
		String projectNumber = noEmptyInput(input);
		specialPrint("Project Name: ");
		String projectName = input.nextLine();
		specialPrint("Building Type: ");
		String buildingType = noEmptyInput(input);
		specialPrint("Adress of site: ");
		String adress = noEmptyInput(input);
		specialPrint("ERF reference: ");
		String erf = noEmptyInput(input);
		specialPrint("Total Contract Fee: R ");
		double totalFee = getDoubleInput(input);
		specialPrint("Amount paid to date: R ");
		double totalPaid = getDoubleInput(input);
		specialPrint("Project deadline (FORMAT 1990-10-27): ");
		String projectDeadline = getValidDate(input);
		// Ask user to choose from existing Person objects or create new ones.
		Person architect = displayChoices(personList, "architect", input);
		Person contractor = displayChoices(personList, "contractor", input);
		Person customer = displayChoices(personList, "customer", input);
		// If projectName field is blank, create it using Last name and buildingType
		if(Objects.equals(projectName, "")) {
			projectName = customer.getName().split(" ")[1] + " " + buildingType;
		}
		// Creates a Project Object from the parameters acquired.
		return new Project(
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
							customer,
							false,
							"incomplete"
							);
	}

	/**
	* Gets a non-empty string from the user
	* 
	* @param input the scanner object being used
	* @return a non empty string
	*/
	private static String noEmptyInput(Scanner input) {
		String attribute = null;
		while(true) {
			attribute = input.nextLine();
			if(Objects.equals(attribute, "")) {
				System.out.println("Blank entry not accepted!");
			}
			else {
				break;
			}
		}
		return attribute;
	}
	
	/**
	* Prints output in a visually pleasing manner using delay on each letter
	* 
	* @param word the string that is to be printed
	*/
	public static void specialPrint(String word) {
		for(int i = 0; i < word.length(); i++) {
			pauseTemp(25);
			System.out.print(word.charAt(i));
		}
		System.out.print("\n");
	}
	
	/**
	* Allows the user to either create a new Person object or choose from existing
	* 
	* @param personList all of our Person objects stored as a list
	* @param role string containing the role we are searching for
	* @param key the scanner object being used
	* @return a Person object, either newly created or existing
	*/
	public static Person displayChoices(
										List<Person> personList, 
										String role, 
										Scanner key
										) {
		int choice;
		int roleChoice;
		while (true) {
			// Allows the user a choice if the list contains objects matching the role
			if(roleExists(personList, role)) {
				System.out.println("1. Create new " + role);
				System.out.println("2. Choose existing " + role);
				choice = getValidInt(key);;
			}
			else {
				choice = 1;
			}
			if (choice == 2) {
				System.out.println(
									"Please choose your " 
									+ role + ", using the reference number: "
									);
				// Count defines the index of the new list
				int count = 1;
				// Creates a temporary list so we can access smaller list numerically.
				// Prints all the objects which match the given "role" attribute
				ArrayList<Person> temp = new ArrayList<>();
				for(int i = 0; i < personList.size(); i++) {
					if(Objects.equals(personList.get(i).getRole(), role)) {
						specialPrint((count) + ": " +  personList.get(i).getName());
						temp.add(personList.get(i));
						count += 1;
					}
				}
				roleChoice = getValidInt(key);
				// input is used to return index of the new list of specified roles
				return temp.get(roleChoice-1);
			}
			// Creates a new Person object
			else if (choice == 1) {
				System.out.print(role + " Name: ");
				String name = "";
				while(true) {
					name = key.nextLine();
					if(!Objects.equals(2, name.split(" ").length)) {
						System.out.println("Please enter a first and last name: ");
					}
					else {
						break;
					}
				}
				System.out.print(role + " Details: ");
				String details = noEmptyInput(key);
				personList.add(new Person(name, role, details));
				updatePeople(personList);
				return (new Person(name, role, details));
			}
			else {
				badInput();
			}
		}
	}

	/**
	* Checks if Person list contains a Role attribute matching the argument passed
	* 
	* @param personList all of our Person objects stored as a list
	* @param role string containing the role we are searching for
	* @return a boolean value of true if list contains any instances with role
	*/
	private static boolean roleExists(List<Person> personList, String role) {
		boolean roleExists = false;
		for(int i = 0; i < personList.size(); i++) {
			if(Objects.equals(personList.get(i).getRole(), role)) {
				roleExists = true;
				break;
			}
		}
		return roleExists;
	}

}