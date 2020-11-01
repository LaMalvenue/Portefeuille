package fr.portefeuille.core.business.portefeuille.service;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;

import fr.portefeuille.core.business.portefeuille.dao.IPortefeuilleDao;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

public class PortefeuilleServiceImpl extends GenericEntityServiceImpl<Long, Portefeuille> implements IPortefeuilleService {

	public PortefeuilleServiceImpl(IPortefeuilleDao dao) {
		super(dao);
	}

}
