package fr.portefeuille.web.application.portefeuille.page;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.sort.SortIconStyle;
import org.iglooproject.wicket.more.markup.html.sort.TableSortLink.CycleMode;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;

import fr.portefeuille.core.business.portefeuille.search.PortefeuilleSort;
import fr.portefeuille.core.util.binding.Bindings;
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
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
		);
		
		PortefeuilleDataProvider portefeuilleDataProvider = new PortefeuilleDataProvider();
		
		DecoratedCoreDataTablePanel<?, ?> resultats = 
			DataTableBuilder.start(portefeuilleDataProvider, portefeuilleDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.portefeuille.nom"), Bindings.portefeuille().nom())
					.withSort(PortefeuilleSort.NOM, SortIconStyle.ALPHABET, CycleMode.DEFAULT_REVERSE)
					.withClass("text text-md align-middle")
				.addLabelColumn(new ResourceModel("business.portefeuille.fondsTotauxDisponibles"), Bindings.portefeuille().fondsTotauxDisponibles())
					.withClass("text text-sm align-middle")
				.bootstrapCard()
					.ajaxPagers()
					.count("portefeuille.list.count")
				.build("resultats", 10);
		
		add(
			resultats
		);
		
	}
}
