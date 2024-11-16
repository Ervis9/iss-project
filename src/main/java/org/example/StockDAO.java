package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks";  // Use MySQL database URL
    private static final String USER = "root";  // Database username
    private static final String PASSWORD = "Root";  // Database password

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Initialize database tables
            stmt.execute("CREATE TABLE IF NOT EXISTS stocks (symbol TEXT PRIMARY KEY, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS stock_data (symbol TEXT, price REAL, date TEXT)");
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database: " + e.getMessage());
        }
    }

    public void saveStock(Stock stock) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO stocks (symbol, name) VALUES (?, ?)")) {
            pstmt.setString(1, stock.getSymbol());
            pstmt.setString(2, stock.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving stock: " + e.getMessage());
        }
    }

    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM stocks")) {
            while (rs.next()) {
                stocks.add(new Stock(rs.getString("symbol"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stocks: " + e.getMessage());
        }
        return stocks;
    }

    public void saveStockData(String symbol, double price) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO stock_data (symbol, price, date) VALUES (?, ?, datetime('now'))")) {
            pstmt.setString(1, symbol);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving stock data: " + e.getMessage());
        }
    }

    public List<Double> getStockPrices(String symbol) {
        List<Double> prices = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT price FROM stock_data WHERE symbol = ?")) {
            pstmt.setString(1, symbol);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    prices.add(rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stock prices: " + e.getMessage());
        }
        return prices;
    }
}
