package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.config.HttpRequestHandle;
import com.cryptowallet.infrastructure.config.ParameterSettings;
import com.jayway.jsonpath.JsonPath;

import java.math.BigDecimal;

public class CoincapRetriveCurrentPrice {

    private static final String ASSET_HISTORY_API = ParameterSettings.API_URL + "/assets/%s/history?interval=d1&start=1617753600000&end=1617753601000";

    private HttpRequestHandle httpRequestHandle = new HttpRequestHandle();

    public CoincapRetriveCurrentPrice() {
    }

    public BigDecimal getCurrentCryptoPrice(String id) throws Exception {
        try {
            String responseBody = httpRequestHandle.makeHttpGetRequest(String.format(ASSET_HISTORY_API, id));
            String priceUsd = JsonPath.read(responseBody, "$.data[0].priceUsd");

            return new BigDecimal(priceUsd);
        }catch (Exception e){
            throw new Exception(String.format("Error retrieving Current Price by id %s", id));
        }
    }
}
