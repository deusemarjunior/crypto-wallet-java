package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.adapter.CoincapRetriveId;
import com.cryptowallet.infrastructure.config.HttpRequestHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoincapRetriveIdTest {

    private HttpRequestHandle httpRequestHandle = new HttpRequestHandle();
    @Test
    public void testGetCryptoAssetIdBySymbol() throws Exception {

        CoincapRetriveId coincapRetriveId = new CoincapRetriveId();
        String symbol = "BTC";
        String expectedId = "bitcoin";
        String actualId = coincapRetriveId.getCryptoAssetIdBySymbol(symbol);
        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetCryptoAssetIdBySymbolNotFound() throws Exception {
        CoincapRetriveId coincapRetriveId = new CoincapRetriveId();
        String symbol = "INVALID";
        Assertions.assertThrows(Exception.class, () -> {
            coincapRetriveId.getCryptoAssetIdBySymbol(symbol);
        });
    }
}