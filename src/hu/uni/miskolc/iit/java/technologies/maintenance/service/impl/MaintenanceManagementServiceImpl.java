package hu.uni.miskolc.iit.java.technologies.maintenance.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Maintenance;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Price;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.service.MaintenanceManagementService;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.MaintenanceDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.ProdMachineDAO;
import hu.uni.miskolc.iit.java.technologies.maintenance.service.dao.exceptions.ProdMachineNotFoundException;

public class MaintenanceManagementServiceImpl implements MaintenanceManagementService {
	
	private static Logger LOG = LogManager.getLogger(MaintenanceManagementServiceImpl.class);
	
	
	private MaintenanceDAO announcementDAO;
	private ProdMachineDAO prodmachineDAO;
	
	

	public MaintenanceManagementServiceImpl(MaintenanceDAO announcementDAO, ProdMachineDAO prodmachineDAO) {
		super();
		this.announcementDAO = announcementDAO;
		this.prodmachineDAO = prodmachineDAO;
	}

	public Collection<Maintenance> listMaintenances() {
		return announcementDAO.readMaintenances();
	}

	public Collection<Maintenance> listOpenMaintenances() {
		Collection<Maintenance> result = new ArrayList<Maintenance>();
		for(Maintenance announcement : announcementDAO.readMaintenances()){
			if(announcement.openMaintenance()){
				result.add(announcement);
			}
		}
		return result;
	}

	public void announce(String prodmachineSerialNo, Date expire, Price price) {
		try{
		ProdMachine prodmachine = prodmachineDAO.readProdMachineBySerialNo(prodmachineSerialNo);
		Maintenance announcement = new Maintenance(prodmachine, price, new Date(), expire, false);
		
		
		announcementDAO.createMaintenance(announcement);
		}
		catch(ProdMachineNotFoundException ex){
			LOG.warn(ex.getMessage());
		}

	}

	@Override
	public void maintain(String serialNo, Date expire, Price price) {
		// TODO Auto-generated method stub
		
	}

}
