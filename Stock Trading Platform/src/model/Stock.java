package model;

public class Stock {
    private int stockID;
    private String symbol;
    private String company_name;
    private double current_price;

    public Stock(){}

    public Stock(int stockID, String symbol, String company_name, double current_price){
        this.stockID = stockID;
        this.symbol = symbol;
        this.company_name = company_name;
        this.current_price = current_price;
    }

    public void setStockID(int stockID){
        this.stockID = stockID;
    }
    public int getStockID(){
        return stockID;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    public String getSymbol(){
        return symbol;
    }

    public void setCompanyName(String company_name){
        this.company_name = company_name;
    }
    public String getCompanyName(){
        return company_name;
    }

    public void setCurrentPrice(double current_price){

        this.current_price = current_price;
    }
    public double getCurrentPrice(){
        return current_price;
    }

    @Override
    public String toString(){
        return "StockID = "+stockID+
                "\nSymbol = "+ symbol+
                "\nCompany name = "+company_name+
                "\nCurrent price = "+current_price;
    }
}