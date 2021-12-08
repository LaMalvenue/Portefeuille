package fr.portefeuille.web.application.common.template;

import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.APPLICATION_THEME;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.MAINTENANCE_URL;
import static org.iglooproject.jpa.more.property.JpaMorePropertyIds.MAINTENANCE;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.functional.SerializableSupplier2;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.behavior.ClassAttributeAppender;
import org.iglooproject.wicket.bootstrap4.console.maintenance.search.page.ConsoleMaintenanceSearchPage;
import org.iglooproject.wicket.bootstrap4.markup.html.template.js.bootstrap.tooltip.BootstrapTooltip;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.feedback.AnimatedGlobalFeedbackPanel;
import org.iglooproject.wicket.more.markup.html.template.AbstractWebPageTemplate;
import org.iglooproject.wicket.more.markup.html.template.component.BodyBreadCrumbPanel;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.dropdown.BootstrapDropdownBehavior;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.tooltip.BootstrapTooltipDocumentBehavior;
import org.iglooproject.wicket.more.markup.html.template.model.BreadCrumbElement;
import org.iglooproject.wicket.more.markup.html.template.model.NavigationMenuItem;
import org.iglooproject.wicket.more.model.ApplicationPropertyModel;

import com.google.common.collect.ImmutableList;

import fr.portefeuille.core.security.model.PortefeuilleAuthorityConstants;
import fr.portefeuille.core.security.service.IPortefeuilleAuthenticationService;
import fr.portefeuille.core.security.service.ISecurityManagementService;
import fr.portefeuille.web.application.PortefeuilleApplication;
import fr.portefeuille.web.application.PortefeuilleSession;
import fr.portefeuille.web.application.common.component.AnnouncementsPanel;
import fr.portefeuille.web.application.common.template.theme.PortefeuilleApplicationTheme;
import fr.portefeuille.web.application.common.template.theme.common.BootstrapBreakpointPanel;
import fr.portefeuille.web.application.portefeuille.page.CompteListPage;
import fr.portefeuille.web.application.portefeuille.page.OperationListPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordExpirationPage;

public abstract class MainTemplate extends AbstractWebPageTemplate {

	private static final long serialVersionUID = -1312989780696228848L;

	@SpringBean
	private IPropertyService propertyService;

	@SpringBean
	private ISecurityManagementService securityManagementService;

	@SpringBean
	private IPortefeuilleAuthenticationService authenticationService;

	private final IModel<PortefeuilleApplicationTheme> applicationThemeModel = ApplicationPropertyModel.of(APPLICATION_THEME);

	public MainTemplate(PageParameters parameters) {
		super(parameters);
		
		if (Boolean.TRUE.equals(propertyService.get(MAINTENANCE)) && !authenticationService.hasAdminRole()) {
			throw new RedirectToUrlException(propertyService.get(MAINTENANCE_URL));
		}
		
		if (securityManagementService.isPasswordExpired(PortefeuilleSession.get().getUser())) {
			throw SecurityPasswordExpirationPage.linkDescriptor().newRestartResponseException();
		}
		
		add(
			new TransparentWebMarkupContainer("htmlElement")
				.add(AttributeAppender.append("lang", PortefeuilleSession.get().getLocale().getLanguage()))
		);
		
		add(
			new TransparentWebMarkupContainer("bodyElement")
				.add(new ClassAttributeAppender(PortefeuilleSession.get().getEnvironmentModel()))
		);
		
		addHeadPageTitlePrependedElement(new BreadCrumbElement(new ResourceModel("common.rootPageTitle")));
		add(createHeadPageTitle("headPageTitle"));
		
		add(new AnnouncementsPanel("announcements"));
		
		add(
			createBodyBreadCrumb("breadCrumb")
				.add(displayBreadcrumb().thenShow())
		);
		
		add(new AnimatedGlobalFeedbackPanel("feedback"));
		
		add(new BootstrapBreakpointPanel("bsBreakpoint"));
		
		add(new BootstrapTooltipDocumentBehavior(getBootstrapTooltip()));
		
		add(new BootstrapDropdownBehavior());
		
		getApplicationTheme().specificContent(
			this,
			(SerializableSupplier2<List<NavigationMenuItem>>) this::getMainNav,
			(SerializableSupplier2<Class<? extends WebPage>>) this::getFirstMenuPage,
			(SerializableSupplier2<Class<? extends WebPage>>) this::getSecondMenuPage
		);
	}

	protected List<NavigationMenuItem> getMainNav() {
		return ImmutableList.of(
			PortefeuilleApplication.get().getHomePageLinkDescriptor()
				.navigationMenuItem(new ResourceModel("navigation.portefeuille"))
				.iconClasses(Model.of("fa fa-fw fas fa-wallet")),
			CompteListPage.linkDescriptor()
				.navigationMenuItem(new ResourceModel("navigation.compte"))
				.iconClasses(Model.of("fa fa-fw fas fa-credit-card")),
			OperationListPage.linkDescriptor()
				.navigationMenuItem(new ResourceModel("navigation.operation"))
				.iconClasses(Model.of("fa fa-fw fas fa-euro-sign")),
			LinkDescriptorBuilder.start()
				.validator(Condition.role(PortefeuilleAuthorityConstants.ROLE_ADMIN))
				.page(ConsoleMaintenanceSearchPage.class)
				.navigationMenuItem(new ResourceModel("navigation.console"))
				.iconClasses(Model.of("fa fa-fw fa-wrench"))
		);
	}

	@Override
	protected Class<? extends WebPage> getSecondMenuPage() {
		return null;
	}

	@Override
	protected Component createBodyBreadCrumb(String wicketId) {
		// By default, we remove one element from the breadcrumb as it is usually also used to generate the page title.
		// The last element is usually the title of the current page and shouldn't be displayed in the breadcrumb.
		return new BodyBreadCrumbPanel(wicketId, bodyBreadCrumbPrependedElementsModel, breadCrumbElementsModel, 1)
			.setDividerModel(Model.of(""))
			.setTrailingSeparator(true);
	}

	protected Condition displayBreadcrumb() {
		return Condition.alwaysTrue();
	}

	protected BootstrapTooltip getBootstrapTooltip() {
		return new BootstrapTooltip()
			.selector("[title],[data-original-title]")
			.animation(true)
			.container("body");
	}

	protected PortefeuilleApplicationTheme getApplicationTheme() {
		return applicationThemeModel.getObject();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		getApplicationTheme().renderHead(response);
	}

	@Override
	public String getVariation() {
		return getApplicationTheme().getMarkupVariation();
	}

}
