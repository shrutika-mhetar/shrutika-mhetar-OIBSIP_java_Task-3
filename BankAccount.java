import java.util.ArrayList;

public class BankAccount {

    private String cardNumber;
    private String pin;
    private double balance;

    private ArrayList<String> history = new ArrayList<>();

    public BankAccount(String cardNumber, String pin, double balance){

        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getPin(){
        return pin;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double amount){

        balance += amount;
        history.add("Deposited: ₹" + amount);
    }

    public boolean withdraw(double amount){

        if(balance >= amount){

            balance -= amount;
            history.add("Withdrawn: ₹" + amount);
            return true;
        }

        return false;
    }

    public boolean transfer(double amount,String receiver){

        if(balance >= amount){

            balance -= amount;
            history.add("Transferred ₹"+amount+" to "+receiver);
            return true;
        }

        return false;
    }

    public ArrayList<String> getHistory(){
        return history;
    }
}