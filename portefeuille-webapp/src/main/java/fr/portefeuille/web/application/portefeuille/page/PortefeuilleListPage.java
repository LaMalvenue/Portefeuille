package fr.portefeuille.web.application.portefeuille.page;
import static fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE;

import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.iglooproject.wicket.more.markup.html.link.BlankLink;
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.behavior.AjaxModalOpenBehavior;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.markup.repeater.table.column.AbstractCoreColumn;
import org.iglooproject.wicket.more.model.BindingModel;
import org.wicketstuff.wiquery.core.events.MouseEvent;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.search.PortefeuilleSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.CommonRenderers;
import fr.portefeuille.web.application.portefeuille.form.PortefeuillePopup;
import fr.portefeuille.web.application.portefeuille.model.PortefeuilleDataProvider;
import fr.portefeuille.web.application.portefeuille.template.PortefeuilleTemplate;

public class PortefeuilleListPage extends PortefeuilleTemplate {

	private static final long serialVersionUID = -5573395853755124837L;
	
	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(PortefeuilleListPage.class);
	}

	public PortefeuilleListPage(PageParameters parameters) {
		super(parameters);
		
		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		PortefeuillePopup popup = new PortefeuillePopup("popup");
		add(popup);
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
				.add(
					new BlankLink("add")
					.add(new AjaxModalOpenBehavior(popup, MouseEvent.CLICK) {
						private static final long serialVersionUID = 1L;
						@Override
						protected void onShow(AjaxRequestTarget target) {
							popup.setUpAdd();
						}
					}),
					PortefeuilleCreatePage.linkDescriptor().link("addPage")
				)
		);
		
		PortefeuilleDataProvider portefeuilleDataProvider = new PortefeuilleDataProvider();
		
		DecoratedCoreDataTablePanel<?, ?> resultats = 
			DataTableBuilder.start(portefeuilleDataProvider, portefeuilleDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.portefeuille.nom"), Bindings.portefeuille().nom())
					.withLink(PortefeuilleDetailPage.MAPPER)
					.withSort(PortefeuilleSort.NOM, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
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
					.count("portefeuille.list.count")
					.build("resultats", propertyService.get(PORTFOLIO_ITEMS_PER_PAGE));
		
		add(
			resultats
		);
	}

	private class FondsDisponiblesFragment extends Fragment {

		private static final long serialVersionUID = 1L;

		public FondsDisponiblesFragment(String id, final IModel<Portefeuille> portefeuilleModel) {
			super(id, "fondsDisponiblesFragment", PortefeuilleListPage.this);

			IModel<Double> fondsDisponiblesModel = BindingModel.of(portefeuilleModel, Bindings.portefeuille().fondsTotauxDisponibles());

			add(
				new CoreLabel("fondsDisponibles", CommonRenderers.sommeEuros().asModel(fondsDisponiblesModel))
			);

		}
	}
}
