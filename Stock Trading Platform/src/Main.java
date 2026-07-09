import GUI.DashboardFrame;
import Service.TradingService;
import database.DBConnection;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                Connection con = DBConnection.getConnection();

                if (con == null) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Unable to connect to MySQL Database.",
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                TradingService tradingService = new TradingService(con);
                new DashboardFrame(tradingService);

            }

            catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Application Failed to Start.\n\n" + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();

            }

        });

    }

}