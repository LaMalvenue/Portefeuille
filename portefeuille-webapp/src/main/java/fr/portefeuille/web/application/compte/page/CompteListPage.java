package fr.portefeuille.web.application.compte.page;

import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE;

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
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.markup.repeater.table.column.AbstractCoreColumn;
import org.iglooproject.wicket.more.model.BindingModel;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.search.CompteSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.CommonRenderers;
import fr.portefeuille.web.application.compte.model.CompteDataProvider;
import fr.portefeuille.web.application.compte.template.CompteTemplate;
import fr.portefeuille.web.application.portefeuille.page.PortefeuilleDetailPage;

public class CompteListPage extends CompteTemplate {

	private static final long serialVersionUID = -85288656017529091L;
	
	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(CompteListPage.class);
	}

	public CompteListPage(PageParameters parameters) {
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
				.addLabelColumn(new ResourceModel("business.compte.portefeuille"), Bindings.compte().portefeuille().nom())
					.withLink(Bindings.compte().portefeuille(), PortefeuilleDetailPage.MAPPER)
					.withSort(CompteSort.PORTEFEUILLE_NOM, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-md align-middle")
				.addLabelColumn(new ResourceModel("business.compte.label"), Bindings.compte().label())
					.withLink(CompteDetailPage.MAPPER)
					.withSort(CompteSort.LABEL, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-md align-middle")
				.addColumn(
						new AbstractCoreColumn<Compte, CompteSort>(new ResourceModel("business.compte.fondsDisponibles")) {
							private static final long serialVersionUID = 1L;
							@Override
							public void populateItem(Item<ICellPopulator<Compte>> cellItem, String componentId, IModel<Compte> rowModel) {
								cellItem.add(
									new FondsDisponiblesFragment(componentId, rowModel)
								);
							}
						}
					)
					.withSort(CompteSort.FONDS_DISPONIBLES, SortIconStyle.NUMERIC, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-sm align-middle")
				.addLabelColumn(new ResourceModel("business.compte.typeCompte"), Bindings.compte().type())
				.withSort(CompteSort.TYPE_COMPTE, SortIconStyle.DEFAULT, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-sm align-middle")
				.bootstrapCard()
					.ajaxPagers() 
					.count("compte.list.count")
				.build("resultats", propertyService.get(PORTFOLIO_ITEMS_PER_PAGE))
		);
	}
	
	private class FondsDisponiblesFragment extends Fragment {
		
		private static final long serialVersionUID = 1L;
		
		public FondsDisponiblesFragment(String id, final IModel<Compte> compteModel) {
			super(id, "fondsDisponiblesFragment", CompteListPage.this);
			
			IModel<Double> fondsDisponiblesModel = BindingModel.of(compteModel, Bindings.compte().fondsDisponibles());
			
			add(
				new CoreLabel("fondsDisponibles", CommonRenderers.sommeEuros().asModel(fondsDisponiblesModel))
			);
			
		}
	}
}
