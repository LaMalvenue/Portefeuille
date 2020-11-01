package fr.portefeuille.core.business.portefeuille.dao;

import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

@Repository
public class PortefeuilleDaoImpl extends GenericEntityDaoImpl<Long, Portefeuille> implements IPortefeuilleDao {

}
