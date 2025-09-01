interface BankUserService{
    double getBalance();
    void changeSecurityKey(String securityKey);
    void setAmount(double amount);
    String getSecurityKey();
}
