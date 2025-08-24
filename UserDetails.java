class UserDetails implements BankUserDetails{
    private int accNo = 0;
    private String name = null;
    private String email = null;
    private String phoneNo = null;

    public UserDetails(int accNo, String name, String email, String phoneNo){
        this.name = name;
        this.accNo = accNo;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public int getAccNo(){
        return accNo;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String getEmail(){
        return email;
    }

    @Override
    public void setPhoneNo(String newPN){
        this.phoneNo = newPN;
    }

    @Override
    public String getPhoneNo(){
        return phoneNo;
    }

    @Override
    public String toString(){
        return "Account Number : " + accNo +
           "\nYour Name : " + name +
           "\nYour Email : " + email +
           "\nYour Phone Number : " + phoneNo;
    }

}
