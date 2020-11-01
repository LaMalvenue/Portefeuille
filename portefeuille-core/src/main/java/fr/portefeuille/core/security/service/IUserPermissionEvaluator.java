package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.jpa.security.service.IGenericPermissionEvaluator;

public interface IUserPermissionEvaluator extends IGenericPermissionEvaluator<User, User> {

}
