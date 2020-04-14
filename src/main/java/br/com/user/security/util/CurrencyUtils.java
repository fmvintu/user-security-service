package br.com.user.security.util;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyUtils {

    public static final int CURRENCY_PRECISION = 2;

    private CurrencyUtils() {
    }

    public static BigDecimal setScale(BigDecimal amount) {
        return setScale(amount, null);
    }

    public static BigDecimal setScale(BigDecimal amount, BigDecimal defaultAmount) {
        return ((isNull(amount))
                ? defaultAmount.setScale(CURRENCY_PRECISION, RoundingMode.HALF_UP)
                : amount.setScale(CURRENCY_PRECISION, RoundingMode.HALF_UP));
    }
}
