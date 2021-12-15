package fr.portefeuille.core.business.portefeuille.service;

import java.util.Objects;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;
import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.portefeuille.core.business.portefeuille.dao.IPortefeuilleDao;
import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.service.IUserService;

@Service
public class PortefeuilleServiceImpl extends GenericEntityServiceImpl<Long, Portefeuille> implements IPortefeuilleService {

	private IPortefeuilleDao dao;

	@Autowired
	private IUserService userService;

	public PortefeuilleServiceImpl(IPortefeuilleDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public Portefeuille getByProprietaire(User utilisateur) {
		Objects.requireNonNull(utilisateur);
		return dao.getByProprietaire(utilisateur);
	}

	@Override
	public void savePortefeuille(Portefeuille portefeuille) throws ServiceException, SecurityServiceException {
		if (portefeuille.isNew()) {
			portefeuille.setProprietaire(userService.getAuthenticatedUser());
			create(portefeuille);
		} else {
			update(portefeuille);
		}
	}

	@Override
	public void addCompte(Portefeuille portefeuille, Compte compte) throws ServiceException, SecurityServiceException {
		Objects.requireNonNull(portefeuille);
		Objects.requireNonNull(compte);
		
		if (portefeuille.isNew()) {
			portefeuille.setProprietaire(userService.getAuthenticatedUser());
			create(portefeuille);
		}
		
		portefeuille.addCompte(compte);
		
		update(portefeuille);
	}

}
