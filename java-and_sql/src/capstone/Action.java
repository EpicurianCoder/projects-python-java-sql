package capstone;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* Contains our main program. Executes the menu and contains numerous functions
* for manipulating Project and Person object data stored in files
*/
public class Action {
	
	/**
	* Loops through a menu that interacts with out list of Projects
	*
	* @param args standard arguments for main()
	*/
	public static void main(String[] args) {
		// Creates two Array Lists for managing our Person and Project Objects
		ArrayList<Person> personList;
		ArrayList<Project> projectList;
		ArrayList<Erf> erfList;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/library_db?useSSL=false",
			        "reguser",
			        "blackhat"
			        );
			statement = connection.createStatement();
			int assignDatabase = statement.executeUpdate("USE PoisePMS");
				if (assignDatabase >= 0) {
			System.out.println("PoisePMS Database Successfully Selected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot Connect to database");
			System.exit(0);
		}
		// Creates a scanner (will later be passed to our methods)
		Scanner key = new Scanner(System.in);
		personList = pullPeople(statement);
		erfList = pullErfs(statement);
		projectList = pullProjects(personList, statement, erfList);
		// siteList = pullSites(connection, statement); // Add this method to populate the ERFs
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
					secondaryMenu(personList, projectList, key, current, connection, statement);
					updateProjects(projectList, current, connection, statement); // UPDATE VIA DATABASE
				break;
				case 2:
					System.out.println(Project.getIncomplete(projectList) + "\n");
					break;
				case 3:
					System.out.println(Project.getOverdue(projectList) + "\n");
					break;
				case 4:
					projectList.add(captureProject(personList, key, connection, statement));
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
					secondaryMenu(personList, projectList, key, chosenProject, connection, statement);
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
			int search = getValidInt(key);
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
	* @param statement interface for our SQL statements
	* @param connection mySQL database connection object
	*/
	private static void secondaryMenu(
										ArrayList<Person> personList, 
										ArrayList<Project> projectList, 
										Scanner key, Project current, 
										Connection connection,
										Statement statement
										) {
		specialPrint("1. Change Project Due date");
		specialPrint("2. Change Paid Amount");
		specialPrint("3. Ammend Paid Amount");
		specialPrint("4. Finalize the project");
		specialPrint("5. Update Contractor Phone");
		specialPrint("6. Update Contractor Email");
		specialPrint("7. Update Contractor Adress");
		specialPrint("8. Display Project Details");
		specialPrint("9. Previous Menu");
		int choice = getValidInt(key);
		switch(choice) {
			case 1:
				System.out.println("Whats the new due date for the project: ");
				String newDeadline = getValidDate(key);
				current.setDeadline(newDeadline);
				updateProjects(projectList, current, connection, statement);
				break;
			case 2:
				System.out.println("Whats the total paid amount for the project: ");
				double fullAmount = getDoubleInput(key);
				current.replacePaidAmount(fullAmount);
				updateProjects(projectList, current, connection, statement);
				break;
			case 3:
				System.out.println("What new amount to append to the paid amount: ");
				double amendment = getDoubleInput(key);
				current.amendPaidAmount(amendment);
				updateProjects(projectList, current, connection, statement);
				break;
			case 4:
				// If project has an outstanding amount, method will generate an invoice
				current.finalizeProject(statement);
				updateProjects(projectList, current, connection, statement);
				break;
			case 5:
				current.getContractor().setPhone(key); // method moving to ACTION.java
				updatePeople(personList, current.getContractor(), connection, statement); 
				break;
			case 6:
				current.getContractor().setEmail(key); // method moving to ACTION.java
				updatePeople(personList, current.getContractor(), connection, statement);
				break;
			case 7:
				current.getContractor().setAdress(key); // method moving to ACTION.java
				updatePeople(personList, current.getContractor(), connection, statement);
				break;
			case 8:
				// Prints out the details for project
				System.out.println(current);
				break;
			case 9:
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
	* @param statement interface for SQL statements
	* @param current Project object which we want to perform actions on
	* @param connection mySQL database connection objects
	*/
	private static void updateProjects(
			ArrayList<Project> projectList, 
			Project current, 
			Connection connection, 
			Statement statement
			) {
		try {
			String[] projectsArgs = current.formatData();
			int rowsAffected = statement.executeUpdate(
            		"UPDATE site_Details SET "
                            + "erf = '" + projectsArgs[4] + "', "
                            + "adress = '" + projectsArgs[2] + "', "
                            + "building_type = '" + projectsArgs[3] + "' "
                            + "WHERE erf = '" + projectsArgs[4] + "'"
                            );
			if(rowsAffected > 0) {
            	System.out.println("site_details TABLE updated successfully!");
            }
            System.out.println("Query complete, " + rowsAffected + " rows added.");
			rowsAffected = statement.executeUpdate(
                    "UPDATE project_info SET "
                    + "proj_num = '" + projectsArgs[0] + "', "
                    + "proj_name = '" + projectsArgs[1] + "', "
                    + "erf = '" + projectsArgs[4] + "', "
                    + "total_fee = '" + projectsArgs[5] + "', "
                    + "total_paid = '" + projectsArgs[6] + "', "
                    + "proj_deadline = '" + projectsArgs[7] + "', "
                    + "proj_completed = '" + projectsArgs[13] + "', "
                    + "architect_name = '" + projectsArgs[8] + "', "
                    + "contractor_name = '" + projectsArgs[9] + "', "
                    + "customer_name = '" + projectsArgs[10] + "', "
                    + "proj_manager_name = '" + projectsArgs[11] + "', "
                    + "engineer_name = '" + projectsArgs[12] + "' "
                    + "WHERE proj_num = " + Integer.parseInt(projectsArgs[0])
                    );
	            if(rowsAffected > 0) {
	            	System.out.println("project_info TABLE updated successfully!");
	            }
	            
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Saves all of the Person objects in the argument to the PERSONFILE
	*
	* @param personList all of our Person objects stored as a list
	* @param statement interface for our SQL statements
	* @param current Person object which we want to update
	* @param connection mySQL database connection object
	*/
	private static void updatePeople(
			List<Person> personList, 
			Person current, 
			Connection connection, 
			Statement statement
			) {
		try {
            int rowsAffected = 0;
            rowsAffected = statement.executeUpdate(
					"UPDATE person_details SET "
                            + "name = '" + current.getName() + "', "
                            + "role = '" + current.getRole() + "', "
                            + "phone = '" + current.getPhone() + "', "
                            + "email = '" + current.getEmail() + "', "
                            + "adress = '" + current.getAdress() + "' "
                            + "WHERE name = '" + current.getName() + "'"
                            );

			System.out.println("Query complete, " + rowsAffected + " rows added.");
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Gets all the data from the PROJECTFILE and returns list of created objects
	*
	* @param statement interface for our SQL statements
	* @param erfList list of Erf objects
	* @param personList list of Person objects
	* @return list of Project objects
	*/
	private static ArrayList<Project> pullProjects(
			ArrayList<Person> personList, 
			Statement statement, 
			ArrayList<Erf> erfList
			) {
		ArrayList<Project> projectsAll =  new ArrayList<>();
		try{
			ResultSet results = statement.executeQuery("SELECT * FROM project_info");
            // Loop over the results, printing them all.
            while (results.next()) {
            	projectsAll.add(new Project(
            			results.getInt("proj_num"),
            			results.getString("proj_name"),
            			erfDetails(results.getString("erf"), erfList, 1),
            			erfDetails(results.getString("erf"), erfList, 2),
            			results.getString("erf"),
            			results.getDouble("total_fee"),
            			results.getDouble("total_paid"),
            			results.getDate("proj_deadline").toString(),
            			toPerson(results.getString("architect_name"), personList),
            			toPerson(results.getString("contractor_name"), personList),
            			toPerson(results.getString("customer_name"), personList),
            			toPerson(results.getString("engineer_name"), personList),
            			toPerson(results.getString("proj_manager_name"), personList),
            			results.getString("proj_completed")
            			));
            }
			return projectsAll;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	* Gets the relative values from within the Erf objects from with in the list of
	* Erf objects
	*
	* @param erfList array of Erf objects
	* @param erf string containing the name of the erf
	* @param choice integer containing our desired choice of Adress/BuildingType
	* @return a list of Project objects
	*/
	public static String erfDetails(String erf, ArrayList<Erf> erfList, int choice) {
		for(int i =0; i < erfList.size(); i ++) {
			if(Objects.equals(erf, erfList.get(i).getErf())) {
				if(choice == 1) {
					return erfList.get(i).getBuildingType();
				}
				else if(choice == 2) {
					return erfList.get(i).getAdress();
				}
				else System.out.println("Error - 'choice' arguement input: erfDetails()");
			}
			}
			
		System.out.println("error fetching Details from Erf Object");
		return "";
	}
	
	/**
	* Reads the person_details table and create Person objects from data
	* 
	* @param statement interface for our SQL statements
	* @return an ArratList of Person objects from file
	*/
	private static ArrayList<Person> pullPeople(Statement statement) {
		ArrayList<Person> peopleAll =  new ArrayList<>();
		try{
            ResultSet results = statement.executeQuery("SELECT * FROM person_details");
            // Loop over the results, printing them all.
            while (results.next()) {
            	peopleAll.add(new Person(
            			results.getString("name"),
            			results.getString("role"),
            			results.getString("phone"),
            			results.getString("email"),
            			results.getString("adress")
            			));
            }
			return peopleAll;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	* Reads the site_details table and create Erf objects from data
	* 
	* @param statement interface for our SQL statements
	* @return an ArratList of Erf objects from file
	*/
	private static ArrayList<Erf> pullErfs(Statement statement) {
		ArrayList<Erf> erfAll =  new ArrayList<>();
		try{
            ResultSet results = statement.executeQuery("SELECT * FROM site_details");
            // Loop over the results, printing them all.
            while (results.next()) {
            	erfAll.add(new Erf(
            			results.getString("erf"),
            			results.getString("adress"),
            			results.getString("building_type")
            			));
            }
			return erfAll;
		}
		catch (SQLException e) {
			e.printStackTrace();
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
		return new Person(name, "unknown", "unknown", "unknown", "unknown");
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
	* @param statement interface to pass SQL statements
	* @param connection object connecting our SQL database
	* @return a Projects object using the captured data
	*/
	private static Project captureProject(
										List<Person> personList,
										Scanner input, Connection connection, 
										Statement statement
										) {
		specialPrint("Project Number: ");
		int projectNumber = getValidInt(input);
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
		Person architect = displayChoices(personList, "architect", input, statement);
		Person contractor = displayChoices(personList, "contractor", input, statement);
		Person customer = displayChoices(personList, "customer", input, statement);
		Person manager = displayChoices(personList, "proj_mananger", input, statement);
		Person engineer = displayChoices(personList, "engineer", input, statement);
		// If projectName field is blank, create it using Last name and buildingType
		if(Objects.equals(projectName, null)) {
			projectName = customer.getName().split(" ")[1] + " " + buildingType;
		}
		try {
			int rowsAffected = statement.executeUpdate(
					 "INSERT INTO Site_details VALUES ('"
			                    + erf + "', '"
			                    + adress + "', '"
			                    + buildingType + "')"
			                    );
			System.out.println("Query complete, " + rowsAffected + " rows added.");
			rowsAffected = statement.executeUpdate(
			        "INSERT INTO project_info VALUES ('"
			        + projectNumber + "','"
			        + projectName + "','"
			        + erf + "','"
			        + totalFee + "','"
			        + totalPaid + "','"
			        + projectDeadline + "','incomplete','" // correct this... should be completionDate not DueDate
			        + architect.getName() + "','"
			        + contractor.getName() + "','"
			        + customer.getName() + "','"
			        + manager.getName() + "','"
			        + engineer.getName() + "')"
			        );
			System.out.println("Query complete, " + rowsAffected + " rows added.");
		} catch (SQLException e) {
			e.printStackTrace();
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
							manager,
							engineer,
							"incomplete" // CompletionDate = null??
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
	* @param statement interface for our SQL statements
	* @return a Person object, either newly created or existing
	*/
	public static Person displayChoices(
										List<Person> personList, 
										String role, 
										Scanner key,
										Statement statement
										) {
		int choice;
		int roleChoice;
		while (true) {
			// Allows the user to capture a new user or allows to choose existing
			// IF the list contains objects with matching role
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
				return capturePerson(personList, role, key, statement);
			}
			else {
				badInput();
			}
		}
	}

	/**
	* Requests each variable required and returns Person object
	* 
	* @param key the scanner object being used
	* @param personList all of our Person objects stored as a list
	* @param statement interface object to pass SQL statements
	* @param role string containing the role of a Person object
	* @return a Person object using the captured data
	*/
	private static Person capturePerson(List<Person> personList, String role, Scanner key, Statement statement) {
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
		System.out.print(role + " Phone: ");
		String phone = key.nextLine();
		System.out.print(role + " Email: ");
		String email = key.nextLine();
		System.out.print(role + " Adress: ");
		String adress = key.nextLine();
		personList.add(new Person(name, role, phone, email, adress));
		int rowsAffected = 0;
		try {
			rowsAffected = statement.executeUpdate(
					 "INSERT INTO person_details VALUES ('"
			                    + name + "', '"
			                    + role + "', '"
			                    + phone + "', '"
			                    + email + "', '"
			                    + adress + "')"
			                    );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Query complete, " + rowsAffected + " rows added.");
		return (new Person(name, role, phone, email, adress));
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