package fr.portefeuille.web.application.portefeuille.page;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.link.descriptor.mapper.IOneParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.link.descriptor.parameter.CommonParameters;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.CommonRenderers;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.navigation.page.HomePage;
import fr.portefeuille.web.application.portefeuille.component.PortefeuilleDetailComptesPanel;

public class PortefeuilleDetailPage extends MainTemplate {

	private static final long serialVersionUID = -4765386531030260936L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleDetailPage.class);

	@SpringBean
	IPortefeuilleService portefeuilleService;

	public static final IOneParameterLinkDescriptorMapper<IPageLinkDescriptor, Portefeuille> MAPPER =
		LinkDescriptorBuilder.start()
			.model(Portefeuille.class)
			.map(CommonParameters.ID).mandatory()
			.page(PortefeuilleDetailPage.class);

	public PortefeuilleDetailPage(PageParameters parameters) {
		super(parameters);
		
		IModel<Portefeuille> portefeuilleModel = new GenericEntityModel<>();
		IModel<BigDecimal> fondsTotauxDisponiblesModel = BindingModel.of(portefeuilleModel, Bindings.portefeuille().solde());
		
		MAPPER
			.map(portefeuilleModel)
			.extractSafely(
				parameters,
				HomePage.linkDescriptor(),
				getString("common.error.unexpected")
			);
		
		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
			.add(
				new EnclosureContainer("actionsContainer")
					.anyChildVisible()
				);
		
		add(
			headerElementsSection,
			new CoreLabel("title", new StringResourceModel("portefeuille.detail.title", portefeuilleModel)),
			new CoreLabel("fondsTotauxDisponibles", CommonRenderers.montant().asModel(fondsTotauxDisponiblesModel))
				.showPlaceholder(),
			new PortefeuilleDetailComptesPanel("comptes", portefeuilleModel)
		);
		
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return HomePage.class;
	}

}