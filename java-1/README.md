## This README belongs to **"java-1"**

<sub>
Three folders exist within this repo
* SRC - Contains our Resources folder where data is stored as .txt files, and our .java files
* DOC - Contains the documentation for this repository
* BIN - Contains our class files and our resources
</sub>

<sub>
Included within the **Capstone** package is:
* Action.java - Contains our main method and executes the body of our program
* Person.java - Our Class files for the Person Objects
* Project.java - Our Class files for the Project Objects
</sub>

# This is part of a series of java programs written as excercises.

Person.java and Project.java are two Classes for creating objects.
  
* **Person** stores:
  * name
  * role
  * details

This person object is used to populate certain attributes of Project.

* **Project** stores
  *  projectNumber
  *  projectName
  *  buildingType
  *  adress
  *  erf
  *  totalFee
  *  totalPaid
  *  projectDeadline - *(java LocalDate object)* as a string
  *  architect - *(Person Object)*
  *  contractor - *(Person Object)*
  *  customer - *(Person Object)*
  *  isFinalized - *(Boolean True/False)*
  *  completionDate - *(java LocalDate object)* as a string


**Class Attributes** for both Object Classes are **private** and attributes are *called via methods* from within each class as is documentation.

