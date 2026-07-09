package model;

public class Holding{
    private int holdingID;
    private int userID;
    private int stockID;
    private int quantity;

    public Holding(){}

    public Holding(int holdingID, int userID, int stockID, int quantity){
        this.holdingID = holdingID;
        this.userID = userID;
        this.stockID = stockID;
        this.quantity = quantity;
    }

    public void setHoldingID(int holdingID){
        this.holdingID = holdingID;
    }
    public int getHoldingID(){
        return holdingID;
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

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return quantity;
    }

    @Override
    public String toString(){
        return "Holding ID = "+holdingID+
               "\nUser ID = "+userID+
               "\nStock ID = "+stockID+
               "\nQunatity = "+quantity;
    }

}