package fr.portefeuille.web.application.common.renderer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;
import org.iglooproject.functional.Function2;
import org.iglooproject.functional.SerializableFunction2;

import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;

public abstract class AbstractSIBigDecimalConverter extends BigDecimalConverter {
	
	private static final long serialVersionUID = -6013689630320750041L;

	protected static final Function2<Locale, DecimalFormatSymbols> SYMBOLS_DEFAULT = new SerializableFunction2<Locale, DecimalFormatSymbols>() {
		private static final long serialVersionUID = 1L;
		@Override
		public DecimalFormatSymbols apply(Locale input) {
			return DecimalFormatSymbols.getInstance(input);
		}
		private Object readResolve() {
			return SYMBOLS_DEFAULT;
		}
	};
	
	protected static final Function2<Locale, DecimalFormatSymbols> SYMBOLS_FRANGLAIS = new SerializableFunction2<Locale, DecimalFormatSymbols>() {
		private static final long serialVersionUID = 1L;
		@Override
		public DecimalFormatSymbols apply(@Nonnull Locale input) {
			DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(input);
			symbols.setDecimalSeparator('.');
			return symbols;
		}
		private Object readResolve() {
			return SYMBOLS_FRANGLAIS;
		}
	};
	
	private final Function<Locale, DecimalFormatSymbols> decimalFormatSymbolsFactory;
	
	public AbstractSIBigDecimalConverter(Function<Locale, DecimalFormatSymbols> decimalFormatSymbolsFactory) {
		super();
		this.decimalFormatSymbolsFactory = decimalFormatSymbolsFactory;
	}
	
	@Override
	protected ConversionException newConversionException(String message, Object value, Locale locale) {
		int apparentScale = getScale() - getDisplayScaleModifier();
		return super.newConversionException(message, value, locale)
				.setVariable("maximumIntegerDigits", getPrecision() - apparentScale)
				.setVariable("maximumFractionDigits", Ints.max(apparentScale, 0));
	}

	/**
	 * Maximum number of digits for the integral part, when <strong>parsing</strong> a BigDecimal.
	 * <p>If a number is parsed that has more integer digits (which means that using this maximum number of digits
	 * would result in a loss of information), an error is reported.
	 */
	protected abstract int getScale();

	/**
	 * Maximum number of digits for the fractional part, when <strong>parsing</strong> a BigDecimal.
	 * <p>If a number is parsed that has more fraction digits (which means that using this maximum number of digits
	 * would result in a loss of information), an error is reported.
	 */
	protected abstract int getPrecision();

	/**
	 * @return A positive integer giving the power of ten by which the value is to be multiplied before display.
	 */
	protected int getDisplayScaleModifier() {
		return 0;
	}
	
	protected abstract DecimalFormat createNumberFormat(Locale locale, DecimalFormatSymbols symbols);

	@Override
	public BigDecimal convertToObject(String value, Locale locale) {
		BigDecimal bigDecimal = super.convertToObject(value, locale);
		if (bigDecimal != null) {
			try {
				bigDecimal = bigDecimal.setScale(getScale());
			} catch (ArithmeticException e) { // NOSONAR: e is not propagated
				throw newConversionException("Value " + value + " has too many fraction digits", value, locale);
			}
			if (bigDecimal.precision() > getPrecision()) {
				throw newConversionException("Value " + value + " has too many integer digits.", value, locale);
			}
		}
		return bigDecimal;
	}

	@Override
	protected final NumberFormat newNumberFormat(Locale locale) {
		DecimalFormatSymbols symbols = decimalFormatSymbolsFactory.apply(locale);
		
		DecimalFormat format = createNumberFormat(locale, symbols);
		
		format.setRoundingMode(RoundingMode.DOWN);
		
		int scaleModifier = getDisplayScaleModifier();
		if (scaleModifier > 0) {
			format.setMultiplier(IntMath.pow(10, scaleModifier));
		}
		
		return format;
	}

}
