package GUI;

import model.Holding;
import model.Stock;
import java.util.ArrayList;
import Service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PortfolioFrame extends JFrame {

    private final TradingService tradingService;
    private JTextField txtUserID;
    private JButton btnLoad;
    private JButton btnRefresh;
    private JButton btnBack;
    private JTable portfolioTable;
    private DefaultTableModel tableModel;
    private JLabel lblTotalValue;

    public PortfolioFrame(TradingService tradingService) {

        this.tradingService = tradingService;

        setTitle("Portfolio");

        setSize(1000,650);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);

        add(createCenterPanel(), BorderLayout.CENTER);

        add(createBottomPanel(), BorderLayout.SOUTH);

        initializeActions();

        setVisible(true);
    }

    private JPanel createHeader(){

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(37,99,235));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("📁 My Portfolio");

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

        btnLoad = new JButton("View Portfolio");

        btnLoad.setBackground(new Color(37,99,235));

        btnLoad.setForeground(Color.WHITE);

        btnLoad.setFont(new Font("Segoe UI",Font.BOLD,15));

        btnLoad.setFocusPainted(false);

        searchPanel.add(lblUser);

        searchPanel.add(txtUserID);

        searchPanel.add(btnLoad);

        panel.add(searchPanel,BorderLayout.NORTH);

        String[] columns = {

                "Holding ID",
                "Stock ID",
                "Symbol",
                "Company",
                "Quantity",
                "Current Price",
                "Total Value"
        };

        tableModel = new DefaultTableModel(columns,0){

            @Override
            public boolean isCellEditable(int row,int column){

                return false;

            }

        };

        portfolioTable = new JTable(tableModel);

        portfolioTable.setRowHeight(28);

        portfolioTable.setFont(new Font("Segoe UI",Font.PLAIN,15));

        portfolioTable.getTableHeader().setFont(
                new Font("Segoe UI",Font.BOLD,15));

        portfolioTable.getTableHeader().setBackground(
                new Color(37,99,235));

        portfolioTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(portfolioTable);

        panel.add(scrollPane,BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        totalPanel.setBackground(new Color(245,247,250));

        lblTotalValue = new JLabel("Portfolio Value : Rs. 0.00");

        lblTotalValue.setFont(new Font("Segoe UI",Font.BOLD,20));

        lblTotalValue.setForeground(new Color(16,185,129));

        totalPanel.add(lblTotalValue);

        panel.add(totalPanel,BorderLayout.SOUTH);

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

        btnLoad.addActionListener(e -> loadPortfolio());

        btnRefresh.addActionListener(e -> loadPortfolio());

        btnBack.addActionListener(e -> dispose());

    }
    private void loadPortfolio() {

        try {

            int userID = Integer.parseInt(txtUserID.getText());

            tableModel.setRowCount(0);

            double totalPortfolioValue = 0;

            ArrayList<Holding> holdings = tradingService.getPortfolio(userID);

            if (holdings.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No portfolio found for this user.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE
                );

                lblTotalValue.setText("Portfolio Value : Rs. 0.00");

                return;
            }

            for (Holding holding : holdings) {

                Stock stock = tradingService.getStockById(holding.getStockID());

                if (stock == null) {
                    continue;
                }

                double currentPrice = stock.getCurrentPrice();

                double totalValue = currentPrice * holding.getQuantity();

                totalPortfolioValue += totalValue;

                tableModel.addRow(new Object[]{

                        holding.getHoldingID(),
                        holding.getStockID(),
                        stock.getSymbol(),
                        stock.getCompanyName(),
                        holding.getQuantity(),
                        currentPrice,
                        totalValue
                });

            }

            lblTotalValue.setText(
                    String.format("Portfolio Value : Rs. %.2f", totalPortfolioValue)
            );

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
}