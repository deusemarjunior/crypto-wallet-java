package com.cryptowallet.domain.usecase;

import com.cryptowallet.domain.entity.CryptoAsset;

import java.util.List;

public interface PortfolioCalculatorUseCase {
    String execute(List<CryptoAsset> cryptoAssets) throws Exception;
}
