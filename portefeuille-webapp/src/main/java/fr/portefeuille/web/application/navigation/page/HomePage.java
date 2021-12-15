package fr.portefeuille.web.application.navigation.page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.behavior.ClassAttributeAppender;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.template.model.BreadCrumbElement;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.PortefeuilleSession;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.navigation.component.OperationAddPanel;
import fr.portefeuille.web.application.navigation.component.OperationListePanel;
import fr.portefeuille.web.application.portefeuille.page.CompteListePage;

public class HomePage extends MainTemplate {

	private static final long serialVersionUID = -6767518941118385548L;

	@SpringBean
	private IPortefeuilleService portefeuilleService;
	
	@SpringBean
	private IPropertyService propertyService;

	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(HomePage.class);
	}

	private final IModel<Portefeuille> portefeuilleModel = GenericEntityModel.of(Bindings.portefeuille().apply(portefeuilleService.getByProprietaire(PortefeuilleSession.get().getUser())));

	public HomePage(PageParameters parameters) {
		super(parameters);
		
		addBreadCrumbElement(new BreadCrumbElement(
			new ResourceModel("home.pageTitle"),
			HomePage.linkDescriptor()
		));
		
		add(new CoreLabel("pageTitle", new ResourceModel("home.pageTitle")));
		
		add(
			new OperationAddPanel("addOperation", portefeuilleModel),
			
			new SoldeFragment("solde"),
			
			new OperationListePanel("dernieresOperations", portefeuilleModel)
		);
	}

	private class SoldeFragment extends Fragment {

		private static final long serialVersionUID = 1L;

		public SoldeFragment(String id) {
			super(id, "soldeFragment", HomePage.this);
			
			add(
				new WebMarkupContainer("header")
					.add(
						new WebMarkupContainer("trelloLogo")
							.add(new ClassAttributeAppender("fa fab fa-trello")),
						new CoreLabel("title",  new ResourceModel("navigation.compte"))
					),
				new CoreLabel("solde", BindingModel.of(portefeuilleModel, Bindings.portefeuille().solde()))
					.showPlaceholder(),
				CompteListePage.linkDescriptor().link("details")
			);
		}
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return HomePage.class;
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(
			portefeuilleModel
		);
	}

}
