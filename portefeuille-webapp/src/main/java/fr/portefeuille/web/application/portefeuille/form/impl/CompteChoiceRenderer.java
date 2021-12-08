package fr.portefeuille.web.application.portefeuille.form.impl;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.iglooproject.wicket.more.markup.html.form.AbstractGenericEntityChoiceRenderer;
import org.iglooproject.wicket.more.markup.html.form.GenericEntityRendererToChoiceRenderer;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.web.application.common.renderer.CompteRenderer;

public class CompteChoiceRenderer extends ChoiceRenderer<Compte> {

	private static final long serialVersionUID = 2221718952260277209L;

	private static final AbstractGenericEntityChoiceRenderer<Compte> INSTANCE = GenericEntityRendererToChoiceRenderer.of(CompteRenderer.get());

	public static AbstractGenericEntityChoiceRenderer<Compte> get() {
		return INSTANCE;
	}

	private CompteChoiceRenderer() {
	}
}
