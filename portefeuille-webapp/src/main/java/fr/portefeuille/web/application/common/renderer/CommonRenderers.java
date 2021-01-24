package fr.portefeuille.web.application.common.renderer;

import java.text.DecimalFormat;

import org.iglooproject.functional.Joiners;
import org.iglooproject.wicket.more.rendering.Renderer;

public final class CommonRenderers {

	private static final String EUROS_KEY = "common.unit.euros";

	// TODO AROUV : Comment ajouter un espace tous les 3 chiffres ?
	private static final Renderer<Double> SOMME_EUROS = withUnit(
			Renderer
				.fromNumberFormat(new DecimalFormat("#.##")),
			EUROS_KEY
		);

	private CommonRenderers() {
	}

	private static <T> Renderer<T> withUnit(Renderer<T> renderer, final String unitKey) {
		return renderer.nullsAsNull()
			.append(Joiners.Functions.onNonBreakingSpace(), Renderer.fromResourceKey(unitKey))
			.nullsAsNull();
	}

	public static Renderer<Double> sommeEuros() {
		return SOMME_EUROS;
	}
}