package fr.portefeuille.core.business.common.model;

import java.math.BigDecimal;

import org.bindgen.Bindable;

@Bindable
public final class Montant {
	
	private Montant() {
	}

	public static final int PRECISION = 11;

	public static final int SCALE = 2;

	// Peut être utilisé lorsqu'on multiplie des montants pour effectuer ensuite une division
	// (c'est la division qui s'occupera de l'arrondi pour limiter les erreurs)
	public static final int MULTIPLY_SCALE = 4;

	public static final BigDecimal ZERO = BigDecimal.valueOf(0L, SCALE);

	public static boolean strictementPositif(BigDecimal montant) {
		return montant != null && montant.compareTo(Montant.ZERO) > 0;
	}
	
	public static boolean positif(BigDecimal montant) {
		return montant != null && montant.compareTo(Montant.ZERO) >= 0;
	}

	public static boolean nonNullDifferentZero(BigDecimal montant) {
		return montant != null && montant.compareTo(Montant.ZERO) != 0;
	}

	public static boolean nonNullZero(BigDecimal montant) {
		return montant != null && montant.compareTo(Montant.ZERO) == 0;
	}
}
