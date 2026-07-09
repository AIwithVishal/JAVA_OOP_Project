package dao;

import model.Stock;

import java.sql.*;
import java.util.ArrayList;

public class StockDAO {

    private final Connection con;
    public StockDAO(Connection con){
        this.con = con;
    }

    public Stock getStockById(int stockID) {

        String query = "SELECT * FROM stock WHERE stockID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, stockID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Stock(
                        rs.getInt("stockID"),
                        rs.getString("symbol"),
                        rs.getString("company_name"),
                        rs.getDouble("current_price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Stock getStockBySymbol(String symbol) {

        String query = "SELECT * FROM stock WHERE symbol = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, symbol);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Stock(
                        rs.getInt("stockID"),
                        rs.getString("symbol"),
                        rs.getString("company_name"),
                        rs.getDouble("current_price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Stock> getAllStocks() {

        ArrayList<Stock> stocks = new ArrayList<>();

        String query = "SELECT * FROM stock";

        try (
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Stock stock = new Stock(
                        rs.getInt("stockID"),
                        rs.getString("symbol"),
                        rs.getString("company_name"),
                        rs.getDouble("current_price")
                );

                stocks.add(stock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    public boolean updateStockPrice(int stockID, double newPrice) {

        String query = "UPDATE stock SET current_price = ? WHERE stockID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setDouble(1, newPrice);
            ps.setInt(2, stockID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}