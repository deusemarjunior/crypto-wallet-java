package com.cryptowallet.infrastructure.adapter;



import com.cryptowallet.CryptoWalletApplication;
import com.cryptowallet.infrastructure.adapter.CoincapRetriveId;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCSV;
import com.cryptowallet.infrastructure.config.HttpRequestHandle;
import com.cryptowallet.domain.entity.CryptoAsset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CryptoAssetLoadCSVTest {

    private HttpRequestHandle httpRequestHandle = new HttpRequestHandle();
    @Test
    @DisplayName("Read CSV invalid symbol")
    public void testReadWalletFromCSVInvalidSymbol() throws Exception {
        // Set up test data
        String pathCSV = CryptoWalletApplication.class.getResource("/test_wallet_invalid_asset.csv").getPath();
        CoincapRetriveId coincapRetriveId = new CoincapRetriveId();

        CryptoAssetLoadCSV cryptoAssetLoadCSV = new CryptoAssetLoadCSV(pathCSV);

        // Test readWalletFromCSV method
        Assertions.assertThrows(Exception.class, () -> {
            cryptoAssetLoadCSV.readWalletFromCSV();
        });

    }
    @Test
    @DisplayName("Read CSV transform to List CryptoAsset with load id ")
    public void testReadWalletFromCSV() throws Exception {

        String pathCSV = CryptoWalletApplication.class.getResource("/test_wallet_loan.csv").getPath();
        CoincapRetriveId coincapRetriveId = new CoincapRetriveId();

        CryptoAssetLoadCSV cryptoAssetLoadCSV = new CryptoAssetLoadCSV(pathCSV);

        List<CryptoAsset> assets = cryptoAssetLoadCSV.readWalletFromCSV();

        assertEquals(2, assets.size());

        CryptoAsset bitcoin = assets.get(0);
        assertEquals("BTC", bitcoin.getSymbol());
        assertEquals(new BigDecimal("0.12345"), bitcoin.getQuantity());
        assertEquals(new BigDecimal("37870.5058"), bitcoin.getPrice());
        
        assertEquals("bitcoin",bitcoin.getId());

        CryptoAsset ethereum = assets.get(1);
        assertEquals("ETH", ethereum.getSymbol());
        assertEquals(new BigDecimal("4.89532"), ethereum.getQuantity());
        assertEquals(new BigDecimal("2004.9774"), ethereum.getPrice());

        assertEquals("ethereum",ethereum.getId());

    }

}