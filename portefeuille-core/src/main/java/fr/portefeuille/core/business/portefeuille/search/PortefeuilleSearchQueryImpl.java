package fr.portefeuille.core.business.portefeuille.search;

import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

@Component
@Scope("prototype")
public class PortefeuilleSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Portefeuille, PortefeuilleSort> implements IPortefeuilleSearchQuery {

	protected PortefeuilleSearchQueryImpl() {
		super(Portefeuille.class);
	}

}
