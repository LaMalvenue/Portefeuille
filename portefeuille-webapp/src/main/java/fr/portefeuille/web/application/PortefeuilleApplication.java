package fr.portefeuille.web.application;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import org.iglooproject.jpa.more.business.history.model.embeddable.HistoryValue;
import org.iglooproject.jpa.security.business.authority.model.Authority;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.bootstrap4.console.navigation.page.ConsoleAccessDeniedPage;
import org.iglooproject.wicket.bootstrap4.console.navigation.page.ConsoleLoginFailurePage;
import org.iglooproject.wicket.bootstrap4.console.navigation.page.ConsoleLoginSuccessPage;
import org.iglooproject.wicket.bootstrap4.console.navigation.page.ConsoleSignInPage;
import org.iglooproject.wicket.bootstrap4.console.template.ConsoleConfiguration;
import org.iglooproject.wicket.more.application.CoreWicketAuthenticatedApplication;
import org.iglooproject.wicket.more.console.common.model.ConsoleMenuSection;
import org.iglooproject.wicket.more.link.descriptor.parameter.CommonParameters;
import org.iglooproject.wicket.more.markup.html.pages.monitoring.DatabaseMonitoringPage;
import org.iglooproject.wicket.more.rendering.BooleanRenderer;
import org.iglooproject.wicket.more.rendering.EnumRenderer;
import org.iglooproject.wicket.more.rendering.LocaleRenderer;
import org.iglooproject.wicket.more.security.page.LoginFailurePage;
import org.iglooproject.wicket.more.security.page.LoginSuccessPage;
import org.iglooproject.wicket.more.util.convert.HibernateProxyAwareConverterLocator;
import org.iglooproject.wicket.more.util.listener.FormInvalidDecoratorListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.business.history.model.atomic.HistoryEventType;
import fr.portefeuille.core.business.portefeuille.model.atomic.CompteType;
import fr.portefeuille.core.business.user.model.BasicUser;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import fr.portefeuille.web.application.administration.page.AdministrationAnnouncementListPage;
import fr.portefeuille.web.application.administration.page.AdministrationBasicUserDetailPage;
import fr.portefeuille.web.application.administration.page.AdministrationBasicUserListPage;
import fr.portefeuille.web.application.administration.page.AdministrationTechnicalUserDetailPage;
import fr.portefeuille.web.application.administration.page.AdministrationTechnicalUserListPage;
import fr.portefeuille.web.application.administration.page.AdministrationUserGroupDetailPage;
import fr.portefeuille.web.application.administration.page.AdministrationUserGroupListPage;
import fr.portefeuille.web.application.common.converter.PostalCodeConverter;
import fr.portefeuille.web.application.common.renderer.AuthorityRenderer;
import fr.portefeuille.web.application.common.renderer.UserGroupRenderer;
import fr.portefeuille.web.application.common.renderer.UserRenderer;
import fr.portefeuille.web.application.common.template.favicon.ApplicationFaviconPackage;
import fr.portefeuille.web.application.common.template.resources.PortefeuilleResourcesPackage;
import fr.portefeuille.web.application.common.template.resources.styles.application.applicationaccess.ApplicationAccessScssResourceReference;
import fr.portefeuille.web.application.common.template.resources.styles.console.console.ConsoleScssResourceReference;
import fr.portefeuille.web.application.common.template.resources.styles.console.consoleaccess.ConsoleAccessScssResourceReference;
import fr.portefeuille.web.application.common.template.resources.styles.notification.NotificationScssResourceReference;
import fr.portefeuille.web.application.console.common.component.ConsoleAccessHeaderAdditionalContentPanel;
import fr.portefeuille.web.application.console.common.component.ConsoleHeaderAdditionalContentPanel;
import fr.portefeuille.web.application.console.common.component.ConsoleHeaderEnvironmentPanel;
import fr.portefeuille.web.application.console.notification.demo.page.ConsoleNotificationDemoIndexPage;
import fr.portefeuille.web.application.history.renderer.HistoryValueRenderer;
import fr.portefeuille.web.application.navigation.page.HomePage;
import fr.portefeuille.web.application.navigation.page.MaintenancePage;
import fr.portefeuille.web.application.portefeuille.page.CompteDetailPage;
import fr.portefeuille.web.application.portefeuille.page.CompteListPage;
import fr.portefeuille.web.application.portefeuille.page.OperationListPage;
import fr.portefeuille.web.application.portefeuille.page.PortefeuilleDetailPage;
import fr.portefeuille.web.application.profile.page.ProfilePage;
import fr.portefeuille.web.application.referencedata.page.ReferenceDataPage;
import fr.portefeuille.web.application.resources.application.PortefeuilleApplicationResources;
import fr.portefeuille.web.application.resources.business.PortefeuilleBusinessResources;
import fr.portefeuille.web.application.resources.common.PortefeuilleCommonResources;
import fr.portefeuille.web.application.resources.console.PortefeuilleConsoleResources;
import fr.portefeuille.web.application.resources.enums.PortefeuilleEnumResources;
import fr.portefeuille.web.application.resources.navigation.PortefeuilleNavigationResources;
import fr.portefeuille.web.application.resources.notifications.PortefeuilleNotificationResources;
import fr.portefeuille.web.application.security.login.page.SignInPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordCreationPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordExpirationPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordRecoveryPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordResetPage;

