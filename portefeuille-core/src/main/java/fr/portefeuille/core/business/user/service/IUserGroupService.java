package fr.portefeuille.core.business.user.service;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.business.person.service.IGenericUserGroupService;

public interface IUserGroupService extends IGenericUserGroupService<UserGroup, User> {

}
