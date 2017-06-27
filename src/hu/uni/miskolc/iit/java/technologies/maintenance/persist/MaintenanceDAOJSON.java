package hu.uni.miskolc.iit.java.technologies.maintenance.persist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.MaintenanceDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.ProdMachineDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Maintenance;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;

public class MaintenanceDAOJSON implements MaintenanceDAO {

	private static Logger LOG = LogManager.getLogger(MaintenanceDAOJSON.class);

	private final File database;

	public MaintenanceDAOJSON(String database) {
		super();
		this.database = new File(database);
	}

	public void createMaintenance(Maintenance maintenance) {
		Collection<Maintenance> maintenances = new ArrayList<Maintenance>(readMaintenances());
		maintenances.add(maintenance);

		System.out.println(maintenances);
		System.out.println("maintenances beszurasa sikeres.");
		Maintenance[] maintenancesArray = maintenances.toArray(new Maintenance[maintenances.size()]);
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(Maintenance.class, MaintenanceMixIn.class);
		try {
			mapper.writeValue(database, maintenancesArray);
			LOG.info(String.format("Maintenances (%s) has been added!", maintenance.getProdMachine()));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Collection<Maintenance> readMaintenances() {
		Collection<Maintenance> result;
		Maintenance[] maintenances = new Maintenance[] {};
		try {
			ObjectMapper mapper = new ObjectMapper();
			maintenances = mapper.readValue(database, Maintenance[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			LOG.fatal(String.format("IOException occured due to %s", e.getMessage()));
		}
		result = new ArrayList<Maintenance>(Arrays.asList(maintenances));
		return result;
	}

	private abstract class MaintenanceMixIn{
		@JsonIgnore abstract boolean openMaintenance(); 
	}
}
