package fr.portefeuille.web.application.property;

import fr.portefeuille.web.application.common.template.theme.PortefeuilleApplicationTheme;
import org.iglooproject.spring.property.model.AbstractPropertyIds;
import org.iglooproject.spring.property.model.ImmutablePropertyId;
import org.iglooproject.spring.property.model.MutablePropertyId;

public final class PortefeuilleWebappPropertyIds extends AbstractPropertyIds {

	/*
	 * Mutable Properties
	 */

	public static final MutablePropertyId<PortefeuilleApplicationTheme> APPLICATION_THEME = mutable("application.theme");
	
	/*
	 * Immutable Properties
	 */

	public static final ImmutablePropertyId<Integer> PORTFOLIO_ITEMS_PER_PAGE = immutable("portfolio.itemsPerPage");
	public static final ImmutablePropertyId<Integer> PORTFOLIO_ITEMS_PER_PAGE_DESCRIPTION = immutable("portfolio.itemsPerPage.description");

	public static final ImmutablePropertyId<String> MAINTENANCE_URL = immutable("maintenance.url");

}
