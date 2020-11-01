package fr.portefeuille.web.application.console.common.component;

import fr.portefeuille.web.application.common.component.EnvironmentPanel;

public class ConsoleEnvironmentPanel extends EnvironmentPanel {

	private static final long serialVersionUID = -1099199206441256170L;

	public ConsoleEnvironmentPanel(String id) {
		super(id);
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		setVisible(true);
	}

}