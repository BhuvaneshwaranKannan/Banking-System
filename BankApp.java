import java.util.*;

public class BankApp {

    public static void main(String[] args) throws Throwable {
        try (Scanner sc = new Scanner(System.in)) {
            CheckUserDetails check = new CheckUserDetails();
            CheckAccountDetails cad = new CheckAccountDetails();

            JdbcUserDetails jdbcUD = new JdbcUserDetails();
            JdbcUserService jdbcUS = new JdbcUserService();
            JdbcServiceHistory jdbcSH = new JdbcServiceHistory();

            System.out.println("\u001B[33m=================================\u001B[0m");
            System.out.println("\u001B[33m\t Banking System \u001B[0m");
            System.out.println("\u001B[33m=================================\u001B[0m");
            boolean y = true;
            while (y) {
                System.out.println("\u001B[33m---------------------------------\u001B[0m");

                System.out.println("\u001b[32m1\u001b[0m - Register Account");
                System.out.println("\u001B[32m2\u001B[0m - Login");
                System.out.println("\u001b[31m3\u001b[0m - Exit App");

                System.out.println("\u001B[33m---------------------------------\u001B[0m");

                System.out.print("\n\u001B[34mEnter the Option[1, 2, 3] : \u001B[0m");

                if (!sc.hasNextInt()) {
                    System.out.println("\u001B[31mInvalid input! Please enter a number (1-3).\u001B[0m\n");
                    sc.next();
                    continue;
                }

                int opt = sc.nextInt();
                sc.nextLine();
                System.out.println("");
                switch (opt) {
                    // <-------------------------------------------------- Register ------------------------------------------------------------>
                    case 1 -> {
                        int NewAccNo = check.checkAccNumber(sc);
                        if (NewAccNo < 1)
                            break;

                        if (jdbcUS.checkSAcNo(NewAccNo)) {
                            System.out.println("\u001B[31mAccount Already Exist!\u001B[0m\n");
                            break;
                        }
                        System.out.print("\nEnter the Password : ");
                        sc.nextLine();
                        String pass = sc.nextLine();

                        double amount = 0;
                        while (true) {
                            System.out.print("\n\u001B[32mEnter the Initial Deposit Amount : \u001B[0m");
                            if (sc.hasNextDouble()) {
                                amount = sc.nextDouble();
                                sc.nextLine();
                                if (amount >= 0)
                                    break;
                                System.out.println("\u001B[31mAmount cannot be negative! Please enter again.\u001B[0m");
                            } else {
                                System.out.println("\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m");
                                sc.next();
                            }
                        }

                        UserService NewUser = new UserService();
                        NewUser.setAccNo(NewAccNo);
                        NewUser.setAmount(amount);
                        NewUser.changeSecurityKey(pass);

                        jdbcUS.insertSRecords(NewUser);

                        System.out.print("\n\u001B[33mDo you want to Add your Account Details Now?\u001B[0m");
                        System.out.print("\n\u001B[34mFor Yes - [Y] Or Press Any key to continue : \u001B[0m");
                        char yOrN = sc.next().charAt(0);

                        if ((yOrN == 'y' || yOrN == 'Y') && jdbcUD.checkAccNo(NewAccNo)) {
                            System.out.println("\n\u001B[32mEnter Account Details : \u001B[0m");
                            sc.nextLine();

                            CheckAccountDetails.Helper helper = cad.checkAD(sc);
                            JdbcUserDetails.insertRecords(new UserDetails(NewAccNo, helper.name, helper.email, helper.phoneNumber));

                            jdbcUS.updateActive(NewAccNo);

                            System.out.println("\n\u001B[32mAccount details Successfully Added\u001B[0m");

                        } else {
                            System.out.println("\u001B[36m---> You can Enter the Account details after login, in the Menu!\u001B[0m");
                        }

                        System.out.println("\n\u001B[33mAccount Created!\u001B[0m");
                        System.out.println("\u001B[36m---> You have to login to continue.\u001B[0m\n");
                    }
                    // <-------------------------------------------------- Login ------------------------------------------------------------>
                    case 2 -> {
                        int oldAccNo = check.checkAccNumber(sc);
                        if (oldAccNo < 1) break;
                        if (!jdbcUS.checkSAcNo(oldAccNo)) {
                            System.out.println("\u001B[31mNo Such Account Exist!\u001B[0m\n");
                            break;
                        }

                        String inputPass;
                        boolean authenticated = false;
                        for (int i = 0; i < 3; i++) {
                            System.out.print("\nEnter the Password : ");
                            inputPass = sc.next();
                            if (jdbcUS.getSPass(oldAccNo).equals(inputPass)) {
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
                            sc.nextLine();
                            System.out.println("");

                            switch (option) {
                                // <----------------------------- change Password ----------------------------->
                                case 1 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println("\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                        break;
                                    }
                                    System.out.print("\nType the Previous Password : ");
                                    String prePass = sc.nextLine();

                                    if (jdbcUS.getSPass(oldAccNo).equals(prePass)) {

                                        System.out.print("\nSet New Password : ");
                                        String newPass = sc.nextLine();
                                        jdbcUS.updatePass(oldAccNo, newPass);

                                        System.out.println("\u001B[32mPassword Saved!\u001B[0m\n");

                                    } else {
                                        System.out.println("\u001B[31mIncorrect Password!\u001B[0m");
                                        System.out.println();
                                    }
                                }
                                // <----------------------------- Deposit ----------------------------->
                                case 2 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println("\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                        break;
                                    }
                                    System.out.print("Enter the deposit amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println("\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }
                                    double transMoney = sc.nextDouble();
                                    if(transMoney < 0) {
                                        System.out.println("\u001B[31mAmount cannot be negative! Please enter again.\u001B[0m\n");
                                        break;
                                    } else if(transMoney == 0) {
                                        System.out.println("\u001B[31mYou cannot deposit zero amount! Please enter again.\u001B[0m\n");
                                        break;
                                    }
                                    jdbcUS.updateBalance(1, oldAccNo, transMoney);

                                    jdbcSH.insertSRecords(oldAccNo, "Deposit", transMoney, jdbcUS.getSBalance(oldAccNo), null);

                                    System.out.println("Deposited Successfully!\n");
                                }
                                // <----------------------------- Withdraw ----------------------------->
                                case 3 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println(
                                                "\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                        break;
                                    }
                                    System.out.print("Enter the withdrawal amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println(
                                                "\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }
                                    double transMoney = sc.nextDouble();
                                    if(transMoney < 0) {
                                        System.out.println("\u001B[31mAmount cannot be negative! Please enter again.\u001B[0m\n");
                                        break;
                                    } else if(transMoney == 0) {
                                        System.out.println("\u001B[31mYou cannot deposit zero amount! Please enter again.\u001B[0m\n");
                                        break;
                                    }
                                    jdbcUS.updateBalance(2, oldAccNo, transMoney);

                                    jdbcSH.insertSRecords(oldAccNo, "withdraw", transMoney, jdbcUS.getSBalance(oldAccNo), null);
                                    System.out.println("Withdrawn Successfully!\n");
                                }
                                // <----------------------------- Get Balance ----------------------------->
                                case 4 -> {
                                    System.out.println("\u001B[32mYour Bank Balance is \u001B[0m" + "Rs. "+ jdbcUS.getSBalance(oldAccNo) + "\n");
                                }
                                // <----------------------------- Get Statement ----------------------------->
                                case 5 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println( "\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                        break;
                                    }
                                    JdbcServiceHistory.readHistory(oldAccNo);
                                }
                                // <----------------------------- Menu ----------------------------->
                                case 6 -> {

                                    System.out.println("\u001B[32m1\u001B[0m" + " - Add Account Details");
                                    System.out.println("\u001B[32m2\u001B[0m" + " - Edit Account Details");
                                    System.out.println("\u001B[32m3\u001B[0m" + " - See Account Details");

                                    System.out.print("\n\u001B[34mEnter the Option[1, 2, 3] : \u001B[0m");

                                    if (!sc.hasNextInt()) {
                                        System.out.println("\u001B[31mInvalid input! Please enter a number (1-3).\u001B[0m\n");
                                        sc.next();
                                        continue;
                                    }
                                    int optionn = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("");

                                    switch (optionn) {
                                        case 1 -> {
                                            if (jdbcUD.checkAccNo(oldAccNo)) {
                                                System.out.println("Enter Account Details : ");

                                                CheckAccountDetails.Helper helper = cad.checkAD(sc);

                                                JdbcUserDetails.insertRecords(new UserDetails(oldAccNo, helper.name,helper.email, helper.phoneNumber));
                                                jdbcUS.updateActive(oldAccNo);
                                                System.out.println("\n\u001B[32mAccounts details added Successfully!\u001B[0m\n");

                                            } else {
                                                System.out.println("\u001B[31mAccounts details already added!\u001B[0m\n");
                                            }
                                        }
                                        // <----------------------------- Edit Details ----------------------------->
                                        case 2 -> {
                                            if (!jdbcUS.checkSisActive(oldAccNo)) {
                                                System.out.println("\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                                break;
                                            }
                                            System.out.println("\u001B[31mEditing Account Details : \u001B[0m\n");

                                            System.out.println("\u001B[32m1\u001B[0m" + " - Edit Name");
                                            System.out.println("\u001B[32m2\u001B[0m" + " - Edit Email");
                                            System.out.println("\u001B[32m3\u001B[0m" + " - Phone Number");

                                            System.out.print("\n\u001B[34mEnter the Option [1, 2, 3]: \u001B[0m");

                                            if (!sc.hasNextInt()) {
                                                System.out.println("\u001B[31mInvalid input! Please enter a number (1-2).\u001B[0m\n");
                                                sc.next();
                                                continue;
                                            }

                                            int opti = sc.nextInt();
                                            sc.nextLine();
                                            switch (opti) {
                                                case 1 -> jdbcUD.updateName(oldAccNo, cad.checkName(sc));                                                  

                                                case 2 -> jdbcUD.updateEmail(oldAccNo, cad.checkEmail(sc));
                                            
                                                case 3 -> jdbcUD.updatePhoneNumber(oldAccNo, cad.checkPhoneNumber(sc));
                                                
                                                default -> System.out.println("\u001B[31mInvalid option!\u001B[0m");
                                            }
                                        }
                                        // <----------------------------- See details ----------------------------->
                                        case 3 -> {
                                            if (!jdbcUD.checkAccNo(oldAccNo)) {
                                                System.out.println("\u001B[33m<----------------- Your Account Details ----------------->\u001B[0m\n");
                                                JdbcUserDetails.readRecords(oldAccNo);
                                                System.out.println("\n\u001B[33m<--------------- X X X X X X X X X X X ---------------->\u001B[0m\n");

                                            } else System.out.println("\u001B[31mYou Didn't Add your details\u001B[0m\n");
                                        }
                                        default -> System.out.println("\u001B[31mInvalid Option!\u001B[0m\n");
                                    }
                                }
                                // <----------------------------- Money Transfer ----------------------------->
                                case 7 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println("\u001B[31mYou need to add your account details first to activate your account!\u001B[0m\n");
                                        break;
                                    }
                                    String passCheck = null;
                                    boolean authenti = false;
                                    for (int i = 0; i < 3; i++) {
                                        System.out.print("\nEnter the Password : ");
                                        passCheck = sc.next();
                                        if (jdbcUS.getSPass(oldAccNo).equals(passCheck)) {
                                            authenti = true;
                                            break;
                                        } else System.out.println("\u001B[31mWrong Password! Attempts left: " + (2 - i) + "\u001B[0m");
                                        
                                    }
                                    if (!authenti) {
                                        System.out.println("\n\u001B[31mToo many failed attempts. Returning to menu.\u001B[m\n");
                                        break;
                                    }
                                    if (jdbcUS.getSPass(oldAccNo).equals(passCheck)) {
                                        System.out.println("\n\u001B[32mCorrect Password\u001B[0m\n");

                                        int transAccNo;
                                        while (true) {
                                            System.out.print("\u001B[34mEnter Account number to transfer : \u001B[0m");
                                            if (sc.hasNextInt()) {
                                                transAccNo = sc.nextInt();

                                                if (transAccNo != oldAccNo && jdbcUS.checkSAcNo(transAccNo)) break;
                                                else System.out.println("\u001B[31mInvalid Account Number! Please try again.\u001B[0m\n");
                                            } else {
                                                System.out.println("\u001B[31mInvalid input! Please enter numbers only.\u001B[0m\n");
                                                sc.next();
                                            }
                                        }
                                        if (transAccNo != oldAccNo && jdbcUS.checkSAcNo(transAccNo)) {
                                            double transMoney = 0;
                                            while (true) {
                                                sc.nextLine();
                                                System.out.print("\n\u001B[34mEnter the money : \u001B[0m\n");
                                                if (sc.hasNextDouble()) {
                                                    transMoney = sc.nextDouble();
                                                    if (transMoney > 0) break;
                                                    else System.out.println("\u001B[31mInsufficient Amount! Please enter again.\u001B[0m");
                                                } else {
                                                    System.out.println("\u001B[31mInvalid Amount! Please enter a valid Amount.\u001B[0m");
                                                    sc.next();
                                                }
                                            }
                                            if (jdbcUS.getSBalance(oldAccNo) >= transMoney) {

                                                jdbcUS.updateBalance(1, transAccNo, transMoney);
                                                System.out.println(".\n.\n.\n");
                                                jdbcUS.updateBalance(2, oldAccNo, transMoney);

                                                System.out.println("\u001B[32mTransaction Successful! Rs. " + transMoney
                                                                + " transferred to Account Number #AccNo "
                                                                + transAccNo + "\u001B[0m\n");

                                                jdbcSH.insertSRecords(oldAccNo, "Transferred", transMoney,
                                                        jdbcUS.getSBalance(oldAccNo), transAccNo);   
                                                
                                                jdbcSH.insertSRecords(transAccNo, "Received", transMoney,
                                                        jdbcUS.getSBalance(transAccNo), oldAccNo);   

                                            } else System.out.println("\n\u001B[31mYou don't have that much amount\u001B[0m\n");
                                        }
                                    } else System.out.println("\n\u001B[31mIncorrect Password\u001B[0m\n");

                                }
                                // <----------------------------- Logout ----------------------------->
                                case 8 -> {
                                    System.out.println("\n\u001B[31mYour Account has been Logged Out!\u001B[0m\n");
                                    x = false;
                                }

                                default -> System.out.println("\u001B[31mInvalid Option!\u001B[0m");
                            }
                        }
                    }

                    // <----------------------------- Exit App ----------------------------->
                    case 3 -> {
                        System.out.println("\u001B[33m\n\t\t\t\tExit!\u001B[0m");
                        y = false;
                    }

                    // case 69 -> JdbcUserDetails.readAllRecords();

                    default -> System.out.println("\u001B[31mInvalid Option!\u001B[0m");
                }
            }
        }
    }
}
