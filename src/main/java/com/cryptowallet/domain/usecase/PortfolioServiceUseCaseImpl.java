package com.cryptowallet.domain.usecase;

import com.cryptowallet.domain.entity.CryptoAsset;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCSV;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCurrentPriceBatch;

import java.util.List;

public class PortfolioServiceUseCaseImpl implements PortfolioServiceUseCase {
    private final CryptoAssetLoadCSV cryptoAssetLoadCSV;

    private final CryptoAssetLoadCurrentPriceBatch cryptoAssetLoadCurrentPriceBatch;
    private final PortfolioCalculatorUseCaseImpl portfolioCalculator;

    public PortfolioServiceUseCaseImpl(CryptoAssetLoadCSV cryptoAssetLoadCSV,
                                       CryptoAssetLoadCurrentPriceBatch cryptoAssetLoadCurrentPriceBatch,
                                       PortfolioCalculatorUseCaseImpl portfolioCalculator) {
        this.cryptoAssetLoadCSV = cryptoAssetLoadCSV;
        this.cryptoAssetLoadCurrentPriceBatch = cryptoAssetLoadCurrentPriceBatch;
        this.portfolioCalculator = portfolioCalculator;
    }

    @Override
    public String getPortfolioValue() throws Exception {

        // Read CSV and set id CryptoAsset from coincap
        List<CryptoAsset> cryptoAssets = cryptoAssetLoadCSV.readWalletFromCSV();

        cryptoAssetLoadCurrentPriceBatch.enrichCryptoAssetCurrentPrice(cryptoAssets);

        // Calculate portfolio value
        return portfolioCalculator.execute(cryptoAssets);
    }
}