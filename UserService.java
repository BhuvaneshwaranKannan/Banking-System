
class UserService implements BankUserService {
    
    private int accNo = 0;
    private double balance = 0;
    private String securityKey;
    
    public int getAccNo() {
        return accNo;
    }
    
    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    @Override
    public void setAmount(double amount) {
        this.balance = amount;
    }
    
    @Override
    public double getBalance() {
        return balance;
    }


    @Override
    public void changeSecurityKey(String password) {
        this.securityKey = password;
    }

    @Override
    public String getSecurityKey() {
        return securityKey;
    }

}

// Green → \u001B[32m

// Red → \u001B[31m

// Yellow → \u001B[33m

// Blue → \u001B[34m

// Cyan → \u001B[36m

// Purple → \u001B[35m

// White → \u001B[37m
