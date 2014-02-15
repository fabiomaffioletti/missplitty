package com.missplitty;

import junit.framework.Assert;

import org.junit.Test;

import com.missplitty.domain.Currency;
import com.missplitty.utils.Utils;

public class UtilsTest extends CommonTest {
	
	@Test
	public void testConvert() {
		Currency eur = new Currency(1, "EUR");
		eur.setRatio(1F);
		Currency usd = new Currency(2, "USD");
		usd.setRatio(1.5F);
		Currency gbp = new Currency(3, "GBP");
		gbp.setRatio(0.5F);
		
		Assert.assertEquals(900F, Utils.convertFloatToCurrency(300F, gbp, usd));
		
//		Assert.assertEquals(10F, Utils.convertFloatToCurrency(10F, eur, usd));
//		
//		usd.setRatio(1.5F);
//		Assert.assertEquals(15F, round(Utils.convertFloatToCurrency(10F, eur, usd)));
//		Assert.assertEquals(6.67F, round(Utils.convertFloatToCurrency(10F, usd, eur)));
		
	}

}
