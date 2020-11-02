package fr.portefeuille.web.application.portefeuille.component;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;
import org.iglooproject.wicket.more.markup.html.action.IOneParameterAjaxAction;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel.AddInPlacement;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.util.model.Detachables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.service.ICompteService;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.ActionRenderers;
import fr.portefeuille.web.application.common.util.CssClassConstants;
import fr.portefeuille.web.application.compte.model.CompteDataProvider;

public class PortefeuilleDetailComptesPanel extends GenericPanel<Portefeuille> {

	private static final long serialVersionUID = -2142250114710217646L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleDetailComptesPanel.class);

	private final CompteDataProvider compteDataProvider;

	@SpringBean
	private ICompteService compteService;

//	private PathAddPopup addComptePopup;

	public PortefeuilleDetailComptesPanel(String id, final IModel<Portefeuille> portefeuilleModel) {
		super(id, portefeuilleModel);
		
		setOutputMarkupId(true);

		this.compteDataProvider = new CompteDataProvider();
		this.compteDataProvider.getPortefeuilleModel().setObject(portefeuilleModel.getObject());
		
		add(
			DataTableBuilder.start(compteDataProvider, compteDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.compte.label"), Bindings.compte().label())
//					.withLink(CompteDetailPage.MAPPER)
					.withClass("text text-md")
				.addActionColumn()
					.addConfirmAction(ActionRenderers.delete())
						.title(new ResourceModel("compte.action.delete.confirmation.title")) 
						.content(parameter ->
							new ResourceModel("compte.action.delete.confirmation.content"))
						.confirm()
						.onClick(new IOneParameterAjaxAction<IModel<Compte>>() {
							private static final long serialVersionUID = 1L;
							@Override
							public void execute(AjaxRequestTarget target, IModel<Compte> parameter) {
								try {
									compteService.delete(parameter.getObject());
									
									Session.get().success(getString("common.success"));
									target.add(PortefeuilleDetailComptesPanel.this);
								} catch (RestartResponseException e) {
									throw e;
								} catch (Exception e) {
									LOGGER.error("Une erreur inattendue est survenue lors de la suppression du compte", e);
									Session.get().error(getString("common.error.unexpected"));
									FeedbackUtils.refreshFeedback(target, getPage());
								}
							}
						})
						.hideLabel()
					.withClassOnElements(CssClassConstants.BTN_TABLE_ROW_ACTION)
					.end()
					.withClass("actions actions-1x")
				.bootstrapCard()
//					.addIn(AddInPlacement.FOOTER_RIGHT, (wicketId, table) -> new SectorPathAddFragment(wicketId, sectorModel))
					.ajaxPager(AddInPlacement.HEADING_RIGHT)
					.count("portefeuille.detail.comptes.count")
				.build("results", 5)
		);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(compteDataProvider);
	}

}
