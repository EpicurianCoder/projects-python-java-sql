package capstone;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
* Contains numerous non-static variables and represents the data
* stored for each Project the company has on file
*/
public class Project {
	
	/**
	* Sets non-static attribute role for the Project class
	*/
	private int projectNumber; 
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String projectName; 
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String buildingType;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String adress;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String erf;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private double totalFee;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private double totalPaid;
	/**
	* Date Format = "YYYY-MM-DD". (java.time.LocalDate)
	*/
	private String projectDeadline;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private Person architect;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private Person contractor;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private Person customer;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private Person engineer;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private Person proj_manager;
	/**
	* Sets non-static attribute role for the Project class
	*/
	private String completionDate;
	
	/**
	* Constructor for Project class
	*
	* @param projectNumber for reference
	* @param projectName as title
	* @param buildingType for type of building
	* @param adress physical site address
	* @param erf the ERF number of the plot
	* @param totalFee the total amount being charged
	* @param totalPaid the amount paid to date
	* @param projectDeadline format is LocalDate compatible
	* @param architecht Person object with architect role
	* @param contractor Person object with contractor role
	* @param customer Person object with customer role
	* @param proj_manager Person object with project manager role
	* @param engineer Person object with structural engineer role
	* @param completionDate format is LocalDate compatible
	*/
	public Project(
			int projectNumber,
			String projectName,
			String buildingType,
			String adress,
			String erf,
			double totalFee,
			double totalPaid,
			String projectDeadline,
			Person architecht,
			Person contractor,
			Person customer,
			Person engineer,
			Person proj_manager,
			String completionDate
			) {
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.buildingType = buildingType;
		this.adress = adress;
		this.erf = erf;
		this.totalFee = totalFee;
		this.totalPaid = totalPaid;
		this.projectDeadline = projectDeadline;
		this.architect = architecht;
		this.contractor = contractor;
		this.customer = customer;
		this.engineer = engineer;
		this.proj_manager = proj_manager;
		this.completionDate = completionDate;
	}
	
	/**
	* Appends amount passed to the totalPaid amount
	*
	* @param payment amount to be appended to totoalPaid attribute
	*/
	public void amendPaidAmount(double payment) {
		totalPaid += payment;
		System.out.println("Amount amended successfully!\n");
	}
	
	/**
	* Creates an array and populates each index with an attribute
	* that is of type string.
	*
	* @return an array with each Project attribute as string
	*/
	public String[] formatData() {
		String[] all = new String[14];
		all[0] = Integer.toString(projectNumber);
		all[1] = projectName;
		all[2] = buildingType;
		all[3] = adress;
		all[4] = erf;
		all[5] = String.valueOf(totalFee);
		all[6] = String.valueOf(totalPaid);
		all[7] = projectDeadline;
		all[8] = architect.getName();
		all[9] = contractor.getName();
		all[10] = customer.getName();
		all[11] = customer.getName();
		all[12] = customer.getName();
		all[13] = completionDate;
		return all;
	}
	
	/**
	* Returns the Name and Number of a project for simple display purposes
	*
	* @return string with projectName and projectNumber combined
	*/
	public String getSimpleDetails() {
		return projectName + ", " + projectNumber + "\n";
	}
	
	/**
	* Returns the Completion Date of a project
	*
	* @return string with Completion Date of a project
	*/
	public String getCompletionDate() {
		return completionDate;
	}
	
	/**
	* Sets totalPaid attribute value
	*
	* @param payment amount to be appended to totoalPaid attribute
	*/
	public void replacePaidAmount(double payment) {
		totalPaid = payment;
		System.out.println("Amount updated successfully!\n");
	}
	
