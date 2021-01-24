package fr.portefeuille.web.application.navigation.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.template.model.BreadCrumbElement;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.portefeuille.component.PortefeuilleDetailComptesPanel;

public class HomePage extends MainTemplate {

	private static final long serialVersionUID = -6767518941118385548L;

	@SpringBean
	private IPortefeuilleService portefeuilleService;

	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(HomePage.class);
	}

	public HomePage(PageParameters parameters) {
		super(parameters);
		
		addBreadCrumbElement(new BreadCrumbElement(
			new ResourceModel("home.pageTitle"),
			HomePage.linkDescriptor()
		));
		
		add(new CoreLabel("pageTitle", new ResourceModel("home.pageTitle")));
		
		IModel<Portefeuille> portefeuilleModel = new GenericEntityModel<>(Portefeuille.get());
		
		add(
			new CoreLabel("title", new StringResourceModel("portefeuille.detail.title.param", portefeuilleModel)),
			new CoreLabel("nom", BindingModel.of(portefeuilleModel, Bindings.portefeuille().nom()))
				.showPlaceholder(),
			new CoreLabel("fondsTotauxDisponibles", BindingModel.of(portefeuilleModel, Bindings.portefeuille().fondsTotauxDisponibles()))
				.showPlaceholder(),
			new PortefeuilleDetailComptesPanel("comptes")
		);
	}

	@Override
	protected Condition displayBreadcrumb() {
		return Condition.alwaysFalse();
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return HomePage.class;
	}

}
