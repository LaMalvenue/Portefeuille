package fr.portefeuille.core.business.compte.search;

import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.compte.model.Compte;

@Component
@Scope("prototype")
public class CompteSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Compte, CompteSort> implements ICompteSearchQuery {

	protected CompteSearchQueryImpl() {
		super(Compte.class);
	}

}
