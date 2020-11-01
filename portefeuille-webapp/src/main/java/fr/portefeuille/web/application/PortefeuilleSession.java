package fr.portefeuille.web.application;

import static fr.portefeuille.core.property.PortefeuilleCorePropertyIds.ENVIRONMENT;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Request;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.config.util.Environment;
import org.iglooproject.wicket.more.AbstractCoreSession;
import org.iglooproject.wicket.more.model.ApplicationPropertyModel;

public class PortefeuilleSession extends AbstractCoreSession<User> {

	private static final long serialVersionUID = 1870827020904365541L;

	private final IModel<Environment> environmentModel = ApplicationPropertyModel.of(ENVIRONMENT);

	public PortefeuilleSession(Request request) {
		super(request);
	}

	public static PortefeuilleSession get() {
		return (PortefeuilleSession) Session.get();
	}

	public IModel<Environment> getEnvironmentModel() {
		return environmentModel;
	}

}