import java.util.Scanner;

public class CheckAccountDetails {

    public class Helper {
        public String name;
        public String email;
        public String phoneNumber;

        public Helper(String name, String email, String phoneNumber) {
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }
    }

    public Helper checkAD(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();
        String name;
        while (true) {
            System.out.print(Colors.blue +"\nEnter Your Name : " + Colors.reset);
            name = sc.nextLine();

            boolean isName = check.checkName(name);
            if (isName) {
                System.out.println(Colors.green +"Name Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }

        String email;
        while (true) {
            System.out.print(Colors.blue +"\nEnter Your Email : " + Colors.reset);
            email = sc.next();
            sc.nextLine();

            boolean isEmail = check.checkEmail(email);

            if (isEmail) {
                System.out.println(Colors.green +"Email ID Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }

        String pn;
        while (true) {
            System.out.print(Colors.blue +"\nEnter Your Phone Number : " + Colors.reset);
            pn = sc.next();

            boolean isPhoneNumber = check.checkPhoneNumber(pn);

            if (isPhoneNumber) {
                System.out.println(Colors.green +"Phone Number Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }
        return new Helper(name, email, pn);
    }

    public String checkName(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newName;
        while (true) {
            System.out.print(Colors.blue +"Enter Your Name : " + Colors.reset);
            newName = sc.nextLine();

            boolean isName = check.checkName(newName);

            if (isName) {
                System.out.println(Colors.green +"Name Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }
        return newName;
    }

    public String checkEmail(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newEmail;
        while (true) {
            System.out.print(Colors.blue +"Enter Your Email : " + Colors.reset);
            newEmail = sc.next();
            sc.nextLine();

            boolean isEmail = check.checkEmail(newEmail);

            if (isEmail) {
                System.out.println(Colors.green +"Email ID Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }
        return newEmail;
    }

    public String checkPhoneNumber(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newPhoneNumber;
        while (true) {
            System.out.print(Colors.blue +"\nEnter Your Phone Number : " + Colors.reset);
            newPhoneNumber = sc.nextLine();

            boolean isPhoneNumber = check
                    .checkPhoneNumber(newPhoneNumber);

            if (isPhoneNumber) {
                System.out.println(Colors.green +"Phone Number Verified!" + Colors.reset);
                break;
            } else {
                System.out.println(Colors.red +"Try again!" + Colors.reset);
            }
        }
        return newPhoneNumber;
    }

}
