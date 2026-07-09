package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    private final Connection con;
    public UserDAO(Connection con){
        this.con = con;
    }

    public boolean addUser(User user) {

        String query = "INSERT INTO users(username, balance) VALUES(?, ?)";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, user.getUsername());
            ps.setDouble(2, user.getBalance());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int userID) {

        String query = "SELECT * FROM users WHERE userID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getDouble("balance")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<User> getAllUsers() {

        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                User user = new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getDouble("balance")
                );

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean updateBalance(int userID, double balance) {

        String query = "UPDATE users SET balance = ? WHERE userID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setDouble(1, balance);
            ps.setInt(2, userID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userID) {

        String query = "DELETE FROM users WHERE userID = ?";

        try (
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, userID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}