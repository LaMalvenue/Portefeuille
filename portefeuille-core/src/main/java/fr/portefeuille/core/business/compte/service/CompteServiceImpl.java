package fr.portefeuille.core.business.compte.service;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;
import org.springframework.stereotype.Service;

import fr.portefeuille.core.business.compte.dao.ICompteDao;
import fr.portefeuille.core.business.compte.model.Compte;

@Service
public class CompteServiceImpl extends GenericEntityServiceImpl<Long, Compte> implements ICompteService {

	public CompteServiceImpl(ICompteDao dao) {
		super(dao);
	}

}
