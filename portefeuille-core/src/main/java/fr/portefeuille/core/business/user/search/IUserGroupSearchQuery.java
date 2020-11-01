package fr.portefeuille.core.business.user.search;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;

public interface IUserGroupSearchQuery extends ISearchQuery<UserGroup, UserGroupSort> {

	IUserGroupSearchQuery user(User user);

	IUserGroupSearchQuery name(String name);

}
