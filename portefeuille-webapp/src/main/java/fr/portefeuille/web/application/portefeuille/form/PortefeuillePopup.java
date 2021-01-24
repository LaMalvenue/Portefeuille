package fr.portefeuille.web.application.portefeuille.form;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.common.behavior.UpdateOnChangeAjaxEventBehavior;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.form.FormMode;
import org.iglooproject.wicket.more.markup.html.link.BlankLink;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.AbstractAjaxModalPopupPanel;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.DelegatedMarkupPanel;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.portefeuille.page.PortefeuilleDetailPage;

public class PortefeuillePopup extends AbstractAjaxModalPopupPanel<Portefeuille> {
	private static final long serialVersionUID = 8677502817018403435L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuillePopup.class);

	@SpringBean
	private IPortefeuilleService portefeuilleService;

	protected Form<Portefeuille> form;	
	protected final IModel<FormMode> formModeModel = new Model<>(FormMode.ADD);

	public PortefeuillePopup(String id) {
		this(id, new GenericEntityModel<>());
	}

	public PortefeuillePopup(String id, IModel<Portefeuille> portefeuilleModel) {
		super(id, portefeuilleModel);
	}

	@Override
	protected Component createHeader(String wicketId) {
		if (addModeCondition().applies()) {
			return new CoreLabel(wicketId, new ResourceModel("portefeuille.create.title"));
		} else {
			return new CoreLabel(wicketId, new StringResourceModel("portefeuille.edit.title", formModeModel));
		}
	}

	@Override
	protected Component createBody(String wicketId) {
		DelegatedMarkupPanel body = new DelegatedMarkupPanel(wicketId, getClass());
		
		IModel<Portefeuille> portefeuilleModel = getModel();
		
		form = new Form<>("form", portefeuilleModel);
		body.add(form);
		
		IModel<String> nomModel = BindingModel.of(portefeuilleModel, Bindings.portefeuille().nom());
		TextField<String> nom = new TextField<>("nom", nomModel, String.class);
		
		form
			.add(
				nom
				.setLabel(new ResourceModel("business.portefeuille.nom"))
				.setRequired(true)
				.add(new UpdateOnChangeAjaxEventBehavior())
			);
		
		return body;
	}

	@Override
	protected Component createFooter(String wicketId) {
		DelegatedMarkupPanel footer = new DelegatedMarkupPanel(wicketId, getClass());
		
		footer.add(new AjaxButton("save", form) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				try {
					Portefeuille portefeuille = PortefeuillePopup.this.getModelObject();
					
					if (addModeCondition().applies()) {
						onSubmitAddMode(portefeuille);
						Session.get().success(getString("portefeuille.create.success"));
					} else {
						onSubmitEditMode(portefeuille);
						Session.get().success(getString("portefeuille.edit.success"));
					}
					closePopup(target);
					refresh(target);
				} catch (RestartResponseException e) { // NOSONAR
					throw e;
				} catch (Exception e) {
					if (addModeCondition().applies()) {
						LOGGER.error("Erreur lors de l'ajout d'un portefeuille.", e);
					} else {
						LOGGER.error("Erreur lors de la mise à jour du portefeuille.", e);
					}
					Session.get().error(getString("common.error.unexpected"));
				}
				FeedbackUtils.refreshFeedback(target, getPage());
			}
			
			@Override
			protected void onError(AjaxRequestTarget target) {
				FeedbackUtils.refreshFeedback(target, getPage());
			}
		});
		
		BlankLink cancel = new BlankLink("cancel");
		addCancelBehavior(cancel);
		footer.add(cancel);
		
		return footer;
	}

	@Override
	protected void onShow(AjaxRequestTarget target) {
		super.onShow(target);
	}

	protected Condition addModeCondition() {
		return FormMode.ADD.condition(formModeModel);
	}

	protected Condition editModeCondition() {
		return FormMode.EDIT.condition(formModeModel);
	}

	public void setUpAdd() {
		getModel().setObject(new Portefeuille());
		formModeModel.setObject(FormMode.ADD);
	}

	public void setUpEdit(Portefeuille portefeuille) {
		getModel().setObject(portefeuille);
	}

	protected void onSubmitAddMode(Portefeuille portefeuille) {
		try {
			portefeuilleService.create(portefeuille);
			
			Session.get().success(getString("common.success"));
			throw PortefeuilleDetailPage.MAPPER.map(PortefeuillePopup.this.getModel()).newRestartResponseException();
		} catch (RestartResponseException e) { // NOSONAR
			throw e;
		} catch (Exception e) {
			LOGGER.error("Une erreur est survenue lors de la création du portefeuille.", e);
			Session.get().error(getString("common.error.unexpected"));
		}
	}

	protected void onSubmitEditMode(Portefeuille portefeuille) {
		try {
			portefeuilleService.update(portefeuille);
			
			Session.get().success(getString("common.success"));
			throw PortefeuilleDetailPage.MAPPER.map(PortefeuillePopup.this.getModel()).newRestartResponseException();
		} catch (RestartResponseException e) { // NOSONAR
			throw e;
		} catch (Exception e) {
			LOGGER.error("Une erreur est survenue lors de l'enregistrement du portefeuille.", e);
			Session.get().error(getString("common.error.unexpected"));
		}
	}

	protected void refresh(AjaxRequestTarget target) {
	}

}
