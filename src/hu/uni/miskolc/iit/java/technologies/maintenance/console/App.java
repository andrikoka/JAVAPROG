package hu.uni.miskolc.iit.java.technologies.maintenance.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import hu.uni.miskolc.iit.java.technologies.maintenance.persist.MaintenanceDAOJSON;
import hu.uni.miskolc.iit.java.technologies.maintenance.persist.ProdMachineDAOJSON;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.MaintenanceDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.ProdMachineDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.impl.MaintenanceManagementServiceImpl;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.impl.ProdMachineManagementServiceImpl;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Maintenance;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Price;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine.Manufacturer;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.MaintenanceManagementService;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.ProdMachineManagementService;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.exceptions.UnknownProdMachineException;

/**
 * Hello world!
 *
 */
public class App {
	private static ProdMachineManagementService prodmachineManager;
	private static MaintenanceManagementService maintenanceManager;

	static {
		ProdMachineDAO prodmachineDAO = new ProdMachineDAOJSON("resources/prodmachines.json");
		MaintenanceDAO maintenanceDAO = new MaintenanceDAOJSON("resources/maintenances.json");
		prodmachineManager = new ProdMachineManagementServiceImpl(prodmachineDAO);
		maintenanceManager = new MaintenanceManagementServiceImpl(maintenanceDAO, prodmachineDAO);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean run = true;
		while (run) {

			String line = br.readLine();
			if ("exit".equals(line)) {
				break;
			}
			if ("list prodmachines".equals(line)) {
				listProdMachines();
			}
			if ("insert prodmachine".equals(line)) {
				addProdMachine();
			}
			if ("list maintenances".equals(line)) {
				printMaintenances(maintenanceManager.listMaintenances());
			}
			if ("list open maintenances".equals(line)) {
				printMaintenances(maintenanceManager.listOpenMaintenances());
			}
			if ("add maintenance".equals(line)) {
				addMaintenance();
			}
		}

	}

	private static void listProdMachines() {
		final int tableWidth = 50;
		printHorisontalLine(tableWidth);
		System.out.println("| SerialNo | Manufacturer | machineType | # Generation | Number Of Products Per Hour |");
		printHorisontalLine(tableWidth);
		for (ProdMachine prodmachine : prodmachineManager.listProdMachines()) {
			System.out.println(String.format("| %1$7s | %2$8s | %3$5s | %4$7d | %5$11d |", prodmachine.getSerialNo(),
					prodmachine.getManufacturer(), prodmachine.getMachineType(), prodmachine.getGeneration(), prodmachine.getNumberOfProductsPerHour()));
			printHorisontalLine(tableWidth);
		}
	}

	private static void printHorisontalLine(int length) {
		for (int i = 0; i < length; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private static void addProdMachine() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Serial No.: ");
		String serialNo = br.readLine();
		System.out.println("Manufacturer: ");
		Manufacturer manufacturer = Manufacturer.valueOf(br.readLine());
		System.out.println("MachineType");
		String machineType = br.readLine();
		System.out.println("Generation: ");
		int gens = Integer.parseInt(br.readLine());
		System.out.println("Number Of Products Per Hour: ");
		int numberOfProductsPerHour = Integer.parseInt(br.readLine());
		ProdMachine prodmachine = new ProdMachine(serialNo, manufacturer, machineType, gens, numberOfProductsPerHour);
		prodmachineManager.acquireProdMachine(prodmachine);

	}

	private static void printMaintenances(Collection<Maintenance> maintenances) {
		final int tableWidth = 80;
		printHorisontalLine(tableWidth);
		System.out.println(
				"| 						ProdMachine							 | 	Price			 | Start Date | Expire Date | open  |");
		System.out.println(
				"| SerialNo | Manufacturer | MachineType | # Generation | Number Of Products Per Hour | Amount | Currency |			  |		        |       |");
		printHorisontalLine(tableWidth);
		for (Maintenance maintenance : maintenances) {
			ProdMachine prodmachine = maintenance.getProdMachine();
			Price price = maintenance.getPrice();
			System.out.println(String.format(
					"| %1$7s | %2$8s | %3$5s | %4$7d | %5$11d | %6$5.2f | %7$8s | %8$10s | %9$5s | %10$5s |",
					prodmachine.getSerialNo(), prodmachine.getManufacturer(), prodmachine.getMachineType(), prodmachine.getGeneration(), prodmachine.getNumberOfProductsPerHour(),
					price.getAmount(), price.getCurrency().toString(), maintenance.getStartDate().toString(),
					maintenance.getExpirationDate().toString(), maintenance.openMaintenance()));
		}
	}

	private static void addMaintenance() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Serial No.: ");
		String serialNo = br.readLine();
		System.out.println("Expiration Date (YYYY-MM-dd): ");
		String expireDateStr = br.readLine();
		System.out.println("Price (amount currency e.g. 100 HUF): ");
		String priceStr = br.readLine();

		try {
			ProdMachine prodmachine = prodmachineManager.getProdMachineBySerialNo(serialNo);
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			Date expireDate = df.parse(expireDateStr);
			Price price = new Price(Double.parseDouble(priceStr.split(" ")[0]),
					Currency.getInstance(priceStr.split(" ")[1]));

			maintenanceManager.maintain(serialNo, expireDate, price);
		} catch (UnknownProdMachineException e) {
			// TODO
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
