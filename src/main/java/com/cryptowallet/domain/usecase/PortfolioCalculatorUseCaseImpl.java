package com.cryptowallet.domain.usecase;

import com.cryptowallet.infrastructure.config.ParameterSettings;
import com.cryptowallet.domain.entity.CryptoAsset;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioCalculatorUseCaseImpl {

    public String execute(List<CryptoAsset> cryptoAssets) throws Exception{
        BigDecimal totalValue = BigDecimal.ZERO;
        CryptoAsset bestPerformingAsset = null;
        CryptoAsset worstPerformingAsset = null;
        BigDecimal bestPerformance = BigDecimal.ZERO;
        BigDecimal worstPerformance = BigDecimal.ZERO;
        for (CryptoAsset asset : cryptoAssets) {

            BigDecimal currentValue = asset.getQuantity().multiply(asset.getCurrentPrice());
            totalValue = totalValue.add(currentValue);
           BigDecimal percentageChange = asset.getCurrentPrice().multiply(BigDecimal.valueOf(100))
                   .divide(asset.getPrice(), 0)
                   .divide(BigDecimal.valueOf(100));

            if (bestPerformingAsset == null || percentageChange.compareTo(bestPerformance) > 0) {
                bestPerformingAsset = asset;
                bestPerformance = percentageChange;
            }
            if (worstPerformingAsset == null || percentageChange.compareTo(worstPerformance) < 0) {
                worstPerformingAsset = asset;
                worstPerformance = percentageChange;
            }
        }
        return  String.format("total=%.2f,best_asset=%s,best_performance=%.2f,worst_asset=%s,worst_performance=%.2f",
                totalValue.setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE),
                bestPerformingAsset.getSymbol(),
                bestPerformance.setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE),
                worstPerformingAsset.getSymbol(),
                worstPerformance.setScale(ParameterSettings.SCALE, ParameterSettings.ROUNDING_MODE));
    }
}
