package fr.portefeuille.web.application.common.template.theme.advanced;

import org.apache.wicket.markup.html.panel.Panel;
import fr.portefeuille.web.application.PortefeuilleApplication;
import org.iglooproject.wicket.more.condition.Condition;

public class SidebarHeaderPanel extends Panel {

	private static final long serialVersionUID = -926306336711136387L;

	public SidebarHeaderPanel(String id) {
		super(id);
		
		add(Condition.anyChildVisible(this).thenShow());
		
		add(
			PortefeuilleApplication.get().getHomePageLinkDescriptor()
				.link("home")
		);
	}

}
