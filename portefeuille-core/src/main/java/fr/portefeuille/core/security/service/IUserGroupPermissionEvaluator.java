package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.service.IGenericPermissionEvaluator;

public interface IUserGroupPermissionEvaluator extends IGenericPermissionEvaluator<User, UserGroup> {

}
