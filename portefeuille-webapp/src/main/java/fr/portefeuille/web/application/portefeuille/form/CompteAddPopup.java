package fr.portefeuille.web.application.portefeuille.form;

import java.math.BigDecimal;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.link.BlankLink;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.AbstractAjaxModalPopupPanel;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.DelegatedMarkupPanel;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.ICompteService;
import fr.portefeuille.core.business.referencedata.model.CompteType;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.PortefeuilleSession;
import fr.portefeuille.web.application.common.form.ReferenceDataDropDownSingleChoice;
import fr.portefeuille.web.application.portefeuille.component.PortefeuilleDetailComptesPanel;

public class CompteAddPopup extends AbstractAjaxModalPopupPanel<Compte> {

	private static final long serialVersionUID = -3170273638215611224L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CompteAddPopup.class);

	@SpringBean
	private ICompteService compteService;
	
	private IModel<Portefeuille> portefeuilleModel = GenericEntityModel.of(PortefeuilleSession.get().getUser().getPortefeuille());

	private Form<Compte> form;

	public CompteAddPopup(String id) {
		super(id, GenericEntityModel.of(new Compte()));
	}

	@Override
	protected Component createHeader(String wicketId) {
		return new CoreLabel(wicketId, new ResourceModel("compte.action.add.title"));
	}

	@Override
	protected Component createBody(String wicketId) {
		DelegatedMarkupPanel body = new DelegatedMarkupPanel(wicketId, getClass());
		
		IModel<BigDecimal> soldeModel = BindingModel.of(getModel(), Bindings.compte().solde());
		
		form = new Form<>("form");
		
		form.add(
			new ReferenceDataDropDownSingleChoice<CompteType>("typeCompte", BindingModel.of(getModel(), Bindings.compte().type()), CompteType.class)
				.setLabel(new ResourceModel("business.compte.typeCompte"))
				.setRequired(true),
			new TextField<>("solde", soldeModel, BigDecimal.class)
				.setLabel(new ResourceModel("business.compte.fondsDisponibles"))
		);
		
		body.add(form);
		
		return body;
	}

	@Override
	protected Component createFooter(String wicketId) {
		DelegatedMarkupPanel footer = new DelegatedMarkupPanel(wicketId, getClass());
		
		footer.add(
			new AjaxButton("save", form) {
				private static final long serialVersionUID = 1L;
				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					try {
						Compte compte = CompteAddPopup.this.getModelObject();
						compte.setPortefeuille(portefeuilleModel.getObject());
						
						compteService.create(compte);
						
						Session.get().success(getString("common.success"));
						
						target.addChildren(getPage(), PortefeuilleDetailComptesPanel.class);
						closePopup(target);
					} catch (RestartResponseException e) { 
						throw e;
					} catch (Exception e) {
						LOGGER.error("Erreur lors de l'enregistrement du compte.", e);
						Session.get().error(getString("common.error.unexpected"));
					}
					FeedbackUtils.refreshFeedback(target, getPage());
				}
				
				@Override
				protected void onError(AjaxRequestTarget target) {
					FeedbackUtils.refreshFeedback(target, getPage());
				}
			}
		);
		
		BlankLink cancel = new BlankLink("cancel");
		addCancelBehavior(cancel);
		footer.add(cancel);
		
		return footer;
	}

	@Override
	protected void onShow(AjaxRequestTarget target) {
		super.onShow(target);
		getModel().setObject(new Compte());
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(portefeuilleModel);
	}

}