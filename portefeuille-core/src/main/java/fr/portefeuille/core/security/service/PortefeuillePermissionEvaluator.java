package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.referencedata.model.ReferenceData;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.security.service.AbstractCorePermissionEvaluator;
import org.iglooproject.jpa.util.HibernateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Permission;

public class PortefeuillePermissionEvaluator extends AbstractCorePermissionEvaluator<User> {

	@Autowired
	private IUserPermissionEvaluator userPermissionEvaluator;

	@Autowired
	private IUserGroupPermissionEvaluator userGroupPermissionEvaluator;

	@Autowired
	private IReferenceDataPermissionEvaluator referenceDataPermissionEvaluator;

	@Override
	protected boolean hasPermission(User user, Object targetDomainObject, Permission permission) {
		if (targetDomainObject != null) {
			targetDomainObject = HibernateUtils.unwrap(targetDomainObject); // NOSONAR
		}
		
		if (user != null) {
			user = HibernateUtils.unwrap(user); // NOSONAR
		}
		
		if (targetDomainObject instanceof User) {
			return userPermissionEvaluator.hasPermission(user, (User) targetDomainObject, permission);
		} else if (targetDomainObject instanceof UserGroup) {
			return userGroupPermissionEvaluator.hasPermission(user, (UserGroup) targetDomainObject, permission);
		} else if (targetDomainObject instanceof ReferenceData) {
			return referenceDataPermissionEvaluator.hasPermission(user, (ReferenceData<?>) targetDomainObject, permission);
		}
		
		return false;
	}

}