package fr.portefeuille.web.application.security.password.page;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.web.application.PortefeuilleApplication;
import fr.portefeuille.web.application.PortefeuilleSession;
import fr.portefeuille.web.application.security.password.component.SecurityPasswordCreationContentPanel;
import fr.portefeuille.web.application.security.password.template.SecurityPasswordTemplate;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.link.descriptor.mapper.ITwoParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.link.descriptor.parameter.CommonParameters;
import org.iglooproject.wicket.more.markup.html.template.model.BreadCrumbElement;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;

public class SecurityPasswordCreationPage extends SecurityPasswordTemplate {

	private static final long serialVersionUID = 3082299089180143270L;

	public static final ITwoParameterLinkDescriptorMapper<IPageLinkDescriptor, User, String> MAPPER = 
		LinkDescriptorBuilder.start()
			.model(User.class)
			.model(String.class)
			.pickFirst().map(CommonParameters.ID).mandatory()
			.pickSecond().map(CommonParameters.TOKEN).mandatory()
			.page(SecurityPasswordCreationPage.class);

	private final IModel<User> userModel = new GenericEntityModel<>();

	public SecurityPasswordCreationPage(PageParameters parameters) {
		super(parameters);
		
		// Being connected here doesn't make any sense
		if (PortefeuilleSession.get().isSignedIn()) {
			PortefeuilleSession.get().invalidate();
		}
		
		addHeadPageTitlePrependedElement(new BreadCrumbElement(
			new ResourceModel("security.password.creation.pageTitle")
		));
		
		final IModel<String> tokenModel = Model.of("");
		
		MAPPER.map(userModel, tokenModel).extractSafely(
			parameters, 
			PortefeuilleApplication.get().getHomePageLinkDescriptor(),
			getString("common.error.unexpected")
		);
		
		if (!tokenModel.getObject().equals(userModel.getObject().getPasswordRecoveryRequest().getToken())) {
			PortefeuilleSession.get().error(getString("security.password.creation.wrongToken"));
			throw PortefeuilleApplication.get().getHomePageLinkDescriptor().newRestartResponseException();
		}
	}

	@Override
	protected IModel<String> getTitleModel() {
		return new ResourceModel("security.password.creation.pageTitle");
	}

	@Override
	protected Component getContentComponent(String wicketId) {
		return new SecurityPasswordCreationContentPanel(wicketId, userModel);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(userModel);
	}

}
