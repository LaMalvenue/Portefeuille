package fr.portefeuille.core.business.portefeuille.service;

import org.iglooproject.jpa.business.generic.service.IGenericEntityService;
import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

public interface IPortefeuilleService extends IGenericEntityService<Long, Portefeuille> {

	void savePortefeuille(Portefeuille portefeuille) throws ServiceException, SecurityServiceException;

	void addCompte(Portefeuille portefeuille, Compte compte) throws ServiceException, SecurityServiceException;

}
