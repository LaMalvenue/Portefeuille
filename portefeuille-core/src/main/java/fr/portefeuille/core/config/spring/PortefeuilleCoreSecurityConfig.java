package fr.portefeuille.core.config.spring;

import static fr.portefeuille.core.property.PortefeuilleCorePropertyIds.SECURITY_PASSWORD_USER_FORBIDDEN_PASSWORDS;

import fr.portefeuille.core.business.user.model.BasicUser;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.security.model.PortefeuillePermission;
import fr.portefeuille.core.security.model.SecurityOptions;
import fr.portefeuille.core.security.service.PortefeuilleAuthenticationServiceImpl;
import fr.portefeuille.core.security.service.PortefeuillePermissionEvaluator;
import fr.portefeuille.core.security.service.PortefeuilleSecurityServiceImpl;
import fr.portefeuille.core.security.service.PortefeuilleUserDetailsServiceImpl;
import fr.portefeuille.core.security.service.IPortefeuilleAuthenticationService;
import fr.portefeuille.core.security.service.IPortefeuilleSecurityService;
import fr.portefeuille.core.security.service.IPortefeuilleUserDetailsService;
import fr.portefeuille.core.security.service.ISecurityManagementService;
import fr.portefeuille.core.security.service.SecurityManagementServiceImpl;
import org.iglooproject.jpa.security.config.spring.DefaultJpaSecurityConfig;
import org.iglooproject.jpa.security.password.rule.SecurityPasswordRulesBuilder;
import org.iglooproject.jpa.security.runas.CoreRunAsManagerImpl;
import org.iglooproject.jpa.security.service.AuthenticationUsernameComparison;
import org.iglooproject.jpa.security.service.ICorePermissionEvaluator;
import org.iglooproject.jpa.security.service.NamedPermissionFactory;
import org.iglooproject.spring.property.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.acls.domain.PermissionFactory;

@Configuration
@Import(DefaultJpaSecurityConfig.class)
public class PortefeuilleCoreSecurityConfig {

	@Autowired
	protected DefaultJpaSecurityConfig defaultJpaSecurityConfig;

	@Autowired
	protected IPropertyService propertyService;

	@Bean
	@Scope(proxyMode = ScopedProxyMode.INTERFACES)
	public ICorePermissionEvaluator permissionEvaluator() {
		return new PortefeuillePermissionEvaluator();
	}

	@Bean
	public IPortefeuilleAuthenticationService authenticationService() {
		return new PortefeuilleAuthenticationServiceImpl();
	}

	@Bean
	public IPortefeuilleSecurityService securityService() {
		return new PortefeuilleSecurityServiceImpl();
	}

	@Bean
	public AuthenticationUsernameComparison authenticationUsernameComparison() {
		return AuthenticationUsernameComparison.CASE_SENSITIVE;
	}

	@Bean
	public IPortefeuilleUserDetailsService userDetailsService() {
		PortefeuilleUserDetailsServiceImpl userDetailsService = new PortefeuilleUserDetailsServiceImpl();
		userDetailsService.setAuthenticationUsernameComparison(authenticationUsernameComparison());
		return userDetailsService;
	}

	@Bean
	public PermissionFactory permissionFactory() {
		return new NamedPermissionFactory(PortefeuillePermission.ALL);
	}

	@Bean
	public RunAsManager runAsManager() {
		CoreRunAsManagerImpl runAsManager = new CoreRunAsManagerImpl();
		runAsManager.setKey(defaultJpaSecurityConfig.getRunAsKey());
		return runAsManager;
	}

	@Bean
	public RunAsImplAuthenticationProvider runAsAuthenticationProvider() {
		RunAsImplAuthenticationProvider runAsAuthenticationProvider = new RunAsImplAuthenticationProvider();
		runAsAuthenticationProvider.setKey(defaultJpaSecurityConfig.getRunAsKey());
		return runAsAuthenticationProvider;
	}

	@Bean
	public ISecurityManagementService securityManagementService() {
		SecurityManagementServiceImpl securityManagementService = new SecurityManagementServiceImpl();
		securityManagementService
			.setOptions(
				TechnicalUser.class,
				new SecurityOptions()
					.passwordAdminRecovery()
					.passwordAdminUpdate()
					.passwordUserRecovery()
					.passwordUserUpdate()
					.passwordRules(
						SecurityPasswordRulesBuilder.start()
							.minMaxLength(User.MIN_PASSWORD_LENGTH, User.MAX_PASSWORD_LENGTH)
							.forbiddenUsername()
							.forbiddenPasswords(propertyService.get(SECURITY_PASSWORD_USER_FORBIDDEN_PASSWORDS))
							.build()
					)
			)
			.setOptions(
				BasicUser.class,
				new SecurityOptions()
					.passwordExpiration()
					.passwordHistory()
					.passwordUserRecovery()
					.passwordUserUpdate()
					.passwordRules(
						SecurityPasswordRulesBuilder.start()
							.minMaxLength(User.MIN_PASSWORD_LENGTH, User.MAX_PASSWORD_LENGTH)
							.forbiddenUsername()
							.forbiddenPasswords(propertyService.get(SECURITY_PASSWORD_USER_FORBIDDEN_PASSWORDS))
							.build()
					)
			)
			.setDefaultOptions(
				SecurityOptions.DEFAULT
			);
		return securityManagementService;
	}

}
