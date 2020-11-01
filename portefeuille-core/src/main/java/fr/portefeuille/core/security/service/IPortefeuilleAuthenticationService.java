package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.jpa.security.service.IAuthenticationService;

public interface IPortefeuilleAuthenticationService extends IAuthenticationService {

	User getUser();

}
