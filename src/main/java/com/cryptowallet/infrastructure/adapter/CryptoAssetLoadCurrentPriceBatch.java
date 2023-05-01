package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.config.ParameterSettings;
import com.cryptowallet.domain.entity.CryptoAsset;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class CryptoAssetLoadCurrentPriceBatch {

    private List<CryptoAsset> cryptoAssets;

    private CoincapRetriveCurrentPrice coincapRetriveCurrentPrice;
    public CryptoAssetLoadCurrentPriceBatch(CoincapRetriveCurrentPrice coincapRetriveCurrentPrice) {
        this.coincapRetriveCurrentPrice = coincapRetriveCurrentPrice;
    }

    public void enrichCryptoAssetCurrentPrice(List<CryptoAsset> cryptoAssets) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(ParameterSettings.MAX_CONCURRENT_REQUESTS);
        int batchSize = ParameterSettings.MAX_CONCURRENT_REQUESTS;
        int startIndex = 0;
        int endIndex = Math.min(batchSize, cryptoAssets.size());
        System.out.println(String.format("Now is %s", ParameterSettings.DATE_FORMATTER.format(LocalTime.now())));
        //Split List CryptAsset in group of execution
        while (startIndex < cryptoAssets.size()) {
            List<CryptoAsset> batchList = cryptoAssets.subList(startIndex, endIndex);
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (CryptoAsset position : batchList) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        position.setCurrentPrice(coincapRetriveCurrentPrice.getCurrentCryptoPrice(position.getId()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(String.format("Submitted request %s at %s",position.getSymbol(),ParameterSettings.DATE_FORMATTER.format(LocalTime.now())));
                 }, executor);

                futures.add(future);
            }

            for (CompletableFuture<Void> future : futures) {
                try {
                   future.get(ParameterSettings.TIMEOUT_TASK_IN_SECONDS,TimeUnit.SECONDS);
                }catch (TimeoutException e) {
                    throw new Exception(String.format("Error time processor is over limit %s seconds ", ParameterSettings.TIMEOUT_TASK_IN_SECONDS));
                } catch (Exception e) {
                    throw new Exception(String.format("Error retrive current prices -  ", e.getMessage()));
                }
            }

            startIndex = endIndex;
            endIndex = Math.min(endIndex + batchSize, cryptoAssets.size());
        }
        executor.shutdown();
    }

}
