package fr.portefeuille.web.application.common.template.theme.advanced;

import org.apache.wicket.markup.html.panel.Panel;
import fr.portefeuille.web.application.PortefeuilleApplication;

public class SidebarNavbarPanel extends Panel {

	private static final long serialVersionUID = -3741272240940846720L;

	public SidebarNavbarPanel(String id) {
		super(id);
		
		add(
			PortefeuilleApplication.get().getHomePageLinkDescriptor()
				.link("home")
		);
	}

}