public class PortefeuilleApplication extends CoreWicketAuthenticatedApplication {
	
	public static final String NAME = "PortefeuilleApplication";
	
	@Autowired
	private IPropertyService propertyService;
	
	public static PortefeuilleApplication get() {
		final Application application = Application.get();
		if (application instanceof PortefeuilleApplication) {
			return (PortefeuilleApplication) application;
		}
		throw new WicketRuntimeException("There is no PortefeuilleApplication attached to current thread " +
			Thread.currentThread().getName());
	}
	
	@Override
	public void init() {
		super.init();
		
		// si on n'est pas en développement, on précharge les feuilles de styles pour éviter la ruée et permettre le remplissage du cache
		if (!propertyService.isConfigurationTypeDevelopment()) {
			preloadStyleSheets(
				ConsoleScssResourceReference.get(),
				NotificationScssResourceReference.get(),
				ApplicationAccessScssResourceReference.get(),
				fr.portefeuille.web.application.common.template.resources.styles.application.basic.StylesScssResourceReference.get(),
				fr.portefeuille.web.application.common.template.resources.styles.application.advanced.StylesScssResourceReference.get()
			);
		}
		
		getResourceSettings().getStringResourceLoaders().addAll(
			0, // Override the keys in existing resource loaders with the following 
			ImmutableList.of(
				new ClassStringResourceLoader(PortefeuilleApplicationResources.class),
				new ClassStringResourceLoader(PortefeuilleBusinessResources.class),
				new ClassStringResourceLoader(PortefeuilleCommonResources.class),
				new ClassStringResourceLoader(PortefeuilleConsoleResources.class),
				new ClassStringResourceLoader(PortefeuilleEnumResources.class),
				new ClassStringResourceLoader(PortefeuilleNavigationResources.class),
				new ClassStringResourceLoader(PortefeuilleNotificationResources.class)
			)
		);
		
		FormInvalidDecoratorListener.init(this);
	}
	
	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		
		converterLocator.set(Authority.class, AuthorityRenderer.get());
		converterLocator.set(User.class, UserRenderer.get());
		converterLocator.set(TechnicalUser.class, UserRenderer.get());
		converterLocator.set(BasicUser.class, UserRenderer.get());
		converterLocator.set(UserGroup.class, UserGroupRenderer.get());
		
		converterLocator.set(Locale.class, LocaleRenderer.get());
		converterLocator.set(Boolean.class, BooleanRenderer.get());
		
		converterLocator.set(HistoryValue.class, HistoryValueRenderer.get());
		converterLocator.set(HistoryEventType.class, EnumRenderer.get());
		
		converterLocator.set(PostalCode.class, PostalCodeConverter.get());
		
		converterLocator.set(CompteType.class, EnumRenderer.get());
		
