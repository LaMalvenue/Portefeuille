package fr.portefeuille.web.application.operation.page;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;

import fr.portefeuille.core.business.operation.search.OperationSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.operation.model.OperationDataProvider;
import fr.portefeuille.web.application.operation.template.OperationTemplate;

public class OperationListPage extends OperationTemplate {

	private static final long serialVersionUID = -5666697687639379935L;

	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(OperationListPage.class);
	}

	public OperationListPage(PageParameters parameters) {
		super(parameters);

		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
		);
		
		OperationDataProvider operationDataProvider = new OperationDataProvider();
		
		DecoratedCoreDataTablePanel<?, ?> resultats = 
			DataTableBuilder.start(operationDataProvider, operationDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.operation.label"), Bindings.operation().label())
					.withSort(OperationSort.LABEL, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-md align-middle")
				.addLabelColumn(new ResourceModel("business.operation.montant"), Bindings.operation().montant())
					.withClass("text text-sm align-middle")
				.addLabelColumn(new ResourceModel("business.operation.date"), Bindings.operation().date())
					.withClass("text text-sm align-middle")
				.addLabelColumn(new ResourceModel("business.operation.categorie"), Bindings.operation().categorie())
					.withClass("text text-sm align-middle")
				.addLabelColumn(new ResourceModel("business.operation.statut"), Bindings.operation().statut())
					.withClass("text text-sm align-middle")
				.bootstrapCard()
					.ajaxPagers()
					.count("operation.list.count")
				.build("resultats", 10);
		
		add(
			resultats
		);
	}

}
