package hu.uni.miskolc.iit.java.technologies.maintenance.service.impl;

import java.util.Collection;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.ProdMachineManagementService;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.exceptions.UnknownProdMachineException;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.ProdMachineDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.exceptions.ProdMachineNotFoundException;

public class ProdMachineManagementServiceImpl implements ProdMachineManagementService {

	private ProdMachineDAO prodmachineDAO;

	public ProdMachineManagementServiceImpl(ProdMachineDAO prodmachineDAO) {
		super();
		this.prodmachineDAO = prodmachineDAO;
	}

	public Collection<ProdMachine> listProdMachines() {
		return prodmachineDAO.readProdMachines();
	}

	public void acquireProdMachine(ProdMachine prodmachine) {
		prodmachineDAO.createProdMachine(prodmachine);
	}
	
	public ProdMachine getProdMachineByPlateNo(String serialNo) throws UnknownProdMachineException {
		try {
			ProdMachine result = prodmachineDAO.readProdMachineBySerialNo(serialNo);
			return result;
		} catch (ProdMachineNotFoundException e) {
			throw new UnknownProdMachineException(serialNo);
		}
	}

	@Override
	public ProdMachine getProdMachineBySerialNo(String serialNo) throws UnknownProdMachineException {
		// TODO Auto-generated method stub
		return null;
	}

}
