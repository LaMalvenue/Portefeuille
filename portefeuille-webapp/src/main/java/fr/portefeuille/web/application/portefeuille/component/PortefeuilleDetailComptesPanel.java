package fr.portefeuille.web.application.portefeuille.component;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;
import org.iglooproject.wicket.more.markup.html.action.IOneParameterAjaxAction;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.link.BlankLink;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.behavior.AjaxModalOpenBehavior;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel.AddInPlacement;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.wiquery.core.events.MouseEvent;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.ICompteService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.common.renderer.ActionRenderers;
import fr.portefeuille.web.application.common.util.CssClassConstants;
import fr.portefeuille.web.application.portefeuille.form.CompteAddPopup;
import fr.portefeuille.web.application.portefeuille.model.CompteDataProvider;
import fr.portefeuille.web.application.portefeuille.page.CompteDetailPage;
import fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds;

public class PortefeuilleDetailComptesPanel extends GenericPanel<Portefeuille> {

	private static final long serialVersionUID = -2142250114710217646L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleDetailComptesPanel.class);

	@SpringBean
	private IPropertyService propertyService;
	@SpringBean
	private ICompteService compteService;

	private CompteAddPopup addComptePopup;

	public PortefeuilleDetailComptesPanel(String id, final IModel<Portefeuille> portefeuilleModel) {
		super(id, portefeuilleModel);
		
		CompteDataProvider compteDataProvider;
		
		setOutputMarkupId(true);
		add(addComptePopup= new CompteAddPopup("addComptePopup", portefeuilleModel));
		
		compteDataProvider = new CompteDataProvider();
		
		add(
			DataTableBuilder.start(compteDataProvider, compteDataProvider.getSortModel())
				.addLabelColumn(new ResourceModel("business.compte.label"), Bindings.compte().label())
					.withLink(CompteDetailPage.MAPPER)
					.withClass("text text-md")
				.addLabelColumn(new ResourceModel("business.compte.fondsDisponibles"), Bindings.compte().solde())
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
					.addIn(AddInPlacement.FOOTER_RIGHT, (wicketId, table) -> new CompteAddFragment(wicketId))
					.ajaxPager(AddInPlacement.HEADING_RIGHT)
					.count("portefeuille.detail.comptes.count")
				.build("results", propertyService.get(PortefeuilleWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE))
		);
	}
	
	private class CompteAddFragment extends Fragment {
		
		private static final long serialVersionUID = 1L;
		
		public CompteAddFragment(String id) {
			super(id, "addCompte", PortefeuilleDetailComptesPanel.this);
			
			add(
				new BlankLink("add")
					.add(new AjaxModalOpenBehavior(addComptePopup, MouseEvent.CLICK))
			);
		}
	}
}
