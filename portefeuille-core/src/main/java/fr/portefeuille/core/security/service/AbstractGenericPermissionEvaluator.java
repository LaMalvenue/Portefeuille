package fr.portefeuille.core.security.service;

import java.util.Arrays;
import java.util.Collection;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.security.business.person.model.IUser;
import org.iglooproject.jpa.security.service.IGenericPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;

public abstract class AbstractGenericPermissionEvaluator<E extends GenericEntity<Long, ?>> implements IGenericPermissionEvaluator<User, E> {

	@Autowired
	protected IPortefeuilleSecurityService securityService;

	@Autowired
	protected PermissionFactory permissionFactory;

	protected final boolean is(Permission permission, String ... permissionNames) {
		return is(permission, Arrays.asList(permissionNames));
	}

	protected final boolean is(Permission permission, Collection<String> permissionNames) {
		for (String permissionName : permissionNames) {
			Permission permissionFromName = permissionFactory.buildFromName(permissionName);
			if (permissionFromName.equals(permission)) {
				return true;
			}
		}
		return false;
	}

	protected final boolean hasPermission(IUser user, String permissionName) {
		return securityService.hasPermission(user, permissionFactory.buildFromName(permissionName));
	}

	protected final boolean hasPermission(IUser user, Object securedObject, String permissionName) {
		return securityService.hasPermission(user, securedObject, permissionFactory.buildFromName(permissionName));
	}

	protected final boolean hasRole(IUser user, String role) {
		return securityService.hasRole(user, role);
	}

}
