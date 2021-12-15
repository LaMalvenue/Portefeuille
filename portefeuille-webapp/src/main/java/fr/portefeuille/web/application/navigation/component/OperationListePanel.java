package fr.portefeuille.web.application.navigation.component;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel.AddInPlacement;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.ICompteService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.portefeuille.model.OperationDataProvider;
import fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds;

public class OperationListePanel extends GenericPanel<Portefeuille> {

	private static final long serialVersionUID = -2142250114710217646L;

	@SpringBean
	private IPropertyService propertyService;

	@SpringBean
	private ICompteService compteService;

	public OperationListePanel(String id, final IModel<Portefeuille> portefeuilleModel) {
		super(id, portefeuilleModel);
		setOutputMarkupId(true);
		
		OperationDataProvider operationDataProvider = new OperationDataProvider();
		operationDataProvider.getPortefeuilleModel().setObject(getModelObject());
		
		add(
			DataTableBuilder.start(operationDataProvider, operationDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.operation.date"), Bindings.operation().date())
					.showPlaceholder()
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.montant"), Bindings.operation().montant())
					.showPlaceholder()
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.label"), Bindings.operation().label())
					.showPlaceholder()
					.withClass("cell-xs")
				.bootstrapCard()
					.ajaxPager(AddInPlacement.HEADING_RIGHT)
					.count("portefeuille.detail.operations.count")
				.build("resultats", propertyService.get(PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE))
		);
	}
	
}
