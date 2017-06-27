package hu.uni.miskolc.iit.java.technologies.maintenance.api.service;

import java.util.Date;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Maintenance;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.ProdMachine;
import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Price;

import java.util.Collection;

public interface MaintenanceManagementService {

	Collection<Maintenance> listMaintenances();
	Collection<Maintenance> listOpenMaintenances();
	
	void maintain(String serialNo, Date expire, Price price);
}
