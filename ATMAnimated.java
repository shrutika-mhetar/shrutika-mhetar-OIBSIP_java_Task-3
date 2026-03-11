import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ATMAnimated extends JFrame {

    CardLayout layout = new CardLayout();
    JPanel mainPanel = new JPanel(layout);

    JTextField cardField;
    JPasswordField pinField;
    JTextArea screen;

    BankAccount account;

    public ATMAnimated(){

        account = new BankAccount("1234567890","1234",10000);

        setTitle("ATM Machine");
        setSize(450,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(cardScreen(),"card");
        mainPanel.add(pinScreen(),"pin");
        mainPanel.add(menuScreen(),"menu");

        add(mainPanel);

        layout.show(mainPanel,"card");

        setVisible(true);
    }

    // CARD SCREEN
    JPanel cardScreen(){

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Insert ATM Card",SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.BOLD,22));

        JButton insertBtn = new JButton("Insert Card");

        insertBtn.addActionListener(e -> loadingAnimation());

        panel.add(label,BorderLayout.CENTER);
        panel.add(insertBtn,BorderLayout.SOUTH);

        return panel;
    }

    // LOADING ANIMATION
    void loadingAnimation(){

        JDialog loading = new JDialog(this,"Reading Card",true);

        loading.setSize(250,150);
        loading.setLayout(new BorderLayout());

        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        loading.add(new JLabel("Reading Card...",SwingConstants.CENTER),BorderLayout.NORTH);
        loading.add(bar,BorderLayout.CENTER);

        javax.swing.Timer timer = new javax.swing.Timer(2000,e -> {

            loading.dispose();
            layout.show(mainPanel,"pin");

        });

        timer.setRepeats(false);
        timer.start();

        loading.setLocationRelativeTo(this);
        loading.setVisible(true);
    }

    // PIN SCREEN
    JPanel pinScreen(){

        JPanel panel = new JPanel(new GridLayout(3,2));

        panel.add(new JLabel("Card Number"));
        cardField = new JTextField();
        panel.add(cardField);

        panel.add(new JLabel("PIN"));
        pinField = new JPasswordField();
        panel.add(pinField);

        JButton login = new JButton("Login");

        login.addActionListener(e -> {

            String card = cardField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if(card.equals(account.cardNumber) && pin.equals(account.pin)){

                JOptionPane.showMessageDialog(this,"Login Successful");
                layout.show(mainPanel,"menu");

            }else{

                JOptionPane.showMessageDialog(this,"Invalid Card or PIN");
            }

        });

        panel.add(login);

        return panel;
    }

    // MENU SCREEN
    JPanel menuScreen(){

        JPanel panel = new JPanel(new BorderLayout());

        screen = new JTextArea();
        screen.setEditable(false);
        screen.setFont(new Font("Monospaced",Font.BOLD,14));

        panel.add(new JScrollPane(screen),BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(4,1));

        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("Deposit");
        JButton balance = new JButton("Check Balance");
        JButton history = new JButton("Transaction History");

        withdraw.addActionListener(e -> withdraw());
        deposit.addActionListener(e -> deposit());
        balance.addActionListener(e -> screen.append("Balance: ₹"+account.balance+"\n"));
        history.addActionListener(e -> showHistory());

        buttons.add(withdraw);
        buttons.add(deposit);
        buttons.add(balance);
        buttons.add(history);

        panel.add(buttons,BorderLayout.EAST);

        return panel;
    }

    // WITHDRAW
    void withdraw(){

        String input = JOptionPane.showInputDialog("Enter withdraw amount");

        if(input == null || input.isEmpty()) return;

        try{

            double amount = Double.parseDouble(input);

            if(account.balance >= amount){

                account.balance -= amount;

                account.history.add("Withdraw ₹"+amount);

                screen.append("Withdraw ₹"+amount+"\n");

                generateReceipt("Withdraw ₹"+amount);

            }else{

                screen.append("Insufficient Balance\n");
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(this,"Invalid number");
        }
    }

    // DEPOSIT
    void deposit(){

        String input = JOptionPane.showInputDialog("Enter deposit amount");

        if(input == null || input.isEmpty()) return;

        try{

            double amount = Double.parseDouble(input);

            account.balance += amount;

            account.history.add("Deposit ₹"+amount);

            screen.append("Deposit ₹"+amount+"\n");

            generateReceipt("Deposit ₹"+amount);

        }catch(Exception e){

            JOptionPane.showMessageDialog(this,"Invalid number");
        }
    }

    // SHOW HISTORY
    void showHistory(){

        screen.append("\n--- Transaction History ---\n");

        for(String h : account.history){

            screen.append(h+"\n");
        }
    }

    // RECEIPT GENERATOR
    void generateReceipt(String text){

        try{

            FileWriter writer = new FileWriter("receipt.txt",true);

            writer.write("\n----- ATM RECEIPT -----\n");
            writer.write(LocalDateTime.now()+"\n");
            writer.write(text+"\n");
            writer.write("-----------------------\n");

            writer.close();

        }catch(Exception e){

            System.out.println("Receipt error");
        }
    }

    // BANK ACCOUNT CLASS
    class BankAccount{

        String cardNumber;
        String pin;
        double balance;

        ArrayList<String> history = new ArrayList<>();

        BankAccount(String card,String pin,double balance){

            this.cardNumber = card;
            this.pin = pin;
            this.balance = balance;
        }
    }

    public static void main(String[] args){

        new ATMAnimated();
    }
}