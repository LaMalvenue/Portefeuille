package fr.portefeuille.core.business.user.dao;

import org.springframework.stereotype.Repository;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.business.person.dao.GenericUserGroupDaoImpl;

@Repository("personGroupDao")
public class UserGroupDaoImpl extends GenericUserGroupDaoImpl<UserGroup, User> implements IUserGroupDao {

	public UserGroupDaoImpl() {
		super();
	}

}
