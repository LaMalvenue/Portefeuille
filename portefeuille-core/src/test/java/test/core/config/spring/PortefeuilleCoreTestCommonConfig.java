package test.core.config.spring;

import fr.portefeuille.core.config.spring.PortefeuilleCoreCommonConfig;
import org.iglooproject.jpa.more.rendering.service.EmptyRendererServiceImpl;
import org.iglooproject.jpa.more.rendering.service.IRendererService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import test.core.TestCorePackage;

@Configuration
@Import({
	PortefeuilleCoreCommonConfig.class,
})
@ComponentScan(basePackageClasses = TestCorePackage.class)
public class PortefeuilleCoreTestCommonConfig {
	
	@Bean
	public IRendererService rendererService() {
		return new EmptyRendererServiceImpl();
	}

}
