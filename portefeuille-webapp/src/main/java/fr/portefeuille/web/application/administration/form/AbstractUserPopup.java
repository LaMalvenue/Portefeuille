package fr.portefeuille.web.application.administration.form;

import java.util.Collections;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.atomic.UserPasswordRecoveryRequestInitiator;
import fr.portefeuille.core.business.user.model.atomic.UserPasswordRecoveryRequestType;
import fr.portefeuille.core.business.user.service.IUserService;
import fr.portefeuille.core.security.service.ISecurityManagementService;
import fr.portefeuille.web.application.PortefeuilleSession;
import fr.portefeuille.web.application.common.validator.UsernamePatternValidator;
import org.iglooproject.spring.property.SpringPropertyIds;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.spring.util.StringUtils;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.form.FormMode;
import org.iglooproject.wicket.more.markup.html.form.ModelValidatingForm;
import org.iglooproject.wicket.more.markup.html.link.BlankLink;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.AbstractAjaxModalPopupPanel;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.DelegatedMarkupPanel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUserPopup<U extends User> extends AbstractAjaxModalPopupPanel<U> {

	private static final long serialVersionUID = -3575009149241618972L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserPopup.class);

	protected static final UsernamePatternValidator USERNAME_PATTERN_VALIDATOR =
		new UsernamePatternValidator() {
			private static final long serialVersionUID = 1L;
			@Override
			protected IValidationError decorate(IValidationError error, IValidatable<String> validatable) {
				((ValidationError) error).setKeys(Collections.singletonList("common.validator.username.pattern"));
				return error;
			}
		};

	@SpringBean
	protected IUserService userService;

	@SpringBean
	protected ISecurityManagementService securityManagementService;

	@SpringBean
	protected IPropertyService propertyService;

	protected final IModel<FormMode> formModeModel = new Model<>(FormMode.ADD);

	protected ModelValidatingForm<U> form;

	protected final IModel<String> passwordModel = Model.of();

	protected AbstractUserPopup(String id) {
		super(id, new GenericEntityModel<Long, U>());
	}

	@Override
	protected Component createHeader(String wicketId) {
		return new CoreLabel(
			wicketId,
			addModeCondition()
				.then(new ResourceModel("administration.user.action.add.title"))
				.otherwise(new StringResourceModel("administration.user.action.edit.title", getModel()))
		);
	}

	@Override
	protected Component createFooter(String wicketId) {
		DelegatedMarkupPanel footer = new DelegatedMarkupPanel(wicketId, AbstractUserPopup.class);
		
		footer.add(
			new AjaxButton("save", form) {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					try {
						IModel<U> userModel = AbstractUserPopup.this.getModel();
						U user = userModel.getObject();
						
						if (addModeCondition().applies()) {
							String password = passwordModel.getObject();
							
							userService.create(user);
							userService.onCreate(user, PortefeuilleSession.get().getUser());
							
							if (StringUtils.hasText(password)) {
								securityManagementService.updatePassword(user, password, PortefeuilleSession.get().getUser());
							} else {
								securityManagementService.initiatePasswordRecoveryRequest(
										user,
										UserPasswordRecoveryRequestType.CREATION,
										UserPasswordRecoveryRequestInitiator.ADMIN,
										PortefeuilleSession.get().getUser()
								);
								
								Session.get().success(getString("administration.user.action.add.success.notification"));
							}
							
							Session.get().success(getString("common.success"));
							
							doOnSuccess(userModel);
						} else {
							User authenticatedUser = PortefeuilleSession.get().getUser();
							if (authenticatedUser != null && authenticatedUser.equals(user) && user.getLocale() != null) {
								PortefeuilleSession.get().setLocale(user.getLocale());
							}
							userService.update(user);
							Session.get().success(getString("common.success"));
							closePopup(target);
							target.add(getPage());
						}
					} catch (RestartResponseException e) { // NOSONAR
						throw e;
					} catch (Exception e) {
						if (addModeCondition().applies()) {
							LOGGER.error("Error occured while creating user", e);
						} else {
							LOGGER.error("Error occured while updating user", e);
						}
						Session.get().error(getString("common.error.unexpected"));
					}
					FeedbackUtils.refreshFeedback(target, getPage());
				}
				
				@Override
				protected void onError(AjaxRequestTarget target) {
					FeedbackUtils.refreshFeedback(target, getPage());
				}
			}
				.add(new CoreLabel(
					"label",
					addModeCondition()
						.then(new ResourceModel("common.action.create"))
						.otherwise(new ResourceModel("common.action.save"))
				))
		);
		
		BlankLink cancel = new BlankLink("cancel");
		addCancelBehavior(cancel);
		footer.add(cancel);
		
		return footer;
	}

	protected abstract void doOnSuccess(IModel<U> userModel);

	public void setUpAdd(U user) {
		if (user.getLocale() == null) {
			user.setLocale(propertyService.get(SpringPropertyIds.DEFAULT_LOCALE));
		}
		
		getModel().setObject(user);
		formModeModel.setObject(FormMode.ADD);
	}

	public void setUpEdit(U user) {
		if (user.getLocale() == null) {
			user.setLocale(propertyService.get(SpringPropertyIds.DEFAULT_LOCALE));
		}
		
		getModel().setObject(user);
		formModeModel.setObject(FormMode.EDIT);
	}

	protected Condition addModeCondition() {
		return FormMode.ADD.condition(formModeModel);
	}

	protected Condition editModeCondition() {
		return FormMode.EDIT.condition(formModeModel);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(
			formModeModel,
			passwordModel
		);
	}

}
