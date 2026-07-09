package GUI;

import Service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DepositFrame extends JFrame {

    private JTextField txtUserID;
    private JTextField txtAmount;
    private JButton btnDeposit;
    private JButton btnClear;
    private JButton btnBack;

    private TradingService tradingService;

    public DepositFrame(TradingService tradingService) {

        this.tradingService = tradingService;

        setTitle("Deposit Money");

        setSize(500,450);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel header = new JPanel();
        header.setBackground(new Color(37,99,235));
        JLabel title = new JLabel("💰 Deposit Money");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,28));
        header.add(title);
        add(header,BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setBackground(new Color(245,247,250));
        center.setBorder(new EmptyBorder(30,40,30,40));
        center.setLayout(new GridLayout(6,1,10,15));
        JLabel lblUser = new JLabel("User ID");
        lblUser.setFont(new Font("Segoe UI",Font.BOLD,16));
        txtUserID = new JTextField();

        txtUserID.setFont(new Font("Segoe UI",Font.PLAIN,18));

        JLabel lblAmount = new JLabel("Deposit Amount");

        lblAmount.setFont(new Font("Segoe UI",Font.BOLD,16));

        txtAmount = new JTextField();

        txtAmount.setFont(new Font("Segoe UI",Font.PLAIN,18));

        center.add(lblUser);

        center.add(txtUserID);

        center.add(lblAmount);

        center.add(txtAmount);

        add(center,BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

        buttons.setBackground(new Color(245,247,250));

        btnDeposit = new JButton("Deposit");

        btnDeposit.setBackground(new Color(34,197,94));

        btnDeposit.setForeground(Color.WHITE);

        btnDeposit.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnDeposit.setFocusPainted(false);

        btnDeposit.setPreferredSize(new Dimension(120,40));

        btnClear = new JButton("Clear");

        btnClear.setBackground(new Color(245,158,11));

        btnClear.setForeground(Color.WHITE);

        btnClear.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnClear.setFocusPainted(false);

        btnClear.setPreferredSize(new Dimension(120,40));

        btnBack = new JButton("Back");

        btnBack.setBackground(new Color(239,68,68));

        btnBack.setForeground(Color.WHITE);

        btnBack.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnBack.setFocusPainted(false);

        btnBack.setPreferredSize(new Dimension(120,40));

        buttons.add(btnDeposit);

        buttons.add(btnClear);

        buttons.add(btnBack);

        add(buttons,BorderLayout.SOUTH);

        btnDeposit.addActionListener(e -> depositMoney());

        btnClear.addActionListener(e -> {

            txtUserID.setText("");

            txtAmount.setText("");

        });

        btnBack.addActionListener(e -> dispose());

        setVisible(true);

    }

    private void depositMoney() {

        try {

            int userID = Integer.parseInt(txtUserID.getText());

            double amount = Double.parseDouble(txtAmount.getText());

            tradingService.depositBalance(userID, amount);

            JOptionPane.showMessageDialog(
                    this,
                    "Deposit Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            txtUserID.setText("");
            txtAmount.setText("");

        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers.",
                    "Error",
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