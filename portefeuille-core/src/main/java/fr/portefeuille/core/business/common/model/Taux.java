package fr.portefeuille.core.business.common.model;

import java.math.BigDecimal;

import org.bindgen.Bindable;

@Bindable
public final class Taux {

	private Taux() {
	}

	// Remarque: pas 9 par hasard, valeux max des taux de la base FAFCEA (quelques decimal 7/2 et un 9/2)
	public static final int PRECISION = 9;

	public static final int SCALE = 2;

	public static final BigDecimal ZERO = BigDecimal.valueOf(0L, SCALE);
}
