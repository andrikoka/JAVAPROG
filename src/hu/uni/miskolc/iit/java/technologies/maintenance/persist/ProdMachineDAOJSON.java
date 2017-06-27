package hu.uni.miskolc.iit.java.technologies.maintenance.persist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.ProdMachineDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.exceptions.ProdMachineNotFoundException;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;

public class ProdMachineDAOJSON implements ProdMachineDAO {

	private Logger LOGGER = LogManager.getLogger(ProdMachineDAOJSON.class);
	
	private File database;
	
	public ProdMachineDAOJSON(String databasePath) {
		this.database = new File(databasePath);
		LOGGER.debug(String.format("ProdMachine Databse : %s", database.getAbsolutePath()));
	}

	public void createProdMachine(ProdMachine prodmachine) {
		Collection<ProdMachine> allProdMachines = readProdMachines();
		allProdMachines.add(prodmachine);
		ProdMachine[] extendedDatabase = allProdMachines.toArray(new ProdMachine[allProdMachines.size()]);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(database, extendedDatabase);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.fatal(String.format("IOException occured due to %s", e.getMessage()));
		}
		LOGGER.info(String.format("ProdMachine (%s) has been added!", prodmachine.getSerialNo()));
		

	}

	public Collection<ProdMachine> readProdMachines() {
		ObjectMapper mapper = new ObjectMapper();
		ProdMachine[] prodmachines = new ProdMachine[] {};
		try {
			prodmachines = mapper.readValue(database, ProdMachine[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			LOGGER.fatal(String.format("IOException occured due to %s", e.getMessage()));
		}
		Collection<ProdMachine> result = new ArrayList<ProdMachine>(Arrays.asList(prodmachines));
		return result;
	}

	public ProdMachine readProdMachineBySerialNo(String serialNo) throws ProdMachineNotFoundException {
		for(ProdMachine prodmachine : readProdMachines()){
			if(serialNo.equals(prodmachine.getSerialNo())){
				return prodmachine;
			}
		}
		throw new ProdMachineNotFoundException(String.format("There is no prodmachine with plate number: %s", serialNo));
	}

}
