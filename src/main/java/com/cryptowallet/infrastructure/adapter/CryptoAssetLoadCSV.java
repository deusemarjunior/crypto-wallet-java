package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.CryptoWalletApplication;
import com.cryptowallet.domain.entity.CryptoAsset;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CryptoAssetLoadCSV {

    private final String pathCSV;

    public CryptoAssetLoadCSV(String pathCSV) {
        this.pathCSV = pathCSV;
    }
    public List<CryptoAsset> readWalletFromCSV() throws Exception {
        CoincapRetriveId coincapRetriveId = new CoincapRetriveId();
        List<CryptoAsset> cryptoAssets = new ArrayList<>();
        try {
            Scanner scanner = null;
            if(pathCSV.isEmpty())
               scanner = new Scanner(CryptoAssetLoadCSV.class.getResourceAsStream("/wallet.csv"));
            else
               scanner = new Scanner(new File(pathCSV));

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                CryptoAsset position = new CryptoAsset(coincapRetriveId.getCryptoAssetIdBySymbol(line[0]),line[0], new BigDecimal(line[1]), new BigDecimal(line[2]));
                cryptoAssets.add(position);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception(String.format("Error read csv by path %s", pathCSV));
        }
        return cryptoAssets;
    }

}
