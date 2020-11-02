package fr.portefeuille.web.application.compte.page;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;

import fr.portefeuille.core.business.compte.search.CompteSort;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.compte.model.CompteDataProvider;
import fr.portefeuille.web.application.compte.template.CompteTemplate;

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
		
		DecoratedCoreDataTablePanel<?, ?> resultats = 
			DataTableBuilder.start(compteDataProvider, compteDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.compte.label"), Bindings.compte().label())
					.withSort(CompteSort.LABEL, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-md align-middle")
				.addLabelColumn(new ResourceModel("business.compte.fondsDisponibles"), Bindings.compte().fondsDisponibles())
					.withClass("text text-sm align-middle")
				.addLabelColumn(new ResourceModel("business.compte.type"), Bindings.compte().type())
					.withClass("text text-sm align-middle")
				.bootstrapCard()
					.ajaxPagers()
					.count("compte.list.count")
				.build("resultats", 10);
		
		add(
			resultats
		);
	}

}
