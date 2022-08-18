package capstone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;  
// import java.io.File;
// import java.io.FileWriter;
// import java.io.BufferedWriter;
import java.util.ArrayList;
// import java.io.IOException;

public class Project {
	// Date Format = "yyyy-mm-dd". (java.time.LocalDate)
	// sets attributes for Project class
	private String projectNumber; 
	private String projectName; 
	private String buildingType;
	private String adress;
	private String erf;
	private double totalFee;
	private double totalPaid;
	private String projectDeadline;
	private Person architect;
	private Person contractor;
	private Person customer;
	private boolean isFinalized;
	private String completionDate;
	
	// Constructor takes input as laid out below and 
	// sets isFinalized to false and completionDate to "incomplete";
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
			Person customer
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
		this.isFinalized = false;
		this.completionDate = "Incomplete";
	}
	
	// adds to totalPaid with argument given
	public void amendPaidAmount(double payment) {
		totalPaid += payment;
		System.out.println("Amount amended successfully!");
	}
	
	// replaces totalPaid figure with argument
	public void replacePaidAmount(double payment) {
		totalPaid = payment;
		System.out.println("Amount updated successfully!");
	}
	
	// returnw q true/false value for whether the project is finalized
	public boolean getFinalized() {
		return isFinalized;
	}
	
	// Replaces the total fee with the given argument
	public void setTotalFee(double newTotalFee) {
		totalFee = newTotalFee;
		System.out.println("Amount updated successfully!");
	}
	
	// replaces Deadline with given argument
	public void setDeadline(String newDeadline) {
		projectDeadline = newDeadline;
		System.out.println("Deadline updated successfully!");
	}
	
	// returns the contractor Person objects
	public Person getContractor() {
		return contractor;
	}
	
	// returns the architect Person objects
	public Person getArchitect() {
		return architect;
	}
	
	// returns the customer Person objects
	public Person getCustomer() {
		return customer;
	}
	
	// changes the isFinalized bool value to true and puts todays date as completion date
	public void finalizeProject() {
		if (isFinalized == true) {
			System.out.println("Error: Already finalized!");
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
		}
		else {
			System.out.println("Settled!");
		}
		System.out.println("\nProject Finalized!\n");
	}
	
	// returns a string with project name and number for the objects not marked as complete
	public static String getIncomplete(ArrayList<Project> projectList) {
	 	String unfinishedProjects = "-- Incomplete Project Name and Project Number --\n";
	 	for(int i = 0; i < projectList.size(); i++) {
	 		if(projectList.get(i).isFinalized == false) {
	 			unfinishedProjects += (projectList.get(i).projectName + ", " + projectList.get(i).projectNumber + "\n");
	 		}
	 	}
	 	return unfinishedProjects;
	}
	
	// Returns a list of names and project numbers for objects whose Deadline are passed the
	// current date given by LocalDate
	public static String getOverdue(ArrayList<Project> projectList) {
		String overdueProjects = "-- Overdue Project Name and Project Number --\n";
		for(int i = 0; i < projectList.size(); i++) {
			if((LocalDate.parse(projectList.get(i).projectDeadline)).compareTo(java.time.LocalDate.now()) < 0) {
				overdueProjects += (projectList.get(i).projectName + ": " + projectList.get(i).projectNumber + "\n");
			}
		}
		return overdueProjects;
	}
	
	// Converts our standard toString() method into clear data
	public String toString() {
		String details = (
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
		return details;
		
	}
}


