package fr.portefeuille.web.application.administration.component.tab;

import org.apache.wicket.model.IModel;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.web.application.administration.component.TechnicalUserDetailDescriptionPanel;
import fr.portefeuille.web.application.administration.component.TechnicalUserDetailGroupsPanel;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;

public class AdministrationTechnicalUserDetailTabMainInformationPanel extends GenericPanel<TechnicalUser> {

	private static final long serialVersionUID = -6822072984820432812L;

	public AdministrationTechnicalUserDetailTabMainInformationPanel(String id, final IModel<? extends TechnicalUser> userModel) {
		super(id, userModel);
		
		add(
			new TechnicalUserDetailDescriptionPanel("description", userModel),
			new TechnicalUserDetailGroupsPanel("groups", userModel)
		);
	}

}
