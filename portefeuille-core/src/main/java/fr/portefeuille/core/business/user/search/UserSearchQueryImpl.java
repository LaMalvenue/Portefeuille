package fr.portefeuille.core.business.user.search;

import fr.portefeuille.core.business.user.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserSearchQueryImpl<U extends User> extends AbstractUserSearchQueryImpl<U> implements IUserSearchQuery<U> {

	protected UserSearchQueryImpl(Class<U> clazz) {
		super(clazz);
	}

}