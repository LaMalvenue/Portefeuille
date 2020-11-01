package fr.portefeuille.core.security.model;

import java.lang.reflect.Field;
import java.util.Collection;

import org.iglooproject.jpa.security.model.NamedPermission;

import com.google.common.collect.ImmutableSet;

public final class PortefeuillePermission extends NamedPermission {

	private static final long serialVersionUID = 8541973919257428300L;

	public static final Collection<PortefeuillePermission> ALL;
	static {
		ImmutableSet.Builder<PortefeuillePermission> builder = ImmutableSet.builder();
		Field[] fields = PortefeuillePermissionConstants.class.getFields();
		for (Field field : fields) {
			try {
				Object fieldValue = field.get(null);
				if (fieldValue instanceof String) {
					builder.add(new PortefeuillePermission((String)fieldValue));
				}
			} catch (IllegalArgumentException|IllegalAccessException ignored) { // NOSONAR
			}
		}
		ALL = builder.build();
	}

	private PortefeuillePermission(String name) {
		super(name);
	}

}
