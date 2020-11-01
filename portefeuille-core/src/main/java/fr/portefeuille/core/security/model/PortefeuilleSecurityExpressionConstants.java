package fr.portefeuille.core.security.model;

import static org.iglooproject.commons.util.security.PermissionObject.DEFAULT_PERMISSION_OBJECT_NAME;

@SuppressWarnings("squid:S1192") // we won't use constants for string definition
public final class PortefeuilleSecurityExpressionConstants {
	
	public static final String READ = "hasPermission(#" + DEFAULT_PERMISSION_OBJECT_NAME + ", '" + PortefeuillePermissionConstants.READ + "')";
	public static final String CREATE = "hasPermission(#" + DEFAULT_PERMISSION_OBJECT_NAME + ", '" + PortefeuillePermissionConstants.CREATE + "')";
	public static final String WRITE = "hasPermission(#" + DEFAULT_PERMISSION_OBJECT_NAME + ", '" + PortefeuillePermissionConstants.WRITE + "')";
	public static final String DELETE = "hasPermission(#" + DEFAULT_PERMISSION_OBJECT_NAME + ", '" + PortefeuillePermissionConstants.DELETE + "')";

	private PortefeuilleSecurityExpressionConstants() {
	}

}
