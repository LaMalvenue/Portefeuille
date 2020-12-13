package fr.portefeuille.web.application.common.renderer;

import java.util.Locale;

import org.iglooproject.wicket.more.rendering.Renderer;

import fr.portefeuille.core.business.compte.model.Compte;

public abstract class CompteRenderer extends Renderer<Compte>{

	private static final long serialVersionUID = 5193411500257507158L;

	private static final Renderer<Compte> INSTANCE = new CompteRenderer() {
		private static final long serialVersionUID = 1L;
		@Override
		public String render(Compte value, Locale locale) {
			return value.getLabel();
		}
	}.nullsAsNull();

	public static Renderer<Compte> get() {
		return INSTANCE;
	}

	private CompteRenderer() {
	}

}
