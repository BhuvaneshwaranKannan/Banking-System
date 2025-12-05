 import java.util.Scanner;

class CheckUserDetails {


    public boolean checkName(String name) {
        return name != null && name.matches("^[A-Za-z ]{3,30}$");
    }

    public boolean checkEmail(String email) {
        return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
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

    public int checkAccNumber(Scanner sc) {
        int NewAccNo;
        while (true) {
            System.out.print(Colors.blue + "Enter the Account Number : " + Colors.reset);

            if (sc.hasNextInt()) {
                NewAccNo = sc.nextInt();
                if (NewAccNo > 0) {
                    break;
                } else {
                    System.out.println(Colors.red + "Account Number must be positive! Try again.\n" + Colors.reset);
                    return 0;
                }
            } else {
                System.out.println(Colors.red + "Invalid Account Number! Please enter a valid Account Number.\n"  + Colors.reset);
                sc.next();
                return 0;
            }
        }
        return NewAccNo;
    }
}
