package fr.portefeuille.web.application.security.login.component;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.security.service.ISecurityManagementService;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordRecoveryPage;

public class SignInFooterPanel extends Panel {

	private static final long serialVersionUID = -7042210777928535702L;
	
	@SpringBean
	private ISecurityManagementService securityManagementService;
	
	public SignInFooterPanel(String wicketId) {
		super(wicketId);
		
		add(
			SecurityPasswordRecoveryPage.linkDescriptor()
				.link("passwordRecovery")
				.setVisibilityAllowed(securityManagementService.getOptions(User.class).isPasswordUserRecoveryEnabled())
		);
	}

}
