package dao;

import model.Holding;

import java.sql.*;
import java.util.ArrayList;

public class HoldingDAO {

    private final Connection con;
    public HoldingDAO(Connection con){
        this.con = con;
    }

    public boolean addHolding(Holding holding) {

        String query = "INSERT INTO holdings(userID, stockID, quantity) VALUES(?, ?, ?)";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, holding.getUserID());
            ps.setInt(2, holding.getStockID());
            ps.setInt(3, holding.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Holding getHolding(int userID, int stockID) {

        String query = "SELECT * FROM holdings WHERE userID = ? AND stockID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, userID);
            ps.setInt(2, stockID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Holding(
                        rs.getInt("holdingID"),
                        rs.getInt("userID"),
                        rs.getInt("stockID"),
                        rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Holding> getHoldingsByUser(int userID) {

        ArrayList<Holding> holdings = new ArrayList<>();

        String query = "SELECT * FROM holdings WHERE userID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Holding holding = new Holding(
                        rs.getInt("holdingID"),
                        rs.getInt("userID"),
                        rs.getInt("stockID"),
                        rs.getInt("quantity")
                );

                holdings.add(holding);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holdings;
    }


    public boolean updateQuantity(int userID, int stockID, int quantity) {

        String query = "UPDATE holdings SET quantity = ? WHERE userID = ? AND stockID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, quantity);
            ps.setInt(2, userID);
            ps.setInt(3, stockID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHolding(int holdingID) {

        String query = "DELETE FROM holdings WHERE holdingID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, holdingID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}