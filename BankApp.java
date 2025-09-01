import java.util.*;

public class BankApp {

    public static void main(String[] args) throws Throwable {
        try (Scanner sc = new Scanner(System.in)) {
            CheckUserDetails check = new CheckUserDetails();
            CheckAccountDetails cad = new CheckAccountDetails();

            JdbcUserDetails jdbcUD = new JdbcUserDetails();
            JdbcUserService jdbcUS = new JdbcUserService();
            JdbcServiceHistory jdbcSH = new JdbcServiceHistory();

            System.out.println(Colors.yellow + "=================================" + Colors.reset);
            System.out.println(Colors.yellow +"\t Banking System " + Colors.reset);
            System.out.println(Colors.yellow + "=================================" + Colors.reset);
            boolean y = true;
            while (y) {
                System.out.println(Colors.yellow + "---------------------------------" + Colors.reset);
                
                System.out.println(Colors.green + 1 + Colors.reset + " - Register Account");
                System.out.println(Colors.green + 2 + Colors.reset + " - Login");
                System.out.println(Colors.red + 3 + Colors.reset + " - Exit App");
                
                System.out.println(Colors.yellow + "---------------------------------" + Colors.reset);

                System.out.print(Colors.blue + "\nEnter the Option[1, 2, 3] : " + Colors.reset);

                if (!sc.hasNextInt()) {
                    System.out.println(Colors.red + "Invalid input! Please enter a number (1-3).\n"+ Colors.reset);
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
                            System.out.println(Colors.red + "Account Already Exist!\n" + Colors.reset);
                            break;
                        }
                        System.out.print("\nEnter the Password : ");
                        sc.nextLine();
                        String pass = sc.nextLine();

                        double amount = 0;
                        while (true) {
                            System.out.print(Colors.green + "\nEnter the Initial Deposit Amount : " + Colors.reset);
                            if (sc.hasNextDouble()) {
                                amount = sc.nextDouble();
                                sc.nextLine();
                                if (amount >= 0)
                                    break;
                                System.out.println(Colors.red + "Amount cannot be negative! Please enter again." + Colors.reset);
                            } else {
                                System.out.println(Colors.red + "Invalid Amount! Please enter a valid Amount." + Colors.reset);
                                sc.next();
                            }
                        }

                        UserService NewUser = new UserService();
                        NewUser.setAccNo(NewAccNo);
                        NewUser.setAmount(amount);
                        NewUser.changeSecurityKey(pass);

                        jdbcUS.insertSRecords(NewUser);

                        System.out.print(Colors.cyan + "\nDo you want to Add your Account Details Now? " + Colors.reset);
                        System.out.print(Colors.blue + "\nFor Yes - [Y] Or Press Any key to continue : " + Colors.reset);
                        char yOrN = sc.next().charAt(0);

                        if ((yOrN == 'y' || yOrN == 'Y') && jdbcUD.checkAccNo(NewAccNo)) {
                            System.out.println(Colors.green + "\nEnter Account Details : " + Colors.reset);
                            sc.nextLine();

                            CheckAccountDetails.Helper helper = cad.checkAD(sc);
                            JdbcUserDetails.insertRecords(new UserDetails(NewAccNo, helper.name, helper.email, helper.phoneNumber));

                            jdbcUS.updateActive(NewAccNo);

                            System.out.println(Colors.green + "\nAccount details Successfully Added!" + Colors.reset);

                        } else {
                            System.out.println(Colors.cyan + "---> You can Enter the Account details after login, in the Menu!" + Colors.reset);
                        }

                        System.out.println(Colors.yellow + "\nAccount Created!" + Colors.reset);
                        System.out.println(Colors.cyan + "---> You have to login to continue.\n" + Colors.reset);
                    }
                    // <-------------------------------------------------- Login ------------------------------------------------------------>
                    case 2 -> {
                        int oldAccNo = check.checkAccNumber(sc);
                        if (oldAccNo < 1) break;
                        if (!jdbcUS.checkSAcNo(oldAccNo)) {
                            System.out.println(Colors.red + "No Such Account Exist!\n" + Colors.reset);
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
                                System.out.println(Colors.red + "Wrong Password! Attempts left: " + (2 - i) + Colors.reset);
                            }
                        }
                        if (!authenticated) {
                            System.out.println(Colors.red + "\nToo many failed attempts. Returning to menu.\n" + Colors.reset);
                            break;
                        }
                        System.out.println(Colors.green + "\n------->Login Successful!\n" + Colors.reset);

                        boolean x = true;
                        while (x) {
                            System.out.println(Colors.green + 1 + Colors.reset + " - Change Password");
                            System.out.println(Colors.green + 2 + Colors.reset + " - Deposit Amount");
                            System.out.println(Colors.green + 3 + Colors.reset + " - Withdraw Amount");
                            System.out.println(Colors.green + 4 + Colors.reset + " - Check Balance");
                            System.out.println(Colors.green + 5 + Colors.reset + " - Transaction History");
                            System.out.println(Colors.green + 6 + Colors.reset + " - Menu");
                            System.out.println(Colors.green + 7 + Colors.reset + " - Money Transfer");
                            System.out.println(Colors.red + 8 + " - Logout" + Colors.reset);
                            System.out.print(Colors.blue +"\nEnter the Option[1, 2, 3, 4, 5, 6, 7, 8] : " + Colors.reset);

                            if (!sc.hasNextInt()) {
                                System.out.println(Colors.red + "Invalid input! Please enter a number (1-8).\n" + Colors.reset);
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
                                        System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
                                        break;
                                    }
                                    System.out.print("\nType the Previous Password : ");
                                    String prePass = sc.nextLine();

                                    if (jdbcUS.getSPass(oldAccNo).equals(prePass)) {

                                        System.out.print(Colors.green + "\nSet New Password : " + Colors.reset);
                                        String newPass = sc.nextLine();
                                        jdbcUS.updatePass(oldAccNo, newPass);

                                        System.out.println(Colors.green + "Password Saved!\n" + Colors.reset);

                                    } else {
                                        System.out.println(Colors.red + "Incorrect Password!\n" + Colors.reset);
                                    }
                                }
                                // <----------------------------- Deposit ----------------------------->
                                case 2 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
                                        break;
                                    }
                                    System.out.print("Enter the deposit amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println(Colors.red + "Invalid Amount! Please enter a valid Amount.\n" + Colors.reset);
                                        sc.next();
                                        continue;
                                    }
                                    double transMoney = sc.nextDouble();
                                    if(transMoney < 0) {
                                        System.out.println(Colors.red + "Amount cannot be negative! Please enter again.\n" + Colors.reset);
                                        break;
                                    } else if(transMoney == 0) {
                                        System.out.println(Colors.red + "You cannot deposit zero amount! Please enter again.\n" + Colors.reset);
                                        break;
                                    }
                                    jdbcUS.updateBalance(1, oldAccNo, transMoney);

                                    jdbcSH.insertSRecords(oldAccNo, "Deposit", transMoney, jdbcUS.getSBalance(oldAccNo), null);

                                    System.out.println(Colors.green + "Deposited Successfully!\n" + Colors.reset);
                                }
                                // <----------------------------- Withdraw ----------------------------->
                                case 3 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
                                        break;
                                    }
                                    System.out.print("Enter the withdrawal amount : ");

                                    if (!sc.hasNextDouble()) {
                                        System.out.println(Colors.red + "Invalid Amount! Please enter a valid Amount.\n" + Colors.reset);
                                        sc.next();
                                        continue;
                                    }
                                    double transMoney = sc.nextDouble();
                                    if(transMoney < 0) {
                                        System.out.println(Colors.red + "Amount cannot be negative! Please enter again.\n" + Colors.reset);
                                        break;
                                    } else if(transMoney == 0) {
                                        System.out.println(Colors.red + "You cannot deposit zero amount! Please enter again.\n" + Colors.reset);
                                        break;
                                    }
                                    jdbcUS.updateBalance(2, oldAccNo, transMoney);

                                    jdbcSH.insertSRecords(oldAccNo, "withdraw", transMoney, jdbcUS.getSBalance(oldAccNo), null);
                                    System.out.println(Colors.green + "Withdrawn Successfully!\n" + Colors.reset);
                                }
                                // <----------------------------- Get Balance ----------------------------->
                                case 4 -> {
                                    System.out.println("Your Bank Balance is " + Colors.green +  "Rs. "+ jdbcUS.getSBalance(oldAccNo) + Colors.reset + "\n");
                                }
                                // <----------------------------- Get Statement ----------------------------->
                                case 5 -> {
                                   if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
                                        break;
                                    }
                                    JdbcServiceHistory.readHistory(oldAccNo);
                                }
                                // <----------------------------- Menu ----------------------------->
                                case 6 -> {

                                    System.out.println(Colors.green + 1 + Colors.reset +  " - Add Account Details");
                                    System.out.println(Colors.green + 2 + Colors.reset + " - Edit Account Details");
                                    System.out.println(Colors.green + 3 + Colors.reset + " - See Account Details\n");

                                    System.out.print(Colors.blue + "Enter the Option[1, 2, 3] : \n" + Colors.reset);

                                    if (!sc.hasNextInt()) {
                                        System.out.println(Colors.red + "Invalid input! Please enter a number (1-3).\n" + Colors.reset);
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
                                                System.out.println(Colors.green + "\nAccounts details added Successfully!\n" + Colors.reset);

                                            } else {
                                                System.out.println(Colors.red + "Accounts details already added!\n" + Colors.reset);
                                            }
                                        }
                                        // <----------------------------- Edit Details ----------------------------->
                                        case 2 -> {
                                            if (!jdbcUS.checkSisActive(oldAccNo)) {
                                                System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
                                                break;
                                            }
                                            System.out.println(Colors.blue + "Editing Account Details : \n" + Colors.reset);

                                            System.out.println(Colors.green + 1 + Colors.reset +  " - Edit Name");
                                            System.out.println(Colors.green + 2 + Colors.reset + " - Edit Email");
                                            System.out.println(Colors.green + 3 + Colors.reset + " - Phone Number");

                                            System.out.print(Colors.blue + "Enter the Option [1, 2, 3]: " + Colors.reset);

                                            if (!sc.hasNextInt()) {
                                                System.out.println(Colors.red +"Invalid input! Please enter a number (1-2).n" + Colors.reset);
                                                sc.next();
                                                continue;
                                            }

                                            int opti = sc.nextInt();
                                            sc.nextLine();
                                            switch (opti) {
                                                case 1 -> jdbcUD.updateName(oldAccNo, cad.checkName(sc));                                                  

                                                case 2 -> jdbcUD.updateEmail(oldAccNo, cad.checkEmail(sc));
                                            
                                                case 3 -> jdbcUD.updatePhoneNumber(oldAccNo, cad.checkPhoneNumber(sc));
                                                
                                                default -> System.out.println(Colors.red +"Invalid option!n" + Colors.reset);
                                            }
                                        }
                                        // <----------------------------- See details ----------------------------->
                                        case 3 -> {
                                            if (!jdbcUD.checkAccNo(oldAccNo)) {
                                                System.out.println(Colors.yellow +"<----------------- Your Account Details ----------------->\n" + Colors.reset);
                                                JdbcUserDetails.readRecords(oldAccNo);
                                                System.out.println(Colors.yellow +"\n<--------------- X X X X X X X X X X X ---------------->\n" + Colors.reset);

                                            } else System.out.println(Colors.red + "You Didn't Add your details\n" + Colors.reset);
                                        }
                                        default -> System.out.println(Colors.red + "Invalid Option!\n" + Colors.reset);
                                    }
                                }
                                // <----------------------------- Money Transfer ----------------------------->
                                case 7 -> {
                                    if (!jdbcUS.checkSisActive(oldAccNo)) {
                                        System.out.println(Colors.red + "You need to add your account details first to activate your account!\n" + Colors.reset);
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
                                        } else System.out.println(Colors.red +"Wrong Password! Attempts left: " + (2 - i) + Colors.reset);
                                        
                                    }
                                    if (!authenti) {
                                        System.out.println(Colors.red + "\nToo many failed attempts. Returning to menu.\n" + Colors.reset);
                                        break;
                                    }
                                    if (jdbcUS.getSPass(oldAccNo).equals(passCheck)) {
                                        System.out.println(Colors.green +"\nCorrect Password\n" + Colors.reset);

                                        int transAccNo;
                                        while (true) {
                                            System.out.print(Colors.blue +"Enter Account number to transfer : " + Colors.reset);
                                            if (sc.hasNextInt()) {
                                                transAccNo = sc.nextInt();

                                                if (transAccNo != oldAccNo && jdbcUS.checkSAcNo(transAccNo)) break;
                                                else System.out.println(Colors.red + "Invalid Account Number! Please try again.\n" + Colors.reset);
                                            } else {
                                                System.out.println(Colors.red + "Invalid input! Please enter numbers only.\n" + Colors.reset);
                                                sc.next();
                                            }
                                        }
                                        if (transAccNo != oldAccNo && jdbcUS.checkSAcNo(transAccNo)) {
                                            double transMoney = 0;
                                            while (true) {
                                                sc.nextLine();
                                                System.out.print(Colors.blue +"\nEnter the money : \n" + Colors.reset);
                                                if (sc.hasNextDouble()) {
                                                    transMoney = sc.nextDouble();
                                                    if (transMoney > 0) break;
                                                    else System.out.println(Colors.red + "Insufficient Amount! Please enter again." + Colors.reset);
                                                } else {
                                                    System.out.println(Colors.red + "Invalid Amount! Please enter a valid Amount." + Colors.reset);
                                                    sc.next();
                                                }
                                            }
                                            if (jdbcUS.getSBalance(oldAccNo) >= transMoney) {

                                                jdbcUS.updateBalance(1, transAccNo, transMoney);
                                                System.out.println(".\n.\n.\n");
                                                jdbcUS.updateBalance(2, oldAccNo, transMoney);

                                                System.out.println(Colors.yellow +"Transaction Successful!" + Colors.reset + Colors.green +"Rs. " + transMoney
                                                             + Colors.reset  + " transferred to Account Number"+ Colors.green +  "#AccNo "
                                                                + transAccNo + Colors.reset + "\n");

                                                jdbcSH.insertSRecords(oldAccNo, "Transferred", transMoney,
                                                        jdbcUS.getSBalance(oldAccNo), transAccNo);   
                                                
                                                jdbcSH.insertSRecords(transAccNo, "Received", transMoney,
                                                        jdbcUS.getSBalance(transAccNo), oldAccNo);   

                                            } else System.out.println(Colors.red +"\nYou don't have that much amount!\n" + Colors.reset);
                                        }
                                    } else System.out.println(Colors.red +"\nIncorrect Password\n" + Colors.reset);

                                }
                                // <----------------------------- Logout ----------------------------->
                                case 8 -> {
                                    System.out.println(Colors.red +"\nYour Account has been Logged Out!\n" + Colors.reset);
                                    x = false;
                                }

                                default -> System.out.println(Colors.red +"Invalid Option!" + Colors.reset);
                            }
                        }
                    }

                    // <----------------------------- Exit App ----------------------------->
                    case 3 -> {
                        System.out.println(Colors.red +"\n\t\t\t\tExit!\n" + Colors.reset);
                        y = false;
                    }

                    // case 69 -> JdbcUserDetails.readAllRecords();

                    default -> System.out.println(Colors.red +"Invalid Option!" + Colors.reset);
                }
            }
        }
    }
}
