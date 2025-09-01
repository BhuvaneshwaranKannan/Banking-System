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
            System.out.print("\n\u001B[34mEnter Your Name : \u001B[0m");
            name = sc.nextLine();

            boolean isName = check.checkName(name);
            if (isName) {
                System.out.println("\u001B[32mName Verified!\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }

        String email;
        while (true) {
            System.out.print("\n\u001B[34mEnter Email id : \u001B[0m");
            email = sc.next();
            sc.nextLine();

            boolean isEmail = check.checkEmail(email);

            if (isEmail) {
                System.out.println("\u001B[32mEmail Id Verified!\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }

        String pn;
        while (true) {
            System.out.print("\n\u001B[34mEnter Phone Number : \u001B[0m");
            pn = sc.next();
            sc.nextLine();

            boolean isPhoneNumber = check.checkPhoneNumber(pn);

            if (isPhoneNumber) {
                System.out.println("\u001B[32mPhone Number verified\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }
        return new Helper(name, email, pn);
    }

    public String checkName(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newName;
        while (true) {
            System.out.print("\n\u001B[34mEnter Your Name : \u001B[0m");
            newName = sc.nextLine();

            boolean isName = check.checkName(newName);

            if (isName) {
                System.out.println(
                        "\u001B[32mName Verified!\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }
        return newName;
    }

    public String checkEmail(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newEmail;
        while (true) {
            System.out.print("\n\u001B[34mEnter Email id : \u001B[0m");
            newEmail = sc.next();
            sc.nextLine();

            boolean isEmail = check.checkEmail(newEmail);

            if (isEmail) {
                System.out.println(
                        "\u001B[32mEmail Id Verified!\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }
        return newEmail;
    }

    public String checkPhoneNumber(Scanner sc) {
        CheckUserDetails check = new CheckUserDetails();

        String newPhoneNumber;
        while (true) {
            System.out.print(
                    "\n\u001B[34mEnter Phone Number : \u001B[0m");
            newPhoneNumber = sc.nextLine();

            boolean isPhoneNumber = check
                    .checkPhoneNumber(newPhoneNumber);

            if (isPhoneNumber) {
                System.out.println(
                        "\u001B[32mPhone Number verified\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31mTry again!\u001B[0m");
            }
        }
        return newPhoneNumber;
    }

}
