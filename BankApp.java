
import java.util.*;

public class BankApp {

    private static HashMap<Integer, UserService> accounts = new HashMap<>();
    private static HashMap<Integer, UserDetails> reg = new HashMap<>();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("\u001B[33m=================================\u001B[0m");
            System.out.println("\u001B[33m\t Banking System \u001B[0m");
            System.out.println("\u001B[33m=================================\u001B[0m");
            boolean y = true;
            while (y) {
                System.out.println("\u001B[33m---------------------------------\u001B[0m");

                System.out.println("""
                                   
                                   \u001b[32m1\u001b[0m - Register Account""");
                System.out.println("\u001B[32m2\u001B[0m" + " - Login");
                System.out.println("""
                                   \u001b[31m3\u001b[0m - Exit App
                                   """);

                System.out.println("\u001B[33m---------------------------------\u001B[0m");

                System.out.print("\n\u001B[34mEnter the Option[1, 2, 3] : \u001B[0m");

                if (!sc.hasNextInt()) {
                    System.out.println("\u001B[31mInvalid input! Please enter a number (1-3).\u001B[0m\n");
                    sc.next();
                    continue;
                }

                int opt = sc.nextInt();
                System.out.println("");

                switch (opt) {
                    // <-------------------------------------------------- Register
                    // ------------------------------------------------------------>
                    case 1 -> {

                        int NewAccNo = -1;

                        while (true) {
                            System.out.print("Enter the Account Number : ");

                            if (sc.hasNextInt()) {
                                NewAccNo = sc.nextInt();
                                if (NewAccNo > 0) {
                                    break; 
                                } else {
                                    System.out.println(
                                            "\u001B[31mAccount Number must be positive! Try again.\u001B[0m\n");
                                }
                            } else {
                                System.out.println(
                                        "\u001B[31mInvalid Account Number! Please enter a valid Account Number.\u001B[0m\n");
                                sc.next();
                            }
                        }

                        if (accounts.containsKey(NewAccNo)) {
                            System.out.println("\u001B[31mAccount Already Exist!\u001B[0m");
                            System.out.println("");
                            break;
                        }

                        System.out.print("\nEnter the Password : ");
                        String pass = sc.next();

                        System.out.print("\n\u001B[33mDo you want to Add your Account Details Now?\u001B[0m");
                        System.out.print("\n\u001B[34mFor Yes - [Y] Or Press Any key to continue : \u001B[0m");
                        char yOrN = sc.next().charAt(0);

                        CheckUserDetails check = new CheckUserDetails();
                        if (yOrN == 'y' || yOrN == 'Y') {
                            System.out.println("\n\u001B[32mEnter Account Details : \u001B[0m");
                            sc.nextLine();

                            String name;
                            while (true) {
                                System.out.print("\n\u001B[34mEnter Your Name : \u001B[0m");
                                name = sc.nextLine().trim();

                                boolean isName = check.checkName(name);
                                if (isName) {
                                    System.out.println("Name verified");
                                    break;
                                } else {
                                    System.out.println("Try again");
                                }
                            }

                            String email;
                            while (true) {
                                System.out.print("\n\u001B[34mEnter Email id : \u001B[0m");
                                email = sc.next();
                                sc.nextLine();

                                boolean isEmail = check.checkEmail(email);
                                if (isEmail) {
                                    System.out.println("Email id verified");
                                    break;
                                } else {
                                    System.out.println("Try again");
                                }
                            }

                            String pn;
                            while (true) {
                                System.out.print("\n\u001B[34mEnter Phone Number : \u001B[0m");
                                pn = sc.nextLine();

                                boolean isPhoneNumber = check.checkPhoneNumber(pn);
                                if (isPhoneNumber) {
                                    System.out.println("Phone Number verified");
                                    break;
                                } else {
                                    System.out.println("Try again");
                                }
                            }

                            UserDetails details = new UserDetails(NewAccNo, name, email, pn);
                            reg.put(NewAccNo, details);
                            System.out.println("\n\u001B[32mAccount details Successfully Added\u001B[0m");

                        } else {
                            System.out.println(
                                    "\u001B[36m\n---> You can Enter the Account details after login!\u001B[0m");
                        }

                        double amount = 0;

                        while (true) {
                            System.out.print("\n\u001B[32mEnter the Initial Deposit Amount : \u001B[0m");

                            if (sc.hasNextDouble()) {
                                amount = sc.nextDouble();
                                if (amount >= 0) {
                                    break;
                                } else {
                                    System.out.println(
                                            "\u001B[31mAmount cannot be negative! Please enter again.\u001B[0m");
                                }
                            } else {
                                System.out.println("\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m");
                                sc.next();
                            }
                        }

                        UserService NewUser = new UserService();
                        NewUser.setAmount(amount);
                        NewUser.changeSecurityKey(pass);

                        accounts.put(NewAccNo, NewUser);

                        System.out.println("");
                        System.out.println("\u001B[33mAccount Created!\u001B[0m");
                        System.out.println("\u001B[36m--->You have to login to continue.\u001B[0m");
                        System.out.println("");
                    }
                    // <-------------------------------------------------- Login
                    // ------------------------------------------------------------>
                    case 2 -> {

                        int oldAccNo = 0;

                        while (true) {
                            System.out.print("Enter the Account Number : ");

                            if (sc.hasNextInt()) {
                                oldAccNo = sc.nextInt();

                                if (oldAccNo > 0) {
                                    break;
                                } else {
                                    System.out.println(
                                            "\u001B[31mAccount Number must be positive! Try again.\u001B[0m\n");
                                }
                            } else {
                                System.out.println(
                                        "\u001B[31mInvalid Account Number! Please enter a Valid existing Account Number.\u001B[0m\n");
                                sc.next();
                            }
                        }

                        UserService us = new UserService();
                        us.setAccNo(oldAccNo);

                        if (!accounts.containsKey(oldAccNo)) {
                            System.out.println("\u001B[31mNo Such Account Exist!\u001B[0m\n");
                            break;
                        }

                        UserService existUser = accounts.get(oldAccNo);

                        boolean authenticated = false;
                        for (int i = 0; i < 3; i++) {
                            System.out.print("\nEnter the Password : ");
                            String inputPass = sc.next();
                            if (existUser.getSecurityKey().equals(inputPass)) {
                                authenticated = true;
                                break;
                            } else {
                                System.out.println("\u001B[31mWrong Password! Attempts left: " + (2 - i) + "\u001B[0m");
                            }
                        }
                        if (!authenticated) {
                            System.out.println("\n\u001B[31mToo many failed attempts. Returning to menu.\u001B[m\n");
                            break;
                        }
                        System.out.println("\n\u001B[32mLogin Successful!\u001B[0m\n");

                        boolean x = true;
                        while (x) {
                            System.out.println("\u001B[32m1\u001B[0m" + " - Change Password");
                            System.out.println("\u001B[32m2\u001B[0m" + " - Deposit Amount");
                            System.out.println("\u001B[32m3\u001B[0m" + " - Withdraw Amount");
                            System.out.println("\u001B[32m4\u001B[0m" + " - Check Balance");
                            System.out.println("\u001B[32m5\u001B[0m" + " - Transaction History");
                            System.out.println("\u001B[32m6\u001B[0m" + " - Menu");
                            System.out.println("\u001B[32m7\u001B[0m" + " - Money Transfer");
                            System.out.println("\u001B[31m8\u001B[0m" + " - Logout");
                            System.out.print("\n\u001B[34mEnter the Option[1, 2, 3, 4, 5, 6, 7, 8] : \u001B[0m");

                            if (!sc.hasNextInt()) {
                                System.out.println("\u001B[31mInvalid input! Please enter a number (1-8).\u001B[0m\n");
                                sc.next();
                                continue;
                            }

                            int option = sc.nextInt();
                            System.out.println("");

                            switch (option) {

                                // <----------------------------- change Password ----------------------------->
                                case 1 -> {
                                    System.out.print("\nType the Previous Password : ");
                                    String prePass = sc.next();

                                    if (existUser.getSecurityKey().equals(prePass)) {

                                        System.out.print("\nSet New Password : ");
                                        existUser.changeSecurityKey(sc.next());

                                        System.out.println("\u001B[32mPassword Saved!\u001B[0m\n");

                                    } else {
                                        System.out.println("\u001B[31mIncorrect Password!\u001B[0m");
                                        System.out.println();
                                    }
                                }

                                // <----------------------------- Deposit ----------------------------->
                                case 2 -> {
                                    System.out.print("Enter the deposit amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println(
                                                "\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }

                                    existUser.deposit(sc.nextDouble());
                                }

                                // <----------------------------- Withdraw ----------------------------->
                                case 3 -> {
                                    System.out.print("Enter the withdrawal amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println(
                                                "\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }

                                    existUser.withdraw(sc.nextDouble());
                                }

                                // <----------------------------- Get Balance ----------------------------->
                                case 4 -> {
                                    System.out.println("\u001B[32mYour Bank Balance is \u001B[0m" + "Rs. "
                                            + existUser.getBalance());
                                    System.out.println();
                                }

                                // <----------------------------- Get Statement ----------------------------->
                                case 5 ->
                                    existUser.getStatements();

                                // <----------------------------- Menu ----------------------------->
                                case 6 -> {
                                    System.out.println("\u001B[32m1\u001B[0m" + " - Add Account Details");
                                    System.out.println("\u001B[32m2\u001B[0m" + " - Edit Account Details");
                                    System.out.println("\u001B[32m3\u001B[0m" + " - See Account Details");

                                    System.out.print("\n\u001B[34mEnter the Option[1, 2, 3] : \u001B[0m");

                                    if (!sc.hasNextInt()) {
                                        System.out.println(
                                                "\u001B[31mInvalid input! Please enter a number (1-3).\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }

                                    int optionn = sc.nextInt();
                                    System.out.println("");

                                    switch (optionn) {

                                        case 1 -> {
                                            if (!reg.containsKey(oldAccNo)) {
                                                System.out.println("Enter Account Details : \n");

                                                CheckUserDetails check = new CheckUserDetails();

                                                String name;
                                                while (true) {
                                                    System.out.print("\n\u001B[34mEnter Your Name : \u001B[0m");
                                                    name = sc.next();
                                                    sc.nextLine();

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
                                                    pn = sc.nextLine();

                                                    boolean isPhoneNumber = check.checkPhoneNumber(pn);

                                                    if (isPhoneNumber) {
                                                        System.out.println("\u001B[32mPhone Number verified\u001B[0m");
                                                        break;
                                                    } else {
                                                        System.out.println("\u001B[31mTry again!\u001B[0m");
                                                    }
                                                }

                                                UserDetails details = new UserDetails(oldAccNo, name, email, pn);
                                                reg.put(oldAccNo, details);

                                                System.out.println(
                                                        "\n\u001B[32mAccounts details added Successfully!\u001B[0m\n");

                                            } else {
                                                System.out.println(
                                                        "\u001B[31mAccounts details already added!\u001B[0m\n");
                                            }
                                        }

                                        // <----------------------------- Edit Details ----------------------------->
                                        case 2 -> {
                                            if (reg.containsKey(oldAccNo)) {
                                                UserDetails ud = reg.get(oldAccNo);
                                                System.out.println("\u001B[31mEditing Account Details : \u001B[0m\n");

                                                System.out.println("\u001B[32m1\u001B[0m" + " - Edit Name");
                                                System.out.println("\u001B[32m2\u001B[0m" + " - Edit Email");
                                                System.out.println("\u001B[32m3\u001B[0m" + " - Phone Number");

                                                System.out.print("\n\u001B[34mEnter the Option [1, 2, 3]: \u001B[0m");

                                                if (!sc.hasNextInt()) {
                                                    System.out.println(
                                                            "\u001B[31mInvalid input! Please enter a number (1-2).\u001B[0m\n");
                                                    sc.next();
                                                    continue;
                                                }

                                                int opti = sc.nextInt();
                                                sc.nextLine();

                                                CheckUserDetails check = new CheckUserDetails();
                                                switch (opti) {
                                                    case 1 -> {
                                                        String newName;
                                                        while (true) {
                                                            System.out.print("\n\u001B[34mEnter Your Name : \u001B[0m");
                                                            newName = sc.next();
                                                            sc.nextLine();

                                                            boolean isName = check.checkName(newName);

                                                            if (isName) {
                                                                System.out.println(
                                                                        "\u001B[32mName Verified!\u001B[0m");
                                                                break;
                                                            } else {
                                                                System.out.println("\u001B[31mTry again!\u001B[0m");
                                                            }
                                                        }
                                                        ud.setName(newName);
                                                        System.out.println(
                                                                "\n\u001B[32mName updated Successfully!\u001B[0m\n");
                                                    }

                                                    case 2 -> {
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
                                                        ud.setEmail(newEmail);
                                                        System.out.println(
                                                                "\n\u001B[32mEmail updated Successfully!\u001B[0m\n");
                                                    }

                                                    case 3 -> {
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
                                                        ud.setPhoneNo(newPhoneNumber);
                                                        System.out.println(
                                                                "\n\u001B[32mPhone Number updated Successfully!\u001B[0m\n");
                                                    }

                                                    default ->
                                                        System.out.println("\u001B[31mInvalid option!\u001B[0m");
                                                }
                                            } else {
                                                System.out.println("\u001B[31mYou didn't add your details!\u001B[0m\n");
                                            }
                                        }
                                        // <----------------------------- See details ----------------------------->
                                        case 3 -> {
                                            if (reg.containsKey(oldAccNo)) {
                                                System.out.println(
                                                        "\u001B[34m<----------------- Your Account Details ----------------->\u001B[0m\n");
                                                System.out.println(reg.get(oldAccNo));
                                                System.out.println(
                                                        "\n\u001B[34m<--------------- X X X X X X X X X X X ---------------->\u001B[0m");
                                            } else {
                                                System.out.println("\u001B[31mYou Didn't Add your details\u001B[0m\n");
                                            }
                                        }
                                        default ->
                                            System.out.println("\u001B[31mInvalid Option!\u001B[0m\n");
                                    }
                                }
                                // <----------------------------- Money Transfer ----------------------------->
                                case 7 -> {
                                    sc.nextLine();
                                    String passCheck = null;
                                    boolean authenti = false;
                                    for (int i = 0; i < 3; i++) {
                                        System.out.print("\nEnter the Password : ");
                                        passCheck = sc.next();
                                        if (existUser.getSecurityKey().equals(passCheck)) {
                                            authenti = true;
                                            break;
                                        } else {
                                            System.out.println("\u001B[31mWrong Password! Attempts left: " + (2 - i)
                                                    + "\u001B[0m");
                                        }
                                    }
                                    if (!authenti) {
                                        System.out.println(
                                                "\n\u001B[31mToo many failed attempts. Returning to menu.\u001B[m\n");
                                        break;
                                    }

                                    if (existUser.getSecurityKey().equals(passCheck)) {
                                        System.out.println("\n\u001B[32mCorrect Password\u001B[0m\n");

                                        int transAccNo;

                                        while (true) {
                                            System.out.print("\u001B[34mEnter Account number to transfer : \u001B[0m");

                                            if (sc.hasNextInt()) {
                                                transAccNo = sc.nextInt();

                                                if (transAccNo != oldAccNo && accounts.containsKey(transAccNo)) {
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            "\u001B[31mInvalid Account Number! Please try again.\u001B[0m\n");
                                                }

                                            } else {
                                                System.out.println(
                                                        "\u001B[31mInvalid input! Please enter numbers only.\u001B[0m\n");
                                                sc.next();
                                            }
                                        }

                                        if (transAccNo != oldAccNo && accounts.containsKey(transAccNo)) {

                                            double transMoney = 0;

                                            while (true) {
                                                sc.nextLine();
                                                System.out.print("\n\u001B[34mEnter the money : \u001B[0m\n");

                                                if (sc.hasNextDouble()) {
                                                    transMoney = sc.nextDouble();
                                                    if (transMoney > 0) {
                                                        break;
                                                    } else {
                                                        System.out.println(
                                                                "\u001B[31mInsufficient Amount! Please enter again.\u001B[0m");
                                                    }
                                                } else {
                                                    System.out.println(
                                                            "\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m");
                                                    sc.next();
                                                }
                                            }

                                            if (existUser.getBalance() >= transMoney) {

                                                UserService transfer = accounts.get(oldAccNo);
                                                transfer.tWithdraw(transAccNo, transMoney);

                                                System.out.println(".\n.\n.\n");

                                                UserService transferService = accounts.get(transAccNo);
                                                transferService.tDeposit(oldAccNo, transAccNo, transMoney);

                                                System.out.println();

                                            } else {
                                                System.out.println(
                                                        "\n\u001B[31mYou don't have that much amount\u001B[0m\n");
                                            }
                                        }
                                    } else {
                                        System.out.println("\n\u001B[31mIncorrect Password\u001B[0m\n");
                                    }

                                }

                                // <----------------------------- Logout ----------------------------->
                                case 8 -> {
                                    System.out.println("\n\u001B[31mYour Account has been Logged Out!\u001B[0m\n");
                                    x = false;
                                }

                                default ->
                                    System.out.println("\u001B[31mInvalid Option!\u001B[0m");
                            }
                        }
                    }

                    // <----------------------------- Exit App ----------------------------->
                    case 3 -> {
                        System.out.println("\u001B[33m\n\t\t\t\tExit!\u001B[0m");
                        y = false;
                    }

                    default ->
                        System.out.println("\u001B[31mInvalid Option!\u001B[0m");
                }
            }
        }
    }
}
