package fr.portefeuille.web.application.common.renderer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.iglooproject.wicket.more.util.convert.converters.CascadingConverter;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import fr.portefeuille.core.business.common.model.Montant;

public final class MontantBigDecimalConverter extends AbstractSIBigDecimalConverter {
	
	private static final long serialVersionUID = -380691980217406087L;
	
	private static final IConverter<BigDecimal> INSTANCE =
		new CascadingConverter<BigDecimal>(
			new MontantBigDecimalConverter(SYMBOLS_DEFAULT),
			ImmutableList.<IConverter<BigDecimal>>of(),
			ImmutableList.<IConverter<BigDecimal>>of(new MontantBigDecimalConverter(SYMBOLS_FRANGLAIS))
		) {
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				return INSTANCE;
			}
		};
	
	public static IConverter<BigDecimal> get() {
		return INSTANCE;
	}
	
	/**
	 * Minimum number of digits for the integral part, when rendering a BigDecimal.
	 */
	private static final int MIN_INTEGER_DIGITS = 1;
	
	/**
	 * Minimum number of digits for the fractional part, when rendering a BigDecimal.
	 */
	private static final int MIN_FRACTION_DIGITS = 2;

	private MontantBigDecimalConverter(Function<Locale, DecimalFormatSymbols> decimalFormatSymbolsFactory) {
		super(decimalFormatSymbolsFactory);
	}
	
	@Override
	protected int getPrecision() {
		return Montant.PRECISION;
	}
	
	@Override
	protected int getScale() {
		return Montant.SCALE;
	}

	@Override
	protected DecimalFormat createNumberFormat(Locale locale, DecimalFormatSymbols symbols) {
		DecimalFormat numberFormat = new DecimalFormat("#,##0.###;-#,##0.###", symbols);
		
		// For formatting only; no effect when parsing
		numberFormat.setMinimumIntegerDigits(MIN_INTEGER_DIGITS);
		numberFormat.setMinimumFractionDigits(MIN_FRACTION_DIGITS);
		
		numberFormat.setRoundingMode(RoundingMode.DOWN);
		
		return numberFormat;
	}
}
