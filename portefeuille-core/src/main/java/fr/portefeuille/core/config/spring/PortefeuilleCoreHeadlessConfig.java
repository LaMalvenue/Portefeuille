package fr.portefeuille.core.config.spring;

import fr.portefeuille.core.PortefeuilleCorePackage;
import fr.portefeuille.core.business.notification.service.EmptyNotificationContentDescriptorFactoryImpl;
import fr.portefeuille.core.business.notification.service.EmptyNotificationUrlBuilderServiceImpl;
import fr.portefeuille.core.business.notification.service.IPortefeuilleNotificationContentDescriptorFactory;
import fr.portefeuille.core.business.notification.service.IPortefeuilleNotificationUrlBuilderService;
import org.iglooproject.jpa.more.rendering.service.EmptyRendererServiceImpl;
import org.iglooproject.jpa.more.rendering.service.IRendererService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PortefeuilleCoreCommonConfig.class
})
@ComponentScan(
	basePackageClasses = {
		PortefeuilleCorePackage.class
	}
)
public class PortefeuilleCoreHeadlessConfig {

	@Bean
	public IRendererService rendererService() {
		return new EmptyRendererServiceImpl();
	}

	@Bean
	public IPortefeuilleNotificationContentDescriptorFactory contentDescriptorFactory() {
		return new EmptyNotificationContentDescriptorFactoryImpl();
	}

	@Bean
	public IPortefeuilleNotificationUrlBuilderService notificationUrlBuilderService() {
		return new EmptyNotificationUrlBuilderServiceImpl();
	}

}
