package fr.portefeuille.web.application.portefeuille.page;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;

import fr.portefeuille.core.business.portefeuille.model.atomic.OperationStatut;
import fr.portefeuille.core.business.portefeuille.search.OperationSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.portefeuille.model.OperationDataProvider;
import fr.portefeuille.web.application.portefeuille.template.OperationTemplate;

public class OperationListePage extends OperationTemplate {

	private static final long serialVersionUID = -5666697687639379935L;

	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(OperationListePage.class);
	}

	public OperationListePage(PageParameters parameters) {
		super(parameters);

		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
		);
		
		OperationDataProvider operationDataProvider = new OperationDataProvider();
		operationDataProvider.getStatutModel().setObject(OperationStatut.A_VENIR);
		
		add(
			DataTableBuilder.start(operationDataProvider, operationDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.operation.date"), Bindings.operation().date())
					.withSort(OperationSort.DATE, SortIconStyle.DEFAULT, CycleMode.DEFAULT_REVERSE)
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.montant"), Bindings.operation().montant())
					.withSort(OperationSort.MONTANT, SortIconStyle.DEFAULT, CycleMode.DEFAULT_REVERSE)
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.label"), Bindings.operation().label())
					.withClass("cell-xl")
				.addLabelColumn(new ResourceModel("business.operation.categorie"), Bindings.operation().categorie())
					.withClass("cell-xs")
				.bootstrapCard()
					.ajaxPagers()
					.count("operation.list.aVenir.count")
				.build("resultatsAVenir")
		);
		
		operationDataProvider.getStatutModel().setObject(OperationStatut.EFFECTUE);
		
		add(
			DataTableBuilder.start(operationDataProvider, operationDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.operation.date"), Bindings.operation().date())
					.withSort(OperationSort.DATE, SortIconStyle.DEFAULT, CycleMode.DEFAULT_REVERSE)
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.montant"), Bindings.operation().montant())
					.withSort(OperationSort.MONTANT, SortIconStyle.DEFAULT, CycleMode.DEFAULT_REVERSE)
					.withClass("cell-xs")
				.addLabelColumn(new ResourceModel("business.operation.label"), Bindings.operation().label())
					.withClass("cell-xl")
				.addLabelColumn(new ResourceModel("business.operation.categorie"), Bindings.operation().categorie())
					.withClass("cell-xs")
				.bootstrapCard()
					.ajaxPagers()
					.count("operation.list.effectue.count")
				.build("resultatsEffectues")
		);
	}

}
