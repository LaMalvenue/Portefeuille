package fr.portefeuille.web.application.common.template.theme.common;

import org.apache.wicket.model.Model;
import fr.portefeuille.core.config.util.Environment;
import fr.portefeuille.web.application.PortefeuilleSession;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;

public class BootstrapBreakpointPanel extends EnclosureContainer {

	private static final long serialVersionUID = 5271828582493462504L;

	public BootstrapBreakpointPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		
		condition(Condition.isEqual(PortefeuilleSession.get().getEnvironmentModel(), Model.of(Environment.development)));
	}

}
