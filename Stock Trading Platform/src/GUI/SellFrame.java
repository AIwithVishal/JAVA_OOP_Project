package GUI;

import Service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SellFrame extends JFrame {

    private JTextField txtUserID;
    private JTextField txtSymbol;
    private JTextField txtQuantity;

    private JButton btnSell;
    private JButton btnClear;
    private JButton btnBack;

    private final TradingService tradingService;

    public SellFrame(TradingService tradingService) {

        this.tradingService = tradingService;

        setTitle("Sell Stocks");

        setSize(550, 500);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel header = new JPanel();

        header.setBackground(new Color(239, 68, 68));
        JLabel title = new JLabel("📉 Sell Stocks");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title);
        add(header, BorderLayout.NORTH);
        JPanel center = new JPanel();

        center.setBackground(new Color(245, 247, 250));
        center.setBorder(new EmptyBorder(30, 40, 30, 40));
        center.setLayout(new GridLayout(6, 1, 10, 15));
        JLabel lblUser = new JLabel("User ID");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtUserID = new JTextField();
        txtUserID.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel lblSymbol = new JLabel("Stock Symbol");
        lblSymbol.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtSymbol = new JTextField();
        txtSymbol.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtQuantity = new JTextField();
        txtQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        center.add(lblUser);
        center.add(txtUserID);

        center.add(lblSymbol);
        center.add(txtSymbol);

        center.add(lblQuantity);
        center.add(txtQuantity);

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttons.setBackground(new Color(245, 247, 250));

        btnSell = new JButton("Sell");

        btnSell.setBackground(new Color(239, 68, 68));

        btnSell.setForeground(Color.WHITE);

        btnSell.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnSell.setFocusPainted(false);

        btnSell.setPreferredSize(new Dimension(120, 40));

        btnClear = new JButton("Clear");

        btnClear.setBackground(new Color(245, 158, 11));

        btnClear.setForeground(Color.WHITE);

        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnClear.setFocusPainted(false);

        btnClear.setPreferredSize(new Dimension(120, 40));

        btnBack = new JButton("Back");

        btnBack.setBackground(new Color(75, 85, 99));

        btnBack.setForeground(Color.WHITE);

        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnBack.setFocusPainted(false);

        btnBack.setPreferredSize(new Dimension(120, 40));

        buttons.add(btnSell);
        buttons.add(btnClear);
        buttons.add(btnBack);

        add(buttons, BorderLayout.SOUTH);
        btnSell.addActionListener(e -> sellStock());
        btnClear.addActionListener(e -> {
            txtUserID.setText("");
            txtSymbol.setText("");
            txtQuantity.setText("");
        });
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }
    private void sellStock() {

        try {
            int userID = Integer.parseInt(txtUserID.getText());
            String symbol = txtSymbol.getText().trim().toUpperCase();
            int quantity = Integer.parseInt(txtQuantity.getText());
            tradingService.sellStock(userID, symbol, quantity);
            JOptionPane.showMessageDialog(
                    this,
                    "Stock Sold Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            txtUserID.setText("");
            txtSymbol.setText("");
            txtQuantity.setText("");

        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid values.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

        catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

}