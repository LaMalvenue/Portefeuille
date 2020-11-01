package fr.portefeuille.core.business.user.dao;

import java.util.List;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.jpa.security.business.person.dao.IGenericUserDao;

public interface IUserDao extends IGenericUserDao<User> {

	User getByEmailCaseInsensitive(String email);

	List<User> listByUsername(String username);

}
