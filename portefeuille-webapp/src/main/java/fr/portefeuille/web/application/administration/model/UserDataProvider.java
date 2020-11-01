package fr.portefeuille.web.application.administration.model;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.search.IUserSearchQuery;
import org.iglooproject.wicket.more.application.CoreWicketApplication;

public class UserDataProvider extends AbstractUserDataProvider<User> {

	private static final long serialVersionUID = -8540890431031886412L;

	public UserDataProvider() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IUserSearchQuery<User> createSearchQuery() {
		return (IUserSearchQuery<User>) CoreWicketApplication.get().getApplicationContext().getBean(IUserSearchQuery.class, User.class);
	}

}
