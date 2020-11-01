package fr.portefeuille.web.application.config.spring;

import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.APPLICATION_THEME;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.MAINTENANCE_URL;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE_DESCRIPTION;

import fr.portefeuille.web.application.common.template.theme.PortefeuilleApplicationTheme;
import org.iglooproject.spring.config.spring.AbstractApplicationPropertyRegistryConfig;
import org.iglooproject.spring.property.service.IPropertyRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortefeuilleWebappApplicationPropertyRegistryConfig extends AbstractApplicationPropertyRegistryConfig {

	@Override
	public void register(IPropertyRegistry registry) {
		registry.registerEnum(APPLICATION_THEME, PortefeuilleApplicationTheme.class, PortefeuilleApplicationTheme.ADVANCED);
		
		registry.registerInteger(PORTFOLIO_ITEMS_PER_PAGE, 20);
		registry.registerInteger(PORTFOLIO_ITEMS_PER_PAGE_DESCRIPTION, 20);
		
		registry.registerString(MAINTENANCE_URL);
	}

}