		return new HibernateProxyAwareConverterLocator(converterLocator);
	}

	@Override
	protected void mountApplicationPages() {
		
		// Sign in
		mountPage("/login/", getSignInPageClass());
		mountPage("/login/failure/", LoginFailurePage.class);
		mountPage("/login/success/", LoginSuccessPage.class);
		
		mountPage("/security/password/recovery/", SecurityPasswordRecoveryPage.class);
		mountPage("/security/password/expiration/", SecurityPasswordExpirationPage.class);
		mountParameterizedPage("/security/password/reset/", SecurityPasswordResetPage.class);
		mountParameterizedPage("/security/password/creation/", SecurityPasswordCreationPage.class);
		
		// Maintenance
		mountPage("/maintenance/", MaintenancePage.class);
		
		// Profile
		mountPage("/profile/", ProfilePage.class);
		
		// Reference data
		mountPage("/reference-data/", ReferenceDataPage.class);
		
		// Portefeuille
		mountParameterizedPage("/portefeuille/detail/", PortefeuilleDetailPage.class);
		
		// Comptes
		mountPage("/comptes/", CompteListPage.class);
		mountParameterizedPage("/comptes/detail/", CompteDetailPage.class);
		
		// Operations
		mountPage("/operations/", OperationListPage.class);
		
		// Administration
		mountPage("/administration/basic-user/", AdministrationBasicUserListPage.class);
		mountParameterizedPage("/administration/basic-user/${" + CommonParameters.ID + "}/", AdministrationBasicUserDetailPage.class);
		mountPage("/administration/technical-user/", AdministrationTechnicalUserListPage.class);
		mountParameterizedPage("/administration/technical-user/${" + CommonParameters.ID + "}/", AdministrationTechnicalUserDetailPage.class);
		mountPage("/administration/user-group/", AdministrationUserGroupListPage.class);
		mountParameterizedPage("/administration/user-group/${" + CommonParameters.ID + "}/", AdministrationUserGroupDetailPage.class);
		mountPage("/administration/announcement/", AdministrationAnnouncementListPage.class);
		
		// Console sign in
		mountPage("/console/login/", ConsoleSignInPage.class);
		mountPage("/console/login/failure/", ConsoleLoginFailurePage.class);
		mountPage("/console/login/success/", ConsoleLoginSuccessPage.class);
		mountPage("/console/access-denied/", ConsoleAccessDeniedPage.class);
		
		// Console
		ConsoleConfiguration consoleConfiguration = ConsoleConfiguration.build("console", propertyService);
		consoleConfiguration.addCssResourceReference(ConsoleScssResourceReference.get());
		consoleConfiguration.addConsoleAccessCssResourceReference(ConsoleAccessScssResourceReference.get());
		consoleConfiguration.setConsoleAccessHeaderAdditionalContentComponentFactory(ConsoleAccessHeaderAdditionalContentPanel::new);
		consoleConfiguration.setConsoleHeaderEnvironmentComponentFactory(ConsoleHeaderEnvironmentPanel::new);
		consoleConfiguration.setConsoleHeaderAdditionalContentComponentFactory(ConsoleHeaderAdditionalContentPanel::new);
		
		ConsoleMenuSection notificationMenuSection = new ConsoleMenuSection(
			"notificationsMenuSection",
			"console.notifications",
			"notifications",
			ConsoleNotificationDemoIndexPage.class
		);
		consoleConfiguration.addMenuSection(notificationMenuSection);
		
		consoleConfiguration.mountPages(this);
		
		// Monitoring
		mountPage("/monitoring/db-access/", DatabaseMonitoringPage.class);
	}

	@Override
	protected void mountApplicationResources() {
		mountStaticResourceDirectory("/application", PortefeuilleResourcesPackage.class);
		
		// See favicon generator https://realfavicongenerator.net/
		mountResource("/android-chrome-192x192.png", new PackageResourceReference(ApplicationFaviconPackage.class, "android-chrome-192x192.png"));
		mountResource("/android-chrome-256x256.png", new PackageResourceReference(ApplicationFaviconPackage.class, "android-chrome-256x256.png"));
		mountResource("/apple-touch-icon.png", new PackageResourceReference(ApplicationFaviconPackage.class, "apple-touch-icon.png"));
		mountResource("/browserconfig.xml", new PackageResourceReference(ApplicationFaviconPackage.class, "browserconfig.xml"));
		mountResource("/favicon.ico", new PackageResourceReference(ApplicationFaviconPackage.class, "favicon.ico"));
		mountResource("/favicon-16x16.png", new PackageResourceReference(ApplicationFaviconPackage.class, "favicon-16x16.png"));
		mountResource("/favicon-32x32.png", new PackageResourceReference(ApplicationFaviconPackage.class, "favicon-32x32.png"));
		mountResource("/mstile-150x150.png", new PackageResourceReference(ApplicationFaviconPackage.class, "mstile-150x150.png"));
		mountResource("/safari-pinned-tab.svg", new PackageResourceReference(ApplicationFaviconPackage.class, "safari-pinned-tab.svg"));
		mountResource("/site.webmanifest", new PackageResourceReference(ApplicationFaviconPackage.class, "site.webmanifest"));
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return PortefeuilleSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	public Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

}
