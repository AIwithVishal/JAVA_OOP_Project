package Service;

import java.util.ArrayList;
import model.Transaction;
import model.User;
import model.Stock;
import model.Holding;

import dao.TransactionDAO;
import dao.HoldingDAO;
import dao.StockDAO;
import dao.UserDAO;

import java.sql.Connection;

public class TradingService {
    private Connection con;

    private UserDAO userDAO;
    private StockDAO stockDAO;
    private HoldingDAO holdingDAO;
    private TransactionDAO transactionDAO;

    public TradingService(Connection con){

        this.con = con;

        userDAO = new UserDAO(con);
        stockDAO = new StockDAO(con);
        holdingDAO = new HoldingDAO(con);
        transactionDAO = new TransactionDAO(con);

    }
    public void buyStock(int userID, String symbol, int quantity) {

        try {
            con.setAutoCommit(false);

            if (quantity <= 0) {
                System.out.println("Invalid quantity.");
                return;
            }
            User user = userDAO.getUserById(userID);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            Stock stock = stockDAO.getStockBySymbol(symbol);

            if (stock == null) {
                System.out.println("Stock not found.");
                return;
            }
            double totalCost = stock.getCurrentPrice() * quantity;

            if (user.getBalance() < totalCost) {
                System.out.println("Insufficient balance.");
                return;
            }

            double newBalance = user.getBalance() - totalCost;

            if (!userDAO.updateBalance(userID, newBalance)) {
                throw new Exception("Failed to update user balance.");
            }
            Holding holding = holdingDAO.getHolding(userID, stock.getStockID());

            if (holding != null) {

                int newQuantity = holding.getQuantity() + quantity;

                if (!holdingDAO.updateQuantity(userID,
                        stock.getStockID(),
                        newQuantity)) {

                    throw new Exception("Failed to update holding.");
                }

            } else {

                Holding newHolding = new Holding();

                newHolding.setUserID(userID);
                newHolding.setStockID(stock.getStockID());
                newHolding.setQuantity(quantity);

                if (!holdingDAO.addHolding(newHolding)) {
                    throw new Exception("Failed to add holding.");
                }
            }
            Transaction transaction = new Transaction();

            transaction.setUserID(userID);
            transaction.setStockID(stock.getStockID());
            transaction.setTransactionType("BUY");
            transaction.setQuantity(quantity);
            transaction.setPrice(stock.getCurrentPrice());

            if (!transactionDAO.addTransaction(transaction)) {
                throw new Exception("Failed to record transaction.");
            }
            con.commit();

            System.out.println("Stock Purchased Successfully.");

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void sellStock(int userID, String symbol, int quantity) {

        try {
            if (quantity <= 0) {
                System.out.println("Invalid quantity.");
                return;
            }
            User user = userDAO.getUserById(userID);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            Stock stock = stockDAO.getStockBySymbol(symbol);

            if (stock == null) {
                System.out.println("Stock not found.");
                return;
            }
            Holding holding = holdingDAO.getHolding(userID, stock.getStockID());

            if (holding == null) {
                System.out.println("You do not own this stock.");
                return;
            }

            // Step 5: Check quantity
            if (holding.getQuantity() < quantity) {
                System.out.println("Insufficient stock quantity.");
                return;
            }
            con.setAutoCommit(false);
            double totalAmount = stock.getCurrentPrice() * quantity;
            double newBalance = user.getBalance() + totalAmount;

            if (!userDAO.updateBalance(userID, newBalance)) {
                throw new Exception("Failed to update user balance.");
            }

            int remainingQuantity = holding.getQuantity() - quantity;

            if (remainingQuantity == 0) {

                if (!holdingDAO.deleteHolding(holding.getHoldingID())) {
                    throw new Exception("Failed to delete holding.");
                }

            } else {

                if (!holdingDAO.updateQuantity(
                        userID,
                        stock.getStockID(),
                        remainingQuantity)) {

                    throw new Exception("Failed to update holding.");
                }
            }

            Transaction transaction = new Transaction();

            transaction.setUserID(userID);
            transaction.setStockID(stock.getStockID());
            transaction.setTransactionType("SELL");
            transaction.setQuantity(quantity);
            transaction.setPrice(stock.getCurrentPrice());

            if (!transactionDAO.addTransaction(transaction)) {
                throw new Exception("Failed to record transaction.");
            }

            con.commit();

            System.out.println("Stock Sold Successfully.");
            System.out.println("Amount Received : " + totalAmount);
            System.out.println("Updated Balance : " + newBalance);

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void depositBalance(int userID, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        User user = userDAO.getUserById(userID);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        double newBalance = user.getBalance() + amount;

        boolean updated = userDAO.updateBalance(userID, newBalance);

        if (updated) {
            System.out.println("Deposit Successful.");
            System.out.println("New Balance = " + newBalance);
        } else {
            System.out.println("Deposit Failed.");
        }
    }

    public ArrayList<Holding> getPortfolio(int userID) {

        return holdingDAO.getHoldingsByUser(userID);

    }
    public Stock getStockById(int stockID) {

        return stockDAO.getStockById(stockID);

    }

    public void viewPortfolioConsole(int userID) {

        User user = userDAO.getUserById(userID);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        ArrayList<Holding> holdings =
                holdingDAO.getHoldingsByUser(userID);

        if (holdings.isEmpty()) {
            System.out.println("Portfolio is empty.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("Portfolio of : " + user.getUsername());
        System.out.println("========================================");

        double totalPortfolioValue = 0;

        for (Holding holding : holdings) {

            Stock stock =
                    stockDAO.getStockById(holding.getStockID());

            if (stock == null) {
                continue;
            }

            double stockValue =
                    holding.getQuantity() * stock.getCurrentPrice();

            totalPortfolioValue += stockValue;

            System.out.println("\nStock Symbol    : " + stock.getSymbol());
            System.out.println("Company Name    : " + stock.getCompanyName());
            System.out.println("Quantity Owned  : " + holding.getQuantity());
            System.out.println("Current Price   : " + stock.getCurrentPrice());
            System.out.println("Total Value     : " + stockValue);

            System.out.println("----------------------------------------");
        }

        System.out.println("Total Portfolio Value : " + totalPortfolioValue);
    }
    public ArrayList<Transaction> getTransactionHistory(int userID) {
        return transactionDAO.getTransactionsByUser(userID);
    }

}