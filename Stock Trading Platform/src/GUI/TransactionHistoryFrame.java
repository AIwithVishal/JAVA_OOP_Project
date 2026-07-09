package GUI;

import model.Transaction;
import model.Stock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionHistoryFrame extends JFrame {

    private final TradingService tradingService;

    private JTextField txtUserID;
    private JButton btnLoad;
    private JButton btnRefresh;
    private JButton btnBack;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionHistoryFrame(TradingService tradingService) {

        this.tradingService = tradingService;

        setTitle("Transaction History");

        setSize(1100,650);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        add(createHeader(),BorderLayout.NORTH);

        add(createCenterPanel(),BorderLayout.CENTER);

        add(createBottomPanel(),BorderLayout.SOUTH);

        initializeActions();
        styleTable();

        setVisible(true);

    }
    private JPanel createHeader(){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(37,99,235));
        panel.setBorder(new EmptyBorder(15,20,15,20));
        JLabel title = new JLabel("📜 Transaction History");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,28));
        panel.add(title,BorderLayout.WEST);
        return panel;

    }
    private JPanel createCenterPanel(){

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(245,247,250));

        panel.setBorder(new EmptyBorder(20,20,20,20));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));

        searchPanel.setBackground(new Color(245,247,250));

        JLabel lblUser = new JLabel("User ID");

        lblUser.setFont(new Font("Segoe UI",Font.BOLD,16));

        txtUserID = new JTextField(12);

        txtUserID.setFont(new Font("Segoe UI",Font.PLAIN,16));

        btnLoad = new JButton("View History");

        btnLoad.setBackground(new Color(37,99,235));

        btnLoad.setForeground(Color.WHITE);

        btnLoad.setFont(new Font("Segoe UI",Font.BOLD,15));

        btnLoad.setFocusPainted(false);

        searchPanel.add(lblUser);

        searchPanel.add(txtUserID);

        searchPanel.add(btnLoad);

        panel.add(searchPanel,BorderLayout.NORTH);

        String[] columns = {

                "Transaction ID",
                "Stock Symbol",
                "Company",
                "Type",
                "Quantity",
                "Price",
                "Total",
                "Date"
        };

        tableModel = new DefaultTableModel(columns,0){

            @Override
            public boolean isCellEditable(int row,int column){

                return false;

            }

        };

        transactionTable = new JTable(tableModel);

        transactionTable.setRowHeight(30);

        transactionTable.setFont(new Font("Segoe UI",Font.PLAIN,15));

        transactionTable.getTableHeader().setFont(
                new Font("Segoe UI",Font.BOLD,15));

        transactionTable.getTableHeader().setBackground(
                new Color(37,99,235));

        transactionTable.getTableHeader().setForeground(Color.WHITE);

        transactionTable.setSelectionBackground(new Color(186,230,253));

        transactionTable.setGridColor(new Color(220,220,220));

        JScrollPane scrollPane = new JScrollPane(transactionTable);

        panel.add(scrollPane,BorderLayout.CENTER);

        return panel;

    }
    private JPanel createBottomPanel(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,15));

        panel.setBackground(new Color(245,247,250));

        btnRefresh = new JButton("Refresh");

        btnRefresh.setBackground(new Color(245,158,11));

        btnRefresh.setForeground(Color.WHITE);

        btnRefresh.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnRefresh.setFocusPainted(false);

        btnRefresh.setPreferredSize(new Dimension(130,40));

        btnBack = new JButton("Back");

        btnBack.setBackground(new Color(239,68,68));

        btnBack.setForeground(Color.WHITE);

        btnBack.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnBack.setFocusPainted(false);

        btnBack.setPreferredSize(new Dimension(130,40));

        panel.add(btnRefresh);

        panel.add(btnBack);

        return panel;

    }
    private void initializeActions() {

        btnLoad.addActionListener(e -> loadTransactions());btnLoad.addActionListener(e -> {

            if(txtUserID.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "User ID cannot be empty.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;

            }

            loadTransactions();

        });
        btnLoad.setToolTipText("Load user's transaction history");

        btnRefresh.addActionListener(e -> {

            if(txtUserID.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter User ID first."
                );

                return;

            }

            loadTransactions();

        });
        btnRefresh.setToolTipText("Reload transaction history");

        btnBack.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(

                    this,

                    "Return to Dashboard?",

                    "Confirmation",

                    JOptionPane.YES_NO_OPTION

            );
            btnBack.setToolTipText("Return to Dashboard");

            if(option == JOptionPane.YES_OPTION){

                dispose();

            }

        });

    }
    private void loadTransactions() {

        try {

            int userID = Integer.parseInt(txtUserID.getText());

            tableModel.setRowCount(0);

            ArrayList<Transaction> transactions =
                    tradingService.getTransactionHistory(userID);

            if (transactions.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No transaction history available for this user.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            SimpleDateFormat sdf =
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            for (Transaction transaction : transactions) {

                Stock stock =
                        tradingService.getStockById(transaction.getStockID());

                String symbol = "";

                String company = "";

                if (stock != null) {

                    symbol = stock.getSymbol();

                    company = stock.getCompanyName();

                }

                double total =
                        transaction.getQuantity() * transaction.getPrice();

                tableModel.addRow(new Object[]{

                        transaction.getTransactionID(),

                        symbol,

                        company,

                        transaction.getTransactionType(),

                        transaction.getQuantity(),

                        String.format("%.2f", transaction.getPrice()),

                        String.format("%.2f", total),

                        sdf.format(transaction.getTransactionDate())

                });

            }

        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid User ID.",
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
    private void styleTable() {

        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        transactionTable.setRowSelectionAllowed(true);

        transactionTable.setShowGrid(true);

        transactionTable.setGridColor(new Color(220,220,220));

        transactionTable.setIntercellSpacing(new Dimension(5,5));

        transactionTable.setRowHeight(32);

        transactionTable.setFont(new Font("Segoe UI",Font.PLAIN,15));

        transactionTable.getTableHeader().setReorderingAllowed(false);

        transactionTable.getTableHeader().setResizingAllowed(true);

    }

}