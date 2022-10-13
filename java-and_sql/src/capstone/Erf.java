package capstone;

/**
* Contains three non-static variables and represent the site info
* assigned to Project objects
*/
public class Erf {

	/**
	* Sets non-static attribute name for the Person class
	*/
	private String erf;
	/**
	* Sets non-static attribute name for the Person class
	*/
	private String adress;
	/**
	* Sets non-static attribute name for the Person class
	*/
	private String buildingType;
	
	/**
	* Constructor for Erf class
	* 
	* @param erf reference name for our object
	* @param adress string containing the adress the object takes
	* @param buildingType string containing type of building
	*/
	public Erf (String erf, String adress, String buildingType) {
		this.setErf(erf);
		this.setAdress(adress);
		this.setBuildingType(buildingType);
	}

	/**
	* Getter for buildingType attribute
	* 
	* @return string value of buildingType
	*/
	public String getBuildingType() {
		return buildingType;
	}

	/**
	* Setter for buildingType attribute
	* 
	* @param buildingType string containing type of building
	*/
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	/**
	* Getter for adress attribute
	* 
	* @return string value of adress
	*/
	public String getAdress() {
		return adress;
	}

	/**
	* Setter for adress attribute
	* 
	* @param adress string containing the adress the object takes
	*/
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	* Getter for erf attribute
	* 
	* @return string value of erf
	*/
	public String getErf() {
		return erf;
	}

	/**
	* Setter for erf attribute
	* 
	* @param erf reference name for our object
	*/
	public void setErf(String erf) {
		this.erf = erf;
	}
}
