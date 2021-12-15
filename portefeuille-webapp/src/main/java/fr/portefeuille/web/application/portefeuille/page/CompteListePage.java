package fr.portefeuille.web.application.portefeuille.page;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE;

import java.math.BigDecimal;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.markup.repeater.table.column.AbstractCoreColumn;
import org.iglooproject.wicket.more.model.BindingModel;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.search.CompteSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.CommonRenderers;
import fr.portefeuille.web.application.portefeuille.model.CompteDataProvider;
import fr.portefeuille.web.application.portefeuille.template.CompteTemplate;

public class CompteListePage extends CompteTemplate {

	private static final long serialVersionUID = -85288656017529091L;
	
	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(CompteListePage.class);
	}

	public CompteListePage(PageParameters parameters) {
		super(parameters);
		
		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
		);
		
		CompteDataProvider compteDataProvider = new CompteDataProvider();
		
		add(
			DataTableBuilder.start(compteDataProvider, compteDataProvider.getSortModel())
				.addColumn(
					new AbstractCoreColumn<Compte, CompteSort>(new ResourceModel("business.compte.solde")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void populateItem(Item<ICellPopulator<Compte>> cellItem, String componentId, IModel<Compte> rowModel) {
							cellItem.add(
								new FondsDisponiblesFragment(componentId, rowModel)
							);
						}
					}
				)
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.compte.type"), Bindings.compte().type())
					.showPlaceholder()
					.withClass("cell-xs")
				.bootstrapCard()
					.ajaxPagers()
					.count("compte.list.count")
				.build("resultats", propertyService.get(PORTFOLIO_ITEMS_PER_PAGE))
		);
	}

	private class FondsDisponiblesFragment extends Fragment {

		private static final long serialVersionUID = 1L;

		public FondsDisponiblesFragment(String id, final IModel<Compte> compteModel) {
			super(id, "fondsDisponiblesFragment", CompteListePage.this);

			IModel<BigDecimal> soldeModel = BindingModel.of(compteModel, Bindings.compte().solde());

			add(
				new CoreLabel("fondsDisponibles", CommonRenderers.montant().asModel(soldeModel))
			);

		}
	}
}
