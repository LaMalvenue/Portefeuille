package fr.portefeuille.core.business.portefeuille.service;

import java.util.Objects;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;
import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.atomic.TypeCompte;
import fr.portefeuille.core.business.compte.service.ICompteService;
import fr.portefeuille.core.business.portefeuille.dao.IPortefeuilleDao;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

@Service
public class PortefeuilleServiceImpl extends GenericEntityServiceImpl<Long, Portefeuille> implements IPortefeuilleService {

	@Autowired
	private ICompteService compteService;

	public PortefeuilleServiceImpl(IPortefeuilleDao dao) {
		super(dao);
	}

	@Override
	public void addCompte(Compte compte) throws ServiceException, SecurityServiceException {
		Objects.requireNonNull(compte);
		
		if (compte.getType().equals(TypeCompte.LIQUIDE)) {
			compte.setLabel("Liquide");
		}
		if (compte.getType().equals(TypeCompte.COMPTE_COURANT)) {
			compte.setLabel("Compte courant");
		}
		if (compte.getType().equals(TypeCompte.EPARGNE)) {
			compte.setLabel("Compte épargne");
		}
		if (compte.getType().equals(TypeCompte.CARTE_RESTAURANT)) {
			compte.setLabel("Carte restaurant");
		}
		
		compteService.create(compte);
		
		Portefeuille.get().addCompte(compte);
	}

}
