package hu.uni.miskolc.iit.java.technologies.maintenance.api.model;

public class ProdMachine {

	public static enum Manufacturer {
		BOSCH, SATISLOH, SCHNEIDER, SCL, LEYBOLD, BUHLER, AR, MEI, MONK, NIDEK 
	}

	private String serialNo;
	private Manufacturer manufacturer;
	private String machineType;
	private int generation;
	private int numberOfProductsPerHour;

	public ProdMachine() {
		super();
	}

	public ProdMachine(String serialNo, Manufacturer manufacturer, String machineType, int generation, int numberOfProductsPerHour) {
		super();
		this.serialNo = serialNo;
		this.manufacturer = manufacturer;
		this.machineType = machineType;
		this.generation = generation;
		this.numberOfProductsPerHour = numberOfProductsPerHour;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setachineType(String machineType) {
		this.machineType = machineType;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public int getNumberOfProductsPerHour() {
		return numberOfProductsPerHour;
	}

	public void setNumberOfProductsPerHour(int numberOfProductsPerHour) {
		this.numberOfProductsPerHour = numberOfProductsPerHour;
	}

}
