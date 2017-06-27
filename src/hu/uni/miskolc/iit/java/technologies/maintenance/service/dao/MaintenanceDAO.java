package hu.uni.miskolc.iit.java.technologies.maintenance.service.dao;

import java.util.Collection;

import hu.uni.miskolc.iit.java.technologies.maintenance.api.model.Maintenance;

public interface MaintenanceDAO {

	void createMaintenance(Maintenance maintenance);

	Collection<Maintenance> readMaintenances();

}
