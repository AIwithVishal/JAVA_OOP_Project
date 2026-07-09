package model;
import java.sql.Timestamp;

public class Transaction{
    private int transactionID;
    private int userID;
    private int stockID;
    private String transactionType;
    private int quantity;
    private double price;
    private Timestamp transactionDate;

    public Transaction(){}

    public Transaction(int transactionID, int userID, int stockID, String transaction_type, int quantity, double price, Timestamp transactionDate){
        this.transactionID = transactionID;
        this.userID = userID;
        this.stockID = stockID;
        this.transactionType = transaction_type;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public void setTransactionID(int transactionID){
        this.transactionID = transactionID;
    }
    public int getTransactionID(){
        return transactionID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }
    public int getUserID(){
        return userID;
    }

    public void setStockID(int stockID){
        this.stockID = stockID;
    }
    public int getStockID(){
        return stockID;
    }

    public void setTransactionType(String transaction_type){
        this.transactionType = transaction_type;
    }
    public String getTransactionType(){
        return transactionType;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return quantity;
    }

    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }

    public void setTransactionDate(Timestamp transactionDate){
        this.transactionDate = transactionDate;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString(){
        return "Transaction ID = "+transactionID+
                "\nUser ID = "+userID+
                "\nStock ID = "+stockID+
                "\nTransaction type = "+transactionType+
                "\nQuantity = "+quantity+
                "\nPrice = "+price;
    }

}