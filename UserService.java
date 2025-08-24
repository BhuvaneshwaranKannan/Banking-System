import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class UserService implements BankUserService {

    private int accNo = 0;
    private double balance = 0;
    private String securityKey;
    final List<String> statements = new ArrayList<>();

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    @Override
    public String getDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        return timestamp;
    }

    @Override
    public void getStatements() {
        System.out.println(
                "\u001B[33m<-------------------------------- Transaction History -------------------------------->\u001B[0m\n");
        if (statements.isEmpty()) {
            System.out.println("\u001B[32mNo Transaction History Yet\u001B[0m\n");
        } else {
            for (String i : statements) {
                System.out.println(i);
            }
        }
        System.out.println(
                "\n\u001B[33m<-------------------------------- X X X X X X X X X X -------------------------------->\u001B[0m\n");
    }

    @Override
    public void setAmount(double amount) {
        this.balance = amount;
        statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                + " - You have Deposited Initial Amount "
                + "\u001B[32mRs. " + amount + "\u001B[0m"
                + " in your Account");
    }

    @Override
    public void changeSecurityKey(String password) {
        this.securityKey = password;
    }

    @Override
    public String getSecurityKey() {
        return securityKey;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        if (0 < amount) {
            balance = balance + amount;
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - You have Deposited "
                    + "\u001B[32mRs. " + amount + "\u001B[0m"
                    + " in your Account");

            System.out.println("\u001B[32mDeposit Successful!\u001B[0m\n");
        } else {
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - Deposit attempt FAILED (Insufficient Amount)");

            System.out.println("\u001B[31mInsufficient Amount.\u001B[0m\n");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - You have Withdrawn "
                    + "\u001B[31mRs. " + amount + "\u001B[0m"
                    + " in your Account");
            System.out.println("\u001B[32mWithdrawn Successful!\u001B[0m\n");
        } else {
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - Withdrawal attempt FAILED (Insufficient Balance)");

            System.out.println("\u001B[31mError Withdrawing!\u001B[0m\n");
        }
    }

    @Override
    public void tDeposit(int oldANum, int newANum, double amount) {
        if (0 < amount) {
            balance = balance + amount;
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - Account Number "
                    + "\u001B[32mAcc# 0" + oldANum + "\u001B[0m"
                    + " have deposited "
                    + "\u001B[32mRs. " + amount + "\u001B[0m"
                    + " in your Account");

            System.out.println("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + "\u001B[32m +Rs. " + amount + "\u001B[0m"
                    + " credited to the Account Number"
                    + "\u001B[32m Acc# 0" + newANum + "\u001B[0m"
                    + "\u001B[32m Successfully!\u001B[0m");
        } else {
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - Amount Transfering attempt FAILED (Insufficient Amount)");

            System.out.println("\u001B[31mInsufficient Amount\u001B[0m\n");
        }
    }

    @Override
    public void tWithdraw(int anum, double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - You have transfered "
                    + "\u001B[31mRs. " + amount + "\u001B[0m"
                    + " to "
                    + "\u001B[32mAcc# 0" + anum + "\u001B[0m");

            System.out.println("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + "\u001B[31m -Rs. " + amount + "\u001B[0m"
                    + " debited from your Account"
                    + "\u001B[32m Successfully!\u001B[0m");

        } else {
            statements.add("On " + "\u001B[34m[" + getDateAndTime() + "]\u001B[0m"
                    + " - Amount Transfering attempt FAILED (Insufficient Balance)");
            System.out.println("\u001B[31mError Withdrawing!\u001B[0m\n");
        }
    }
}

// Green → \u001B[32m

// Red → \u001B[31m

// Yellow → \u001B[33m

// Blue → \u001B[34m

// Cyan → \u001B[36m

// Purple → \u001B[35m

// White → \u001B[37m
