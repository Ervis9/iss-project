package org.example;

import java.util.Scanner;

public class StockTrackerMenu {
    private final StockService stockService;

    public StockTrackerMenu() {
        this.stockService = new StockService(new StockDAO());
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        // Ensure that the scanner is closed when the program ends
        try {
            while (true) {
                System.out.println("\n=== Stock Tracker ===");
                System.out.println("1. Add Stock");
                System.out.println("2. Display Stocks");
                System.out.println("3. Fetch Stock Data");
                System.out.println("4. Historical Analysis");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline after int input

                switch (choice) {
                    case 1 -> addStock(scanner);
                    case 2 -> displayStocks();
                    case 3 -> fetchStockData(scanner);
                    case 4 -> historicalAnalysis(scanner);
                    case 5 -> {
                        System.out.println("Exiting application...");
                        return; // Exit the program
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } finally {
            // Ensure the scanner is closed when the loop ends
            scanner.close();
        }
    }

    private void addStock(Scanner scanner) {
        System.out.print("Enter Stock Symbol: ");
        String symbol = scanner.nextLine();
        System.out.print("Enter Stock Name: ");
        String name = scanner.nextLine();

        // Validate input (you can add further validation if necessary)
        if (symbol.isEmpty() || name.isEmpty()) {
            System.out.println("Invalid input. Stock symbol and name cannot be empty.");
            return;
        }

        stockService.addStock(new Stock(symbol, name));
        System.out.println("Stock added successfully.");
    }

    private void displayStocks() {
        var stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            System.out.println("No stocks found.");
        } else {
            stocks.forEach(System.out::println);
        }
    }

    private void fetchStockData(Scanner scanner) {
        System.out.print("Enter Stock Symbol to fetch data: ");
        String symbol = scanner.nextLine();

        // Validate symbol input
        if (symbol.isEmpty()) {
            System.out.println("Stock symbol cannot be empty.");
            return;
        }

        try {
            stockService.fetchAndSaveStockData(symbol);
            System.out.println("Stock data fetched successfully.");
        } catch (Exception e) {
            System.out.println("Error fetching stock data: " + e.getMessage());
        }
    }

    private void historicalAnalysis(Scanner scanner) {
        System.out.print("Enter Stock Symbol for analysis: ");
        String symbol = scanner.nextLine();

        // Validate symbol input
        if (symbol.isEmpty()) {
            System.out.println("Stock symbol cannot be empty.");
            return;
        }

        try {
            stockService.performHistoricalAnalysis(symbol);
            System.out.println("Historical analysis performed.");
        } catch (Exception e) {
            System.out.println("Error performing historical analysis: " + e.getMessage());
        }
    }
}
