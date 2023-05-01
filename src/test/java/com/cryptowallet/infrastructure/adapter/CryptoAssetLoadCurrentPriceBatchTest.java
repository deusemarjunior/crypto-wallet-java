package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.adapter.CoincapRetriveCurrentPrice;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCurrentPriceBatch;
import com.cryptowallet.domain.entity.CryptoAsset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CryptoAssetLoadCurrentPriceBatchTest {
    @Mock
    private CoincapRetriveCurrentPrice coincapRetriveCurrentPrice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnrichCryptoAsset() throws Exception {
        // GIVEN
        CryptoAsset bitcoin = new CryptoAsset("bitcoin", "BTC", new BigDecimal("1.0"), new BigDecimal("0.0"));
        CryptoAsset ethereum = new CryptoAsset("ethereum", "ETH", new BigDecimal("1.0"), new BigDecimal("0.0"));
        List<CryptoAsset> cryptoAssets = new ArrayList<>();
        cryptoAssets.add(bitcoin);
        cryptoAssets.add(ethereum);

        // WHEN
        when(coincapRetriveCurrentPrice.getCurrentCryptoPrice(bitcoin.getId()))
                .thenReturn(new BigDecimal("99999.0"));
        when(coincapRetriveCurrentPrice.getCurrentCryptoPrice(ethereum.getId()))
                .thenReturn(new BigDecimal("88888.0"));

        // THEN
        CryptoAssetLoadCurrentPriceBatch cryptoAssetBatch = new CryptoAssetLoadCurrentPriceBatch(coincapRetriveCurrentPrice);
        cryptoAssetBatch.enrichCryptoAssetCurrentPrice(cryptoAssets);

        // verify that the CryptoAsset objects have been updated with current price
        verify(coincapRetriveCurrentPrice, times(2)).getCurrentCryptoPrice(any());

        assertEquals(bitcoin.getCurrentPrice(), new BigDecimal("99999.0"));
        assertEquals(ethereum.getCurrentPrice(), new BigDecimal("88888.0"));
    }

}