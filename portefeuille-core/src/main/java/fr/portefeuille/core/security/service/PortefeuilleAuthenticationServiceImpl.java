package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.service.IUserService;
import org.iglooproject.jpa.security.service.CoreAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class PortefeuilleAuthenticationServiceImpl extends CoreAuthenticationServiceImpl implements IPortefeuilleAuthenticationService {

	@Autowired
	private IUserService userService;

	@Override
	public User getUser() {
		return userService.getAuthenticatedUser();
	}

}
