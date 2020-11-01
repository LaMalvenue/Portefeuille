package fr.portefeuille.web.application.common.form.impl;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.web.application.common.renderer.UserRenderer;
import org.iglooproject.wicket.more.markup.html.form.AbstractGenericEntityChoiceRenderer;
import org.iglooproject.wicket.more.markup.html.form.GenericEntityRendererToChoiceRenderer;

public abstract class UserChoiceRenderer extends ChoiceRenderer<User> {

	private static final long serialVersionUID = -4657800061123148721L;

	private static final AbstractGenericEntityChoiceRenderer<User> INSTANCE = GenericEntityRendererToChoiceRenderer.of(UserRenderer.get());

	public static AbstractGenericEntityChoiceRenderer<User> get() {
		return INSTANCE;
	}

	private UserChoiceRenderer() {
	}

}