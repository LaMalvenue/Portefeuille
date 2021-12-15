package fr.portefeuille.core.business.portefeuille.search;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.referencedata.model.CompteType;

@Component
@Scope("prototype")
public class CompteSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Compte, CompteSort> implements ICompteSearchQuery {

	protected CompteSearchQueryImpl() {
		super(Compte.class);
	}

	@Override
	public ICompteSearchQuery text(String text) {
		BooleanJunction<?> junction = getDefaultQueryBuilder().bool();
		if (!junction.isEmpty()) {
			must(junction.createQuery());
		}
		return this;
	}

	@Override
	public ICompteSearchQuery typeCompte(CompteType typeCompte) {
		must(matchIfGiven(Compte.TYPE, typeCompte));
		return this;
	}

}
