package model;

public class User{
    private int userID;
    private String username;
    private double balance;

    public User(){}

    public User(int userID, String username, double balance ){
        this.userID = userID;
        this.username = username;
        this.balance = balance;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }
    public int getUserID(){
        return userID;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public double getBalance(){
        return balance;
    }
    @Override
    public String toString(){
        return  "userID = "+userID +
                "userName = "+username +
                "Balance = "+balance;
    }
}