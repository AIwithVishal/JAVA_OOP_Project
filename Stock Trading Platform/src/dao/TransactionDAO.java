package dao;

import model.Transaction;
import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {

    private final Connection con;

    public TransactionDAO(Connection con) {
        this.con = con;
    }

    public boolean addTransaction(Transaction transaction) {

        final String query = """
                INSERT INTO transactions
                (userID, stockID, transaction_type, quantity, price)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, transaction.getUserID());
            ps.setInt(2, transaction.getStockID());
            ps.setString(3, transaction.getTransactionType());
            ps.setInt(4, transaction.getQuantity());
            ps.setDouble(5, transaction.getPrice());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Transaction getTransactionById(int transactionID) {

        final String query =
                "SELECT * FROM transactions WHERE transactionID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, transactionID);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapTransaction(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Transaction> getTransactionsByUser(int userID) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        final String query =
                "SELECT * FROM transactions WHERE userID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    transactions.add(mapTransaction(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public ArrayList<Transaction> getAllTransactions() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        final String query = "SELECT * FROM transactions";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByStock(int stockID) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        final String query =
                "SELECT * FROM transactions WHERE stockID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, stockID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    transactions.add(mapTransaction(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {

        return new Transaction(
                rs.getInt("transactionID"),
                rs.getInt("userID"),
                rs.getInt("stockID"),
                rs.getString("transaction_type"),
                rs.getInt("quantity"),
                rs.getDouble("price"),
                rs.getTimestamp("transaction_date")
        );
    }
}