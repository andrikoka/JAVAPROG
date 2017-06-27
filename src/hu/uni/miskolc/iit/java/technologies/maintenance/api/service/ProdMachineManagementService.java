package hu.uni.miskolc.iit.java.technologies.maintenance.api.service;

import java.util.Collection;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.exceptions.UnknownProdMachineException;

public interface ProdMachineManagementService {

	Collection<ProdMachine> listProdMachines();
	ProdMachine getProdMachineBySerialNo(String serialNo) throws UnknownProdMachineException;
	void acquireProdMachine(ProdMachine prodmachine);
	
}
