package br.com.user.security.utils;

import br.com.user.security.util.CurrencyUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CurrencyUtilsTest {

    @Test
    public void testSetScaleWithDefault() {
        assertThat(CurrencyUtils.setScale(null, new BigDecimal("1234.1233")), is(new BigDecimal("1234.12")));
    }

    @Test
    public void testSetScale() {
        assertThat(CurrencyUtils.setScale(new BigDecimal("1234.1233")), is(new BigDecimal("1234.12")));
    }
}
