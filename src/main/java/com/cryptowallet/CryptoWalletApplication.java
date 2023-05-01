package com.cryptowallet;

import com.cryptowallet.infrastructure.adapter.CoincapRetriveCurrentPrice;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCSV;
import com.cryptowallet.infrastructure.adapter.CryptoAssetLoadCurrentPriceBatch;
import com.cryptowallet.domain.usecase.PortfolioCalculatorUseCaseImpl;
import com.cryptowallet.domain.usecase.PortfolioServiceUseCase;
import com.cryptowallet.domain.usecase.PortfolioServiceUseCaseImpl;

import java.io.File;

public class CryptoWalletApplication {

    public static void main(String[] args){
        String filePath;

        // Check if a file path was passed as an argument
        if (args.length > 0) {
            filePath = args[0];
        } else {
            filePath = "";
        }

        try {
            // Create a portfolio service with dependencies
            PortfolioServiceUseCase portfolioServiceUseCase = new PortfolioServiceUseCaseImpl(
                    new CryptoAssetLoadCSV(filePath),
                    new CryptoAssetLoadCurrentPriceBatch(new CoincapRetriveCurrentPrice()),
                    new PortfolioCalculatorUseCaseImpl());

            // Calculate portfolio value
            System.out.println(portfolioServiceUseCase.getPortfolioValue());

        } catch (Exception e){
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
