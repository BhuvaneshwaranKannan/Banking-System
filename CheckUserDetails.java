class CheckUserDetails {

    public boolean checkName(String name) {
        return name != null && name.matches("^[A-Za-z ]{3,30}$");
    }

    public boolean checkEmail(String email) {
        return email.matches("^[\\w.%+-]+@[\\w-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean checkPhoneNumber(String num) {
        int length = num.length();
        if (length != 10)
            return false;
        if (num.charAt(0) < '5') {
            return false;
        }
        for (char ch : num.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

}
