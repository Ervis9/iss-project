package org.example;

import java.util.List;

public class StockService {
    private final StockDAO stockDAO;

    public StockService(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public void addStock(Stock stock) {
        stockDAO.saveStock(stock);
    }

    public List<Stock> getAllStocks() {
        return stockDAO.getAllStocks();
    }

    public void fetchAndSaveStockData(String symbol) {
        // Placeholder for API integration or file retrieval logic
        double price = Math.random() * 100; // Simulate stock price
        stockDAO.saveStockData(symbol, price);
    }

    public void performHistoricalAnalysis(String symbol) {
        List<Double> prices = stockDAO.getStockPrices(symbol);
        if (prices.isEmpty()) {
            System.out.println("No historical data found for symbol: " + symbol);
            return;
        }

        double average = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        System.out.println("Historical average price for " + symbol + ": " + average);
    }
}

