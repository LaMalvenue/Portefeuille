package fr.portefeuille.core.business.compte.search;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.atomic.TypeCompte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

@Component
@Scope("prototype")
public class CompteSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Compte, CompteSort> implements ICompteSearchQuery {

	protected CompteSearchQueryImpl() {
		super(Compte.class);
	}

	@Override
	public ICompteSearchQuery text(String text) {
		BooleanJunction<?> junction = getDefaultQueryBuilder().bool();
		shouldIfNotNull(
			junction,
			matchAutocompleteIfGiven(text, Compte.LABEL)
		);
		if (!junction.isEmpty()) {
			must(junction.createQuery());
		}
		return this;
	}

	@Override
	public ICompteSearchQuery typeCompte(TypeCompte typeCompte) {
		must(matchIfGiven(Compte.TYPE, typeCompte));
		return this;
	}

	@Override
	public ICompteSearchQuery portefeuille(Portefeuille portefeuille) {
		must(matchIfGiven(Compte.PORTEFEUILLE, portefeuille));
		return this;
	}

}
