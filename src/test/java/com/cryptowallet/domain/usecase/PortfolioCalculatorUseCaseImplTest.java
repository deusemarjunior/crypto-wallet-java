package com.cryptowallet.domain.usecase;

import com.cryptowallet.domain.entity.CryptoAsset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PortfolioCalculatorUseCaseImplTest {

    @Test
    public void testEmptyPortfolio() throws Exception {
        List<CryptoAsset> portfolio = new ArrayList<>();
        var calculator = new PortfolioCalculatorUseCaseImpl();

        assertThrows(Exception.class, () -> {
            calculator.execute(portfolio);
        });
    }

    @Test
    public void testSingleAsset() throws Exception {
        List<CryptoAsset> portfolio = new ArrayList<>();
        CryptoAsset asset = new CryptoAsset("bitcoin", "BTC", BigDecimal.valueOf(0.12345), BigDecimal.valueOf(37870.5058), BigDecimal.valueOf(56999.9728252053067291));
        portfolio.add(asset);
        var calculator = new PortfolioCalculatorUseCaseImpl();
        String result = calculator.execute(portfolio);
        assertEquals("total=7036.65,best_asset=BTC,best_performance=1.51,worst_asset=BTC,worst_performance=1.51", result);
    }

    @Test
    public void testMultipleAssets() throws Exception {
        List<CryptoAsset> portfolio = new ArrayList<>();
        CryptoAsset asset1 = new CryptoAsset("bitcoin", "BTC", BigDecimal.valueOf(0.12345), BigDecimal.valueOf(37870.5058), BigDecimal.valueOf(56999.9728252053067291));
        CryptoAsset asset2 = new CryptoAsset("ethereum", "ETH", BigDecimal.valueOf(4.89532), BigDecimal.valueOf(2004.9774), BigDecimal.valueOf(2032.1394325557042107));
        portfolio.add(asset1);
        portfolio.add(asset2);
        var calculator = new PortfolioCalculatorUseCaseImpl();
        String result = calculator.execute(portfolio);
        assertEquals("total=16984.62,best_asset=BTC,best_performance=1.51,worst_asset=ETH,worst_performance=1.01", result);
    }
}