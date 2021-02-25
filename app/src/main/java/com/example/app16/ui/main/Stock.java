package com.example.app16.ui.main;

public class Stock {
    private String stockTicker;
    private String stockName;
    private String exchange;
    private String category;
    private String country;

    public Stock(String stockTicker, String stockName) {
        this.stockTicker = stockTicker;
        this.stockName = stockName;
    }

    public Stock(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Override
    public String toString() {
        return stockTicker;
    }
}
