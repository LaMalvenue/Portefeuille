package test.web.config.spring;

import org.apache.wicket.protocol.http.WebApplication;
import fr.portefeuille.web.application.PortefeuilleApplication;
import fr.portefeuille.web.application.config.spring.PortefeuilleWebappConfig;
import org.iglooproject.wicket.more.config.spring.WicketMoreApplicationPropertyRegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	PortefeuilleWebappConfig.class,
	WicketMoreApplicationPropertyRegistryConfig.class
})
public class PortefeuilleWebappTestCommonConfig {

	@Bean
	public WebApplication application() {
		return new PortefeuilleApplication();
	}

}
