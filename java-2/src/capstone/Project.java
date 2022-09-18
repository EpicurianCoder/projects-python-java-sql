package capstone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
* Contains numerous non-static variables and represents the data
* stored for each Project the company has on file
*/
public class Project {
	
	private String projectNumber; 
	private String projectName; 
	private String buildingType;
	private String adress;
	private String erf;
	private double totalFee;
	private double totalPaid;
	/**
	* Date Format = "YYYY-MM-DD". (java.time.LocalDate)
	*/
	private String projectDeadline;
	private Person architect;
	private Person contractor;
	private Person customer;
	private boolean isFinalized;
	/**
	* Date Format = "YYYY-MM-DD". (java.time.LocalDate)
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
	* @param isFinalized a boolean to show completeness
	* @param completionDate format is LocalDate compatible
	*/
	public Project(
			String projectNumber,
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
			boolean isFinalized,
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
		this.isFinalized = isFinalized;
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
		String[] all = new String[13];
		all[0] = projectNumber;
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
		all[11] = Boolean.toString(isFinalized);
		all[12] = completionDate;
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
		return isFinalized;
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
	public String getNumber() {
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
	* @throws IOException if there is an error creating or writing file
	*/
	private void generateInvoice() {
		// create invoice using the details and amount calculated
		double outstandingFee = totalFee - totalPaid;
		String content = 
						"Amount Owed: " 
						+ outstandingFee + "\n" 
						+ "Customer Details: "
						+ getCustomer().getDetails();
		String invoiceName = "src/resources/Invoice" + projectNumber + ".txt";
		FileWriter invoice = null;
		try {
			File myFile = new File(invoiceName);
			if(myFile.createNewFile()) {
				System.out.println("File Created!");
			}
			invoice = new FileWriter(invoiceName);
			invoice.write(content);
			invoice.close();
			System.out.println("Invoice Generated and Saved!");
		}
		catch (IOException e) {
		      System.out.println("Error with file!");
		      System.out.println("Invoice not Generated!");
		}
	}
	
	/**
	* Generates and exports and invoice using the outstandingFee amount
	*/
	public void finalizeProject() {
		if (isFinalized) {
			System.out.println("Error: Already finalized!\n");
			return;
		}
		isFinalized = true;
		this.completionDate = java.time.LocalDate.now().format(DateTimeFormatter.ISO_DATE); 
		// Calculates the outstanding amount
		double outstandingFee = totalFee - totalPaid;
		System.out.print("Account Status: ");
		if(outstandingFee > 0) {
			String stripDate = completionDate.replace("-", "");
			String invoiceNumber = projectNumber + "_" + stripDate;
			System.out.println("Pending Payment (Invoice number is " + invoiceNumber + ")");
			System.out.println("*** Outstanding fee is " + outstandingFee + " ***");
			generateInvoice();
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
	 		if(!projectList.get(i).isFinalized) {
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
			if(!projectList.get(i).isFinalized && (LocalDate.parse(
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
				+ "isFinalized: " + isFinalized + "\n"
				+ "completionDate: " + completionDate + "\n"
						);
	}

}


