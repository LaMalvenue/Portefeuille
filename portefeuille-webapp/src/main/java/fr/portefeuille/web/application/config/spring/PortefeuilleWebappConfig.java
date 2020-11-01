package fr.portefeuille.web.application.config.spring;

import java.util.Date;

import fr.portefeuille.core.business.user.model.BasicUser;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.config.spring.PortefeuilleCoreCommonConfig;
import fr.portefeuille.web.application.PortefeuilleApplication;
import fr.portefeuille.web.application.common.renderer.UserRenderer;
import fr.portefeuille.web.application.common.template.resources.styles.notification.NotificationScssResourceReference;
import org.iglooproject.jpa.exception.ServiceException;
import org.iglooproject.jpa.more.rendering.service.IRendererService;
import org.iglooproject.wicket.bootstrap4.config.spring.AbstractBootstrapWebappConfig;
import org.iglooproject.wicket.more.notification.service.IHtmlNotificationCssService;
import org.iglooproject.wicket.more.notification.service.IWicketContextProvider;
import org.iglooproject.wicket.more.rendering.BooleanRenderer;
import org.iglooproject.wicket.more.rendering.Renderer;
import org.iglooproject.wicket.more.rendering.service.RendererServiceImpl;
import org.iglooproject.wicket.more.util.DatePattern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PortefeuilleCoreCommonConfig.class,
	PortefeuilleWebappSecurityConfig.class,
	PortefeuilleWebappCacheConfig.class,
	PortefeuilleWebappApplicationPropertyRegistryConfig.class
})
@ComponentScan(
	basePackageClasses = {
		PortefeuilleApplication.class
	},
	excludeFilters = @Filter(Configuration.class)
)
public class PortefeuilleWebappConfig extends AbstractBootstrapWebappConfig {

	@Override
	@Bean(name = { "PortefeuilleApplication", "application" })
	public PortefeuilleApplication application() {
		return new PortefeuilleApplication();
	}

	@Override
	public IRendererService rendererService(IWicketContextProvider wicketContextProvider) {
		RendererServiceImpl rendererService = new RendererServiceImpl(wicketContextProvider);
		
		rendererService.registerRenderer(Boolean.class, BooleanRenderer.get());
		rendererService.registerRenderer(boolean.class, BooleanRenderer.get());
		
		Renderer<Date> shortDateRenderer = Renderer.fromDatePattern(DatePattern.SHORT_DATE);
		rendererService.registerRenderer(Date.class, shortDateRenderer);
		rendererService.registerRenderer(java.sql.Date.class, shortDateRenderer);
		
		rendererService.registerRenderer(User.class, UserRenderer.get());
		rendererService.registerRenderer(TechnicalUser.class, UserRenderer.get());
		rendererService.registerRenderer(BasicUser.class, UserRenderer.get());
		
		return rendererService;
	}

	/**
	 * Override parent bean declaration so that we add our custom styles.
	 */
	@Override
	@Bean
	public IHtmlNotificationCssService htmlNotificationCssService() throws ServiceException {
		IHtmlNotificationCssService service = super.htmlNotificationCssService();
		service.registerDefaultStyles(NotificationScssResourceReference.get());
		return service;
	}

}
