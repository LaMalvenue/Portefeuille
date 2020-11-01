package fr.portefeuille.core.business.user.service;

import fr.portefeuille.core.business.user.dao.IUserGroupDao;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.business.person.service.GenericUserGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personGroupService")
public class UserGroupServiceImpl extends GenericUserGroupServiceImpl<UserGroup, User>
		implements IUserGroupService {

	@Autowired
	public UserGroupServiceImpl(IUserGroupDao userGroupDao) {
		super(userGroupDao);
	}

}
