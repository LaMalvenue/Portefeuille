package fr.portefeuille.web.application.navigation.page;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE;

import java.math.BigDecimal;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.template.model.BreadCrumbElement;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.markup.repeater.table.column.AbstractCoreColumn;
import org.iglooproject.wicket.more.model.BindingModel;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.search.PortefeuilleSort;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.CommonRenderers;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.portefeuille.model.PortefeuilleDataProvider;
import fr.portefeuille.web.application.portefeuille.page.PortefeuilleDetailPage;

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

	public HomePage(PageParameters parameters) {
		super(parameters);
		
		addBreadCrumbElement(new BreadCrumbElement(
			new ResourceModel("home.pageTitle"),
			HomePage.linkDescriptor()
		));
		
		add(new CoreLabel("pageTitle", new ResourceModel("home.pageTitle")));
		
		PortefeuilleDataProvider portefeuilleDataProvider = new PortefeuilleDataProvider();
		
		DecoratedCoreDataTablePanel<?, ?> resultats = 
			DataTableBuilder.start(portefeuilleDataProvider, portefeuilleDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.portefeuille.nom"), Bindings.portefeuille())
					.withLink(PortefeuilleDetailPage.MAPPER)
					.withClass("text text-md align-middle")
				.addColumn(
					new AbstractCoreColumn<Portefeuille, PortefeuilleSort>(new ResourceModel("business.portefeuille.fondsTotauxDisponibles")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void populateItem(Item<ICellPopulator<Portefeuille>> cellItem, String componentId, IModel<Portefeuille> rowModel) {
							cellItem.add(
								new FondsDisponiblesFragment(componentId, rowModel)
							);
						}
					}
				)
				.bootstrapCard()
					.ajaxPagers()
					.build("resultats", propertyService.get(PORTFOLIO_ITEMS_PER_PAGE));
		
		add(
			resultats
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

	private class FondsDisponiblesFragment extends Fragment {

		private static final long serialVersionUID = 1L;

		public FondsDisponiblesFragment(String id, final IModel<Portefeuille> portefeuilleModel) {
			super(id, "fondsDisponiblesFragment", HomePage.this);

			IModel<BigDecimal> fondsDisponiblesModel = BindingModel.of(portefeuilleModel, Bindings.portefeuille().fondsTotauxDisponibles());

			add(
				new CoreLabel("fondsDisponibles", CommonRenderers.sommeEuros().asModel(fondsDisponiblesModel))
			);

		}
	}
}
