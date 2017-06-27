package hu.uni.miskolc.iit.java.technologies.maintenance.service.dao;

import java.util.Collection;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.exceptions.ProdMachineNotFoundException;

public interface ProdMachineDAO {

	void createProdMachine(ProdMachine prodmachine);
	
	Collection<ProdMachine> readProdMachines();
	ProdMachine readProdMachineBySerialNo(String serialNo) throws ProdMachineNotFoundException;
	
}
