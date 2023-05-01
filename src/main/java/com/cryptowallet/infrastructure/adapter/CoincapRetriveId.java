package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.config.HttpRequestHandle;
import com.cryptowallet.infrastructure.config.ParameterSettings;
import com.jayway.jsonpath.JsonPath;

import java.util.List;

public class CoincapRetriveId {

    private static final String ASSET_SEARCH_API = ParameterSettings.API_URL + "/assets?search=%s";

    public CoincapRetriveId() {
    }

    public String getCryptoAssetIdBySymbol(String symbol) throws Exception{
        HttpRequestHandle httpRequestHandle = new HttpRequestHandle();
        String responseBody = httpRequestHandle.makeHttpGetRequest(String.format(ASSET_SEARCH_API, symbol));
        List<String> arrResp = JsonPath.read(responseBody, "$.data[?(@.symbol=='" + symbol + "')].id");
        if(arrResp.size()<1)
            throw new Exception(String.format("Error retrieving Crypto id by symbol %s",symbol));
        return arrResp.get(0);
    }
}
