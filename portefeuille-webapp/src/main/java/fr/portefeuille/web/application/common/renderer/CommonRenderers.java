package fr.portefeuille.web.application.common.renderer;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.iglooproject.functional.Joiners;
import org.iglooproject.wicket.more.rendering.Renderer;

public final class CommonRenderers {

	private static final String EUROS_KEY = "common.unit.euros";

	private static final Renderer<BigDecimal> SOMME_EUROS = withUnit(
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

	public static Renderer<BigDecimal> sommeEuros() {
		return SOMME_EUROS;
	}
}