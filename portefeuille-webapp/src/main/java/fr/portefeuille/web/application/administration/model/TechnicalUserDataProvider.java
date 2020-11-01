package fr.portefeuille.web.application.administration.model;

import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.search.ITechnicalUserSearchQuery;

public class TechnicalUserDataProvider extends AbstractUserDataProvider<TechnicalUser> {

	private static final long serialVersionUID = -8540890431031886412L;

	public TechnicalUserDataProvider() {
		super(TechnicalUser.class);
	}

	@Override
	protected ITechnicalUserSearchQuery createSearchQuery() {
		return createSearchQuery(ITechnicalUserSearchQuery.class);
	}

}
