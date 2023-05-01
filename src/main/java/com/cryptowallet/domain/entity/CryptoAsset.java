package com.cryptowallet.domain.entity;

import java.math.BigDecimal;

public class CryptoAsset {

    private String id;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal currentPrice;

    public CryptoAsset(String id, String symbol, BigDecimal quantity, BigDecimal price) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public CryptoAsset(String id, String symbol, BigDecimal quantity, BigDecimal price, BigDecimal currentPrice) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.currentPrice = currentPrice;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
