interface BankUserService{
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount);
    void changeSecurityKey(String securityKey);
    void setAmount(double amount);
    String getSecurityKey();
    void getStatements();
    String getDateAndTime();  
    void tDeposit(int newANum, int oldANum, double amount);
    void tWithdraw(int anum, double amount);
}
