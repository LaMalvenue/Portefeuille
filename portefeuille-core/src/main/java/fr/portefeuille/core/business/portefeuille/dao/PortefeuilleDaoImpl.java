package fr.portefeuille.core.business.portefeuille.dao;

import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.model.QPortefeuille;
import fr.portefeuille.core.business.user.model.User;

@Repository
public class PortefeuilleDaoImpl extends GenericEntityDaoImpl<Long, Portefeuille> implements IPortefeuilleDao {

	private static final QPortefeuille qPortefeuille = QPortefeuille.portefeuille;

	@Override
	public Portefeuille getByProprietaire(User utilisateur) {
		return new JPAQuery<Portefeuille>(getEntityManager())
			.select(qPortefeuille)
			.from(qPortefeuille)
			.where(qPortefeuille.proprietaire.eq(utilisateur))
			.fetchFirst();
	}
}
