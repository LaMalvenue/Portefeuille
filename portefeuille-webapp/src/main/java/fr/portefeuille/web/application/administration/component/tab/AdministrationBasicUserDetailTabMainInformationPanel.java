package fr.portefeuille.web.application.administration.component.tab;

import org.apache.wicket.model.IModel;
import fr.portefeuille.core.business.user.model.BasicUser;
import fr.portefeuille.web.application.administration.component.BasicUserDetailDescriptionPanel;
import fr.portefeuille.web.application.administration.component.BasicUserDetailGroupsPanel;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;

public class AdministrationBasicUserDetailTabMainInformationPanel extends GenericPanel<BasicUser> {

	private static final long serialVersionUID = -3900528127687137340L;

	public AdministrationBasicUserDetailTabMainInformationPanel(String id, final IModel<? extends BasicUser> userModel) {
		super(id, userModel);
		
		add(
			new BasicUserDetailDescriptionPanel("description", userModel),
			new BasicUserDetailGroupsPanel("groups", userModel)
		);
	}

}
