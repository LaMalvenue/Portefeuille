package fr.portefeuille.web.application.notification.component;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import fr.portefeuille.web.application.PortefeuilleApplication;

public abstract class AbstractHtmlNotificationPanel<T> extends GenericPanel<T> {

	private static final long serialVersionUID = -3576134833190785445L;

	public AbstractHtmlNotificationPanel(String id) {
		this(id, null);
	}

	public AbstractHtmlNotificationPanel(String id, IModel<T> model) {
		super(id, model);
		
		add(
			PortefeuilleApplication.get()
				.getHomePageLinkDescriptor()
				.link("homePageLink")
				.setAbsolute(true)
		);
	}

}
