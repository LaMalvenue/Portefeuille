package fr.portefeuille.core.business.portefeuille.search;

import org.iglooproject.jpa.more.business.search.query.AbstractJpaSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.model.QPortefeuille;
import fr.portefeuille.core.business.user.model.User;

@Component
@Scope("prototype")
public class PortefeuilleSearchQueryImpl extends AbstractJpaSearchQuery<Portefeuille, PortefeuilleSort> implements IPortefeuilleSearchQuery {

	private static final QPortefeuille qPortefeuille = QPortefeuille.portefeuille;

	protected PortefeuilleSearchQueryImpl() {
		super(qPortefeuille);
	}

	@Override
	public IPortefeuilleSearchQuery proprietaire(User proprietaire) {
		must(matchIfGiven(qPortefeuille.proprietaire, proprietaire));
		return this;
	}

}
