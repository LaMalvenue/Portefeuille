package fr.portefeuille.core.business.portefeuille.dao;

import org.iglooproject.jpa.business.generic.dao.IGenericEntityDao;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.user.model.User;

public interface IPortefeuilleDao extends IGenericEntityDao<Long, Portefeuille> {

	Portefeuille getByProprietaire(User utilisateur);

}
