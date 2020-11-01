package fr.portefeuille.web.application.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import org.iglooproject.web.security.config.spring.AbstractWebappSecurityConfig;

@Configuration
@ImportResource({ "classpath:spring/security-web-context.xml" })
public class PortefeuilleWebappSecurityConfig extends AbstractWebappSecurityConfig {

}
