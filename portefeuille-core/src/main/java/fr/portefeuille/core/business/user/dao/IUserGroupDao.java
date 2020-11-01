package fr.portefeuille.core.business.user.dao;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.business.person.dao.IGenericUserGroupDao;

public interface IUserGroupDao extends IGenericUserGroupDao<UserGroup, User> {

}
