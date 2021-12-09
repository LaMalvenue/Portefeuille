package fr.portefeuille.web.application.common.renderer;

import java.math.BigDecimal;
import java.util.Locale;

import org.iglooproject.functional.Joiners;
import org.iglooproject.wicket.more.rendering.Renderer;

public final class CommonRenderers {

	private static final String HEURES_KEY = "common.unit.heures";

	private static final String MOIS_KEY = "common.unit.mois";

	private static final String ANS_KEY = "common.unit.ans";

	private static final String EUROS_KEY = "common.unit.euro";

	private static final String EUROS_PAR_HEURE_KEY = "common.unit.euroParHeure";

	private static final Renderer<Integer> HEURES = withUnit(Renderer.<Integer>stringValue(), HEURES_KEY);

	private static final Renderer<Integer> MOIS = withUnit(Renderer.<Integer>stringValue(), MOIS_KEY);

	private static final Renderer<Integer> ANS = withUnit(Renderer.<Integer>stringValue(), ANS_KEY);

	private static final Renderer<BigDecimal> BIG_DECIMAL = Renderer.from(MontantBigDecimalConverter.get());

	private static final Renderer<BigDecimal> MONTANT = withUnit(BIG_DECIMAL, EUROS_KEY);
	
	private static final Renderer<BigDecimal> MONTANT_SANS_UNITE = withoutUnit(BIG_DECIMAL);

	private static final Renderer<BigDecimal> MONTANT_PAR_HEURE = withUnit(BIG_DECIMAL, EUROS_PAR_HEURE_KEY);

	private CommonRenderers() {
	}

	private static <T> Renderer<T> withUnit(Renderer<T> renderer, final String unitKey) {
		return renderer.nullsAsNull()
			.append(Joiners.Functions.onNonBreakingSpace(), Renderer.fromResourceKey(unitKey))
			.nullsAsNull();
	}

	private static <T> Renderer<T> withoutUnit(Renderer<T> renderer) {
		return renderer.nullsAsNull();
	}

	public static Renderer<BigDecimal> montant() {
		return MONTANT;
	}
	
	public static Renderer<BigDecimal> montantSansUnite() {
		return MONTANT_SANS_UNITE;
	}

	public static Renderer<BigDecimal> montantHoraire() {
		return MONTANT_PAR_HEURE;
	}

	public static Renderer<Integer> heures() {
		return HEURES;
	}

	public static Renderer<Integer> mois() {
		return MOIS;
	}

	public static Renderer<Integer> ans() {
		return ANS;
	}

	public static Renderer<Boolean> yesNo() {
		return new Renderer<Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String render(Boolean value, Locale locale) {
				return Boolean.TRUE.equals(value) ? getString("common.yes", locale) : getString("common.no", locale);
			}
		};
	}
}