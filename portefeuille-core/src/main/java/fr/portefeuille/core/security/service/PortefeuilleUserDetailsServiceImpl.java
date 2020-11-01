package fr.portefeuille.core.security.service;

import java.util.Collection;
import java.util.Set;

import org.iglooproject.jpa.security.service.CoreJpaUserDetailsServiceImpl;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class PortefeuilleUserDetailsServiceImpl extends CoreJpaUserDetailsServiceImpl implements IPortefeuilleUserDetailsService {

	private static final Multimap<String, String> PERMISSIONS_BY_ROLE = ImmutableMultimap.<String, String>builder()
		.build();

	@Override
	protected void addPermissionsFromAuthorities(Collection<? extends GrantedAuthority> expandedGrantedAuthorities, Set<Permission> permissions) {
		for (GrantedAuthority authority : expandedGrantedAuthorities) {
			addPermissions(permissions, PERMISSIONS_BY_ROLE.get(authority.getAuthority()));
		}
	}

}
