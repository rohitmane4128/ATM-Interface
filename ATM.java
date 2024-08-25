import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserAccount {
    private double balance;

    public UserAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

public class ATM extends JFrame implements ActionListener {
    private UserAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;

    public ATM(UserAccount account) {
        this.account = account;

        
        setTitle("ATM Interface");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // Use GridBagLayout for better control over component positioning

        
        balanceLabel = new JLabel("Current Balance: $" + account.getBalance());
        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(100, 25)); // Set a small size for the input box

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton exitButton = new JButton("Exit");

       
        customizeButton(checkBalanceButton);
        customizeButton(depositButton);
        customizeButton(withdrawButton);
        customizeButton(exitButton);

        
        checkBalanceButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        exitButton.addActionListener(this);

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(balanceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(checkBalanceButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(depositButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(withdrawButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(exitButton, gbc);

        setVisible(true);
    }


    private void customizeButton(JButton button) {
        button.setFocusPainted(false);  // Remove focus border
        button.setPreferredSize(new Dimension(150, 35)); // Set button size
        button.setBackground(new Color(70, 130, 180)); // Change background color
        button.setForeground(Color.WHITE);  // Change text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
    }

    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            switch (command) {
                case "Check Balance":
                    balanceLabel.setText("Current Balance: $" + account.getBalance());
                    break;
                case "Deposit":
                    double depositAmount = Double.parseDouble(amountField.getText());
                    account.deposit(depositAmount);
                    balanceLabel.setText("Current Balance: $" + account.getBalance());
                    JOptionPane.showMessageDialog(this, "Successfully deposited $" + depositAmount);
                    break;
                case "Withdraw":
                    double withdrawAmount = Double.parseDouble(amountField.getText());
                    if (account.withdraw(withdrawAmount)) {
                        balanceLabel.setText("Current Balance: $" + account.getBalance());
                        JOptionPane.showMessageDialog(this, "Successfully withdrew $" + withdrawAmount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient funds. Cannot withdraw $" + withdrawAmount, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Exit":
                    JOptionPane.showMessageDialog(this, "Exiting ATM. Have a great day!");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid option. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        UserAccount account = new UserAccount(1000.00); // Initial balance
        new ATM(account);
    }
}
