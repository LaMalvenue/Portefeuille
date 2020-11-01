package fr.portefeuille.web.application.administration.form;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.typedescriptor.UserTypeDescriptor;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.administration.page.AdministrationTechnicalUserDetailPage;
import fr.portefeuille.web.application.common.model.UserTypeDescriptorModel;
import fr.portefeuille.web.application.common.validator.EmailUnicityValidator;
import fr.portefeuille.web.application.common.validator.UserPasswordValidator;
import fr.portefeuille.web.application.common.validator.UsernameUnicityValidator;
import org.iglooproject.functional.Predicates2;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.form.LocaleDropDownChoice;
import org.iglooproject.wicket.more.markup.html.form.ModelValidatingForm;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.modal.component.DelegatedMarkupPanel;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.util.model.Detachables;

public class TechnicalUserPopup extends AbstractUserPopup<TechnicalUser> {

	private static final long serialVersionUID = -1282527654253780436L;

	private final IModel<? extends UserTypeDescriptor<TechnicalUser>> userTypeDescriptorModel;

	public TechnicalUserPopup(String id) {
		super(id);
		
		this.userTypeDescriptorModel = UserTypeDescriptorModel.fromUser(getModel());
	}

	@Override
	protected Component createBody(String wicketId) {
		DelegatedMarkupPanel body = new DelegatedMarkupPanel(wicketId, getClass());
		
		form = new ModelValidatingForm<>("form", getModel());
		body.add(form);
		
		boolean passwordRequired =
				securityManagementService.getOptions(TechnicalUser.class).isPasswordAdminUpdateEnabled()
			&&	!securityManagementService.getOptions(TechnicalUser.class).isPasswordUserRecoveryEnabled();
		
		PasswordTextField passwordField = new PasswordTextField("password", passwordModel);
		PasswordTextField confirmPasswordField = new PasswordTextField("confirmPassword", Model.of());
		
		form
			.add(
				new TextField<String>("firstName", BindingModel.of(getModel(), Bindings.user().firstName()))
					.setLabel(new ResourceModel("business.user.firstName"))
					.setRequired(true),
				new TextField<String>("lastName", BindingModel.of(getModel(), Bindings.user().lastName()))
					.setLabel(new ResourceModel("business.user.lastName"))
					.setRequired(true),
				new TextField<String>("username", BindingModel.of(getModel(), Bindings.user().username()))
					.setLabel(new ResourceModel("business.user.username"))
					.setRequired(true)
					.add(USERNAME_PATTERN_VALIDATOR)
					.add(new UsernameUnicityValidator(getModel())),
				new EnclosureContainer("addContainer")
					.condition(addModeCondition())
					.add(
						new EnclosureContainer("passwordContainer")
							.condition(Condition.predicate(Model.of(securityManagementService.getOptions(TechnicalUser.class).isPasswordAdminUpdateEnabled()), Predicates2.isTrue()))
							.add(
								passwordField
									.setLabel(new ResourceModel("business.user.password"))
									.setRequired(passwordRequired),
								new CoreLabel("passwordHelp",
									new StringResourceModel("security.${resourceKeyBase}.password.help", userTypeDescriptorModel)
										.setDefaultValue(new ResourceModel("security.user.password.help"))
								),
								confirmPasswordField
									.setLabel(new ResourceModel("business.user.confirmPassword"))
									.setRequired(passwordRequired)
							),
						
						new CheckBox("active", BindingModel.of(getModel(), Bindings.user().active()))
							.setLabel(new ResourceModel("business.user.active"))
							.setOutputMarkupId(true)
					),
				new EmailTextField("email", BindingModel.of(getModel(), Bindings.user().email()))
					.setLabel(new ResourceModel("business.user.email"))
					.add(EmailAddressValidator.getInstance())
					.add(new EmailUnicityValidator(getModel())),
				new LocaleDropDownChoice("locale", BindingModel.of(getModel(), Bindings.user().locale()))
					.setLabel(new ResourceModel("business.user.locale"))
					.setRequired(true)
			);
		
		form.add(new EqualPasswordInputValidator(passwordField, confirmPasswordField));
		
		form.add(
			new UserPasswordValidator(userTypeDescriptorModel.map(UserTypeDescriptor::getClazz), passwordField)
				.userModel(getModel())
		);
		
		return body;
	}

	@Override
	protected void doOnSuccess(IModel<TechnicalUser> userModel) {
		throw AdministrationTechnicalUserDetailPage.MAPPER
			.ignoreParameter2()
			.map(userModel)
			.newRestartResponseException();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(userTypeDescriptorModel);
	}

}
