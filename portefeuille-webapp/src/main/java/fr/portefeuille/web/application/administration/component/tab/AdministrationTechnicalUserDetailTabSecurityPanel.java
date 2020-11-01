package fr.portefeuille.web.application.administration.component.tab;

import org.apache.wicket.model.IModel;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.web.application.administration.component.UserDetailHistoryLogPanel;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;

public class AdministrationTechnicalUserDetailTabSecurityPanel extends GenericPanel<TechnicalUser> {

	private static final long serialVersionUID = -341225850371484771L;

	public AdministrationTechnicalUserDetailTabSecurityPanel(String id, final IModel<? extends TechnicalUser> userModel) {
		super(id, userModel);
		
		add(
			new UserDetailHistoryLogPanel("audits", userModel)
		);
	}

}
