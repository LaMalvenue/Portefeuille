package fr.portefeuille.web.application.common.form.impl;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import fr.portefeuille.core.business.user.model.UserGroup;
import fr.portefeuille.web.application.common.renderer.UserGroupRenderer;
import org.iglooproject.wicket.more.markup.html.form.AbstractGenericEntityChoiceRenderer;
import org.iglooproject.wicket.more.markup.html.form.GenericEntityRendererToChoiceRenderer;

public abstract class UserGroupChoiceRenderer extends ChoiceRenderer<UserGroup> {

	private static final long serialVersionUID = 8473460282352945231L;

	private static final AbstractGenericEntityChoiceRenderer<UserGroup> INSTANCE = GenericEntityRendererToChoiceRenderer.of(UserGroupRenderer.get());

	public static AbstractGenericEntityChoiceRenderer<UserGroup> get() {
		return INSTANCE;
	}

	private UserGroupChoiceRenderer() {
	}

}