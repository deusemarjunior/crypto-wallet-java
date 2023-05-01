package com.cryptowallet.domain.usecase;

import com.cryptowallet.domain.entity.CryptoAsset;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCSV;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCurrentPriceBatch;
import com.cryptowallet.infrastructure.config.ParameterSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PortfolioServiceUseCaseImplTest {

    @Mock
    private CryptoAssetLoadCSV cryptoAssetLoadCSV;

    @Mock
    private CryptoAssetLoadCurrentPriceBatch cryptoAssetLoadCurrentPriceBatch;

    @Mock
    private PortfolioCalculatorUseCaseImpl portfolioCalculator;

    private PortfolioServiceUseCaseImpl portfolioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        portfolioService = new PortfolioServiceUseCaseImpl(cryptoAssetLoadCSV, cryptoAssetLoadCurrentPriceBatch,
                portfolioCalculator);
    }

    @Test
    public void testGetPortfolioValue() throws Exception {
        // GIVE
        List<CryptoAsset> cryptoAssets = new ArrayList<>();
        CryptoAsset cryptoAsset1 = new CryptoAsset("","BTC", BigDecimal.valueOf(0.01), BigDecimal.valueOf(50000));
        CryptoAsset cryptoAsset2 = new CryptoAsset("", "ETH", BigDecimal.valueOf(2), BigDecimal.valueOf(3000));
        cryptoAssets.add(cryptoAsset1);
        cryptoAssets.add(cryptoAsset2);

        when(cryptoAssetLoadCSV.readWalletFromCSV()).thenReturn(cryptoAssets);

        BigDecimal btcCurrentPrice = BigDecimal.valueOf(55000);
        BigDecimal ethCurrentPrice = BigDecimal.valueOf(3500);

        CryptoAsset btc = new CryptoAsset("","BTC", BigDecimal.valueOf(0.01), BigDecimal.valueOf(50000));
        btc.setCurrentPrice(btcCurrentPrice);
        CryptoAsset eth = new CryptoAsset("","ETH", BigDecimal.valueOf(2), BigDecimal.valueOf(3000));
        eth.setCurrentPrice(ethCurrentPrice);

        String expected = String.format("total=%.2f,best_asset=%s,best_performance=%.2f,worst_asset=%s,worst_performance=%.2f",
                BigDecimal.valueOf(71000).setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE),
                "BTC", BigDecimal.valueOf(10).setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE),
                "ETH", BigDecimal.valueOf(16.67).setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE));

        //When
        when(portfolioCalculator.execute(cryptoAssets)).thenReturn(expected);

        //Than
        String result = portfolioService.getPortfolioValue();

        assertEquals(expected, result);
    }
}