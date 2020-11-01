package fr.portefeuille.core.config.spring;

import org.igloo.spring.autoconfigure.EnableIglooAutoConfiguration;
import fr.portefeuille.core.PortefeuilleCorePackage;
import org.iglooproject.config.bootstrap.spring.annotations.ManifestPropertySource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ManifestPropertySource(prefix = "portefeuille.core")
@Import({
	PortefeuilleCoreCommonJpaConfig.class,			// configuration de la persistence
	PortefeuilleCoreSecurityConfig.class,			// configuration de la sécurité
	PortefeuilleCoreTaskManagementConfig.class,			// configuration de la gestion des tâches
	PortefeuilleCoreNotificationConfig.class,		// configuration des notifications
	PortefeuilleCoreSchedulingConfig.class,			// configuration des tâches planifiées
	PortefeuilleCoreApplicationPropertyConfig.class	// configuration des propriétés de l'application
})
@ComponentScan(
	basePackageClasses = {
		PortefeuilleCorePackage.class
	},
	// https://jira.springsource.org/browse/SPR-8808
	// on veut charger de manière explicite le contexte ; de ce fait,
	// on ignore l'annotation @Configuration sur le scan de package.
	excludeFilters = @Filter(Configuration.class)
)
// fonctionnement de l'annotation @Transactional
@EnableTransactionManagement(order = PortefeuilleAdviceOrder.TRANSACTION)
@EnableIglooAutoConfiguration
public class PortefeuilleCoreCommonConfig {

	public static final String APPLICATION_NAME = "portefeuille";

	public static final String PROFILE_TEST = "test";

}