	/**
	* Gets isFinalized attribute value
	*
	* @return boolean for isFinalized attribute
	*/
	public boolean getFinalized() {
		if (completionDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* Sets totalFee attribute value
	*
	* @param newTotalFee amount to replay totalFee value
	*/
	public void setTotalFee(double newTotalFee) {
		totalFee = newTotalFee;
		System.out.println("Amount updated successfully!\n");
	}
	
	/**
	* Sets Deadline attribute value
	*
	* @param newDeadline a string formatted as LocalDate item
	*/
	public void setDeadline(String newDeadline) {
		projectDeadline = newDeadline;
		System.out.println("Deadline updated successfully!\n");
	}
	
	/**
	* Gets the contractor Person object
	*
	* @return Person object stored in contractor attribute
	*/
	public Person getContractor() {
		return contractor;
	}
	
	/**
	* Gets the architect Person object
	*
	* @return Person object stored in architect attribute
	*/
	public Person getArchitect() {
		return architect;
	}
	
	/**
	* Gets the customer Person object
	*
	* @return Person object stored in customer attribute
	*/
	public Person getCustomer() {
		return customer;
	}
	
	/**
	* Gets the customer Person object
	*
	* @return Person object stored in customer attribute
	*/
	public Person getProjectManager() {
		return proj_manager;
	}
	
	/**
	* Gets the customer Person object
	*
	* @return Person object stored in customer attribute
	*/
	public Person getEngineer() {
		return engineer;
	}
	
	/**
	* Gets projectName attribute
	*
	* @return string is the projectName for the object
	*/
	public String getName() {
		return projectName;
	}
	
	/**
	* Gets the projectNumber attribute
	*
	* @return string is the projectNumber for the object
	*/
	public int getNumber() {
		return projectNumber;
	}
	
	/**
	* Gets the outstanding amount of totalFee - totalPaid
	*
	* @return the difference between totalFee and totalPaid as double
	*/
	public double getOutstanding() {
		return totalFee - totalPaid;
	}
	
	/**
	* Generates and exports and invoice using the outstandingFee amount
	* 
	* @param statement is the interface object for the SQL interface opened
	*/
	private void generateInvoice(Statement statement) {
		// create invoice using the details and amount calculated
		double outstandingFee = totalFee - totalPaid;
		if(outstandingFee > 0) {
			try {
				int rowsAffected = statement.executeUpdate(
						"UPDATE completed_projects SET "
	                            + "invoice_necessary = 1 "
	                            + "WHERE project_num = '" + projectNumber + "'"
	                            );
				if(rowsAffected > 0) {
					System.out.println("Table completed_projects successfully updated!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* Generates an "invoice" and creates completed_projects entry, and then if 
	* no outstanding amount, changes the invoice_necessary value.
	* 
	* @param statement is the interface object for the SQL interface openend
	*/
	public void finalizeProject(Statement statement) {
		if (!Objects.equals(completionDate, "incomplete")) {
			System.out.println("Error: Already finalized!\n");
			return;
		}
		this.completionDate = java.time.LocalDate.now().format(DateTimeFormatter.ISO_DATE); 
		// Calculates the outstanding amount
		double outstandingFee = totalFee - totalPaid;
		int rowsAffected = 0;
		try {
			rowsAffected = statement.executeUpdate(
					"INSERT into completed_projects VALUES ('"
							+ projectNumber + "', '"
							+ completionDate  + "', "
							+ getOutstanding() + ", "
			                + "invoice_necessary = 0)"
			                );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rowsAffected > 0) {
			System.out.println("Table completed_projects successfully updated!");
		}
		System.out.print("Account Status: ");
		if(outstandingFee > 0) {
			String stripDate = completionDate.replace("-", "");
			String invoiceNumber = projectNumber + "_" + stripDate;
			System.out.println("Pending Payment (Invoice number is " + invoiceNumber + ")");
			System.out.println("*** Outstanding fee is " + outstandingFee + " ***");
			generateInvoice(statement);
		}
		else {
			System.out.println("Settled!");
		}
		System.out.println("\nProject Finalized!\n");
	}
	
	/**
	* Returns a string with project name and number for the objects not 
	* marked as complete
	* 
	* @param projectList is a list of all Project objects
	* @return string with simple details of all incomplete projects
	*/
	public static String getIncomplete(List<Project> projectList) {
	 	StringBuilder unfinishedProjects = new StringBuilder();
	 	unfinishedProjects.append("-- Incomplete Project Name and Project Number --\n");
	 	for(int i = 0; i < projectList.size(); i++) {
	 		// Checks if they are marked as finalized
	 		if(!projectList.get(i).getFinalized()) {
	 			// Adds the simple details of the project to the string for display
	 			unfinishedProjects.append(projectList.get(i).getSimpleDetails());
	 		}
	 	}
	 	return unfinishedProjects.toString();
	}
	
	/**
	* Returns a string with project name and number for the objects not 
	* marked as complete AND whose deadlines are passed
	* 
	* @param projectList is a list of all Project objects
	* @return string with simple details of all incomplete projects
	*/
	public static String getOverdue(List<Project> projectList) {
		StringBuilder overdueProjects = new StringBuilder();
		overdueProjects.append("-- Overdue Project Name and Project Number --\n");
		for(int i = 0; i < projectList.size(); i++) {
			//  Compares due date to current date using LocatDate format and compareTo()
			if(!projectList.get(i).getFinalized() && (LocalDate.parse(
												projectList.get(i).projectDeadline))
												.compareTo(java.time.LocalDate.now()) < 0
												) {
				overdueProjects.append(projectList.get(i).getSimpleDetails());
			}
		}
		return overdueProjects.toString();
	}
	
	/**
	* Overrides the toString method and returns a neat data
	* 
	* @return string with all projects attributes values for easy display
	*/
	public String toString() {
		return (
				"projectNumber: " + projectNumber + "\n"
				+ "projectName: " + projectName + "\n"
				+ "buildingType: " + buildingType + "\n"
				+ "adress: " + adress + "\n"
				+ "erf: " + erf + "\n"
				+ "totalFee: " + totalFee + "\n"
				+ "totalPaid: " + totalPaid + "\n"
				+ "projectDeadline: " + projectDeadline + "\n"
				+ "architecht: " + architect.getName() + "\n"
				+ "contractor: " + contractor.getName() + "\n"
				+ "customer: " + customer.getName() + "\n"
				// + "isFinalized: " + isFinalized + "\n" fix this!!
				+ "completionDate: " + completionDate + "\n"
						);
	}

}


