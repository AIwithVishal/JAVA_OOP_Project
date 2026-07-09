package GUI;
import Service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardFrame extends JFrame {
    private TradingService tradingService;

    private final Color BACKGROUND = new Color(240, 244, 248);
    private final Color SIDEBAR = new Color(17, 24, 39);
    private final Color HEADER1 = new Color(37, 99, 235);
    private final Color HEADER2 = new Color(59, 130, 246);
    private final Color WHITE = Color.WHITE;
    private final Color DEPOSIT = new Color(34, 197, 94);
    private final Color BUY = new Color(59, 130, 246);
    private final Color SELL = new Color(239, 68, 68);
    private final Color PORTFOLIO = new Color(139, 92, 246);
    private final Color HISTORY = new Color(245, 158, 11);


    private JButton depositButton;
    private JButton buyButton;
    private JButton sellButton;
    private JButton portfolioButton;
    private JButton historyButton;
    private JButton exitButton;

    public DashboardFrame(TradingService tradingService) {

        this.tradingService = tradingService;

        setTitle("Stock Trading Platform");
        setSize(1400, 800);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        getContentPane().setBackground(BACKGROUND);

        add(createHeader(), BorderLayout.NORTH);

        add(createSidebar(), BorderLayout.WEST);

        add(createDashboard(), BorderLayout.CENTER);
        initializeActions();

        setVisible(true);

    }

    private JPanel createHeader() {

        JPanel panel = new JPanel() {

            @Override

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0,
                        HEADER1,
                        getWidth(),
                        getHeight(),
                        HEADER2
                );

                g2.setPaint(gp);

                g2.fillRect(0, 0, getWidth(), getHeight());

            }

        };

        panel.setPreferredSize(new Dimension(1400, 80));

        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel(" STOCK TRADING PLATFORM");

        title.setForeground(WHITE);

        title.setFont(new Font("Segoe UI", Font.BOLD, 30));

        title.setBorder(new EmptyBorder(20, 30, 20, 20));

        JLabel welcome = new JLabel("Welcome, Prem Vishal   ");

        welcome.setForeground(WHITE);

        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));

        panel.add(title, BorderLayout.WEST);

        panel.add(welcome, BorderLayout.EAST);

        return panel;

    }

    private JPanel createSidebar() {

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(220, 700));

        panel.setBackground(SIDEBAR);

        panel.setLayout(new GridLayout(7, 1, 10, 15));

        panel.setBorder(new EmptyBorder(25, 20, 25, 20));

        depositButton = createSidebarButton("💰 Deposit");

        buyButton = createSidebarButton("📈 Buy Stock");

        sellButton = createSidebarButton("📉 Sell Stock");

        portfolioButton = createSidebarButton("📁 Portfolio");

        historyButton = createSidebarButton("📜 Transactions");

        exitButton = createSidebarButton("🚪 Exit");

        JLabel dash = new JLabel("Dashboard");

        dash.setForeground(Color.WHITE);

        dash.setHorizontalAlignment(SwingConstants.CENTER);

        dash.setFont(new Font("Segoe UI", Font.BOLD, 24));

        panel.add(dash);

        panel.add(depositButton);

        panel.add(buyButton);

        panel.add(sellButton);

        panel.add(portfolioButton);

        panel.add(historyButton);

        panel.add(exitButton);

        return panel;

    }

    private JPanel createDashboard() {

        JPanel main = new JPanel(new BorderLayout());

        main.setBackground(BACKGROUND);

        JLabel heading = new JLabel("Trading Dashboard");

        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));

        heading.setBorder(new EmptyBorder(25, 40, 20, 20));

        main.add(heading, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 3, 30, 30));

        cards.setBackground(BACKGROUND);

        cards.setBorder(new EmptyBorder(20, 40, 40, 40));

        cards.add(createCard("💰", "Deposit Money", DEPOSIT));

        cards.add(createCard("📈", "Buy Stocks", BUY));

        cards.add(createCard("📉", "Sell Stocks", SELL));
        cards.add(createCard("📁", "Portfolio", PORTFOLIO));
        cards.add(createCard("📜", "Transactions", HISTORY));
        cards.add(createCard("🚪", "Exit", Color.DARK_GRAY));
        main.add(cards, BorderLayout.CENTER);

        return main;


    }
    private JButton createSidebarButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 17));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(31, 41, 55));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(10,20,10,10));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBackground(new Color(59,130,246));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                button.setBackground(new Color(31,41,55));

            }

        });

        return button;

    }
    private JPanel createCard(String icon, String text, Color color) {

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(210, 210, 210));
                g2.fillRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 30, 30);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 16, getHeight() - 16, 30, 30);
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(260, 180));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
        iconLabel.setForeground(color);

        JLabel textLabel = new JLabel(text);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        textLabel.setForeground(new Color(55, 65, 81));

        JPanel line = new JPanel();
        line.setPreferredSize(new Dimension(100, 8));
        line.setBackground(color);

        panel.add(line, BorderLayout.NORTH);
        panel.add(iconLabel, BorderLayout.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                panel.setBorder(
                        BorderFactory.createLineBorder(color, 3, true));

            }

            @Override
            public void mouseExited(MouseEvent e) {

                panel.setBorder(null);

            }

            @Override
            public void mouseClicked(MouseEvent e) {

                switch (text) {

                    case "Deposit Money":

                        new DepositFrame(tradingService);

                        break;

                    case "Buy Stocks":

                        new BuyFrame(tradingService);

                        break;

                    case "Sell Stocks":

                        new SellFrame(tradingService);

                        break;

                    case "Portfolio":

                        new PortfolioFrame(tradingService);

                        break;

                    case "Transactions":

                        new TransactionHistoryFrame(tradingService);

                        break;

                    case "Exit":

                        exitApplication();

                        break;

                    default:

                        JOptionPane.showMessageDialog(
                                DashboardFrame.this,
                                "Feature not available.");

                }

            }

        });

        return panel;
    }

    private void exitApplication(){

        int option = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if(option==JOptionPane.YES_OPTION){

            dispose();

        }

    }

    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());

        footer.setBackground(new Color(17,24,39));

        footer.setPreferredSize(new Dimension(100,40));

        JLabel lbl = new JLabel(
                "© 2026 Stock Trading Platform | Developed by Prem Vishal");

        lbl.setForeground(Color.WHITE);

        lbl.setFont(new Font("Segoe UI",Font.PLAIN,14));

        lbl.setBorder(new EmptyBorder(10,20,10,20));

        footer.add(lbl,BorderLayout.WEST);

        return footer;

    }

    private JPanel createStatistics() {

        JPanel panel = new JPanel(new GridLayout(1,3,25,25));

        panel.setOpaque(false);

        panel.setBorder(new EmptyBorder(20,40,20,40));

        panel.add(createStatCard(
                "Current Balance",
                "Rs. 100000",
                new Color(34,197,94)));

        panel.add(createStatCard(
                "Portfolio Value",
                "Rs. 35000",
                new Color(59,130,246)));

        panel.add(createStatCard(
                "Total Stocks",
                "5",
                new Color(245,158,11)));

        return panel;

    }
    private JPanel createStatCard(String title,
                                  String value,
                                  Color color){

        JPanel panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics g){

                super.paintComponent(g);

                Graphics2D g2=(Graphics2D)g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(210,210,210));

                g2.fillRoundRect(8,8,getWidth()-16,getHeight()-16,25,25);

                g2.setColor(Color.WHITE);

                g2.fillRoundRect(0,0,getWidth()-16,getHeight()-16,25,25);

            }

        };

        panel.setOpaque(false);

        panel.setLayout(new GridLayout(2,1));

        JLabel lblTitle = new JLabel(title);

        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        lblTitle.setForeground(Color.GRAY);

        lblTitle.setFont(new Font("Segoe UI",Font.BOLD,18));

        JLabel lblValue = new JLabel(value);

        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        lblValue.setForeground(color);

        lblValue.setFont(new Font("Segoe UI",Font.BOLD,28));

        panel.add(lblTitle);

        panel.add(lblValue);

        return panel;

    }

    private void initializeActions(){

        depositButton.addActionListener(e -> {
            new DepositFrame(tradingService);
        });

        buyButton.addActionListener(e -> {
            new BuyFrame(tradingService);
        });

        sellButton.addActionListener(e -> {
            new SellFrame(tradingService);
        });

        portfolioButton.addActionListener(e -> {

            new PortfolioFrame(tradingService);

        });

        historyButton.addActionListener(e -> {

            new TransactionHistoryFrame(tradingService);

        });

        exitButton.addActionListener(e->exitApplication());

    }

}
