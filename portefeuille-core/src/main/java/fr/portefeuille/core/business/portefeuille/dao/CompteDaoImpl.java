package fr.portefeuille.core.business.portefeuille.dao;

import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import fr.portefeuille.core.business.portefeuille.model.Compte;

@Repository
public class CompteDaoImpl extends GenericEntityDaoImpl<Long, Compte> implements ICompteDao {

}
