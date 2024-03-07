/**
 * CustomerInterface.java
 * @author Your name
 * @author Partner's name
 * CIS 22C, Applied Lab 4
 */
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerInterface {
	public static void main(String[] args) throws IOException {
        final int NUM_MUTUAL_FUNDS = 7;
        final int NUM_CUSTOMERS = 100;
        HashTable<MutualFund> funds = new HashTable<>(NUM_MUTUAL_FUNDS * 2);
        HashTable<Customer> customers = new HashTable<>(NUM_CUSTOMERS);

        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        loadMutualFunds(funds);
        loadCustomerFile("customers.txt", customers, funds);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Mutual Fund InvestorTrack (TM)!\n");

        System.out.print("Please enter your email address: ");
        String userEmail = scanner.next();
        System.out.print("Please enter your password: \n");
        String userPassword = scanner.next();

        Customer loggedInCustomer = null;
        Customer tempCustomer = new Customer(userEmail, userPassword);
        
        if (!customers.contains(new Customer(userEmail, userPassword))) {
            System.out.println("We don't have your account on file...\n");
            System.out.println("Let's create an account for you!");
            System.out.print("Enter your first name: ");
            String firstName = scanner.next();
            System.out.print("Enter your last name: ");
            String lastName = scanner.next();

            loggedInCustomer = new Customer(firstName, lastName, userEmail, userPassword);
            customers.add(loggedInCustomer);
            System.out.println("\nWelcome, " + firstName + " " + lastName + "!\n\n");
        } else {
            loggedInCustomer = customers.get(tempCustomer);
            System.out.println("\nWelcome, " + loggedInCustomer.getFirstName() + " " + loggedInCustomer.getLastName() + "!");
        }
        
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.next().toUpperCase();
            switch (choice) {
                case "A":
                	purchaseFund(funds, loggedInCustomer, scanner);
                    break;
                case "B":
                	sellFund(loggedInCustomer, scanner);
                    break;
                case "C":
                	addCash(loggedInCustomer, scanner);
                    break;
                case "D":
                	displayCurrentFunds(loggedInCustomer, scanner);
                    break;
                case "X":
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
                    break;
            }
        }

        scanner.close();
    }

	private static void loadCustomerFile(String fileName, HashTable<Customer> customers, HashTable<MutualFund> funds) {
        File file = new File(fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] fullName = fileScanner.nextLine().split(" ", 2);
                String firstName = fullName[0];
                String lastName = fullName.length > 1 ? fullName[1] : ""; 
                String email = fileScanner.nextLine();
                String password = fileScanner.nextLine();
                double cash = Double.parseDouble(fileScanner.nextLine());
                int numFunds = Integer.parseInt(fileScanner.nextLine());
                ArrayList<MutualFundAccount> mutualFundAccounts = new ArrayList<>();
                for (int i = 0; i < numFunds; i++) {
                    String ticker = fileScanner.nextLine();
                    double numShares = Double.parseDouble(fileScanner.nextLine());

                    MutualFundAccount account = new MutualFundAccount(funds.get(new MutualFund(ticker)), numShares);
                    mutualFundAccounts.add(account);
                }

                Customer customer = new Customer(firstName, lastName, email, password, cash, mutualFundAccounts);
                customers.add(customer);
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File '" + fileName + "' not found.");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number from the file: " + e.getMessage());
        }
    }
    
    private static void loadMutualFunds(HashTable<MutualFund> funds) {
        File file = new File("mutual_funds.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String fundName = fileScanner.nextLine(); 
                String ticker = fileScanner.nextLine();
                double pricePerShare = Double.parseDouble(fileScanner.nextLine());
                double tradingFee = Double.parseDouble(fileScanner.nextLine());

                MutualFund mutualFund = new MutualFund(fundName, ticker, pricePerShare, tradingFee);
                funds.add(mutualFund);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'mutual_funds.txt' not found.");
        }
    }

    
    private static void displayMainMenu() {
		System.out.println("Please select from the following options:\n");
		System.out.println("A. Purchase a Fund");
		System.out.println("B. Sell a Fund");
		System.out.println("C. Add Cash");
		System.out.println("D. Display Your Current Funds");
		System.out.println("X. Exit\n");
		System.out.print("Enter your choice: ");
	}
    
    private static void purchaseFund(HashTable<MutualFund> funds, Customer loggedInCustomer, Scanner scanner) {
        if (funds == null) {
            System.out.println("There are no funds available for purchase at this time.");
            return;
        }
        
        System.out.println("\nPlease select from the options below:\n");
        System.out.println(funds.toString());

        System.out.print("Enter the ticker of the fund to purchase: ");
        String ticker = scanner.next().toUpperCase();

        MutualFund selectedFund = funds.get(new MutualFund(ticker));
        if (selectedFund == null) {
            System.out.println("Fund not found. Please enter a valid ticker.");
            return;
        }

        System.out.print("Enter the number of shares to purchase: ");
        double numShares = scanner.nextDouble();

        if (numShares <= 0) {
            System.out.println("The number of shares must be greater than 0. Please try again.");
            return;
        } 
        
        
        boolean purchaseSuccess = loggedInCustomer.addFund(numShares, selectedFund);
        
        if (purchaseSuccess) {
            System.out.println("You successfully added shares of the following fund: ");
            System.out.println(selectedFund.toString());
            System.out.println("Number of shares added: " + numShares);
        } else {
            System.out.println("\nYou don't have enough cash to purchase that fund.\r\n" + "Please add cash to make a purchase\n");
        }
    }

    
    private static void sellFund(Customer loggedInCustomer, Scanner scanner) {
        DecimalFormat df = new DecimalFormat("###,##0.00");

        if (!loggedInCustomer.hasOpenAccounts()) {
            System.out.println("\nYou don't have any funds to sell at this time.\n");
            return;
        }

        System.out.println("You own the following mutual funds:\n");
        loggedInCustomer.printAccountsByName();

        System.out.print("Enter the name of the fund to sell: ");
        scanner.nextLine();
        String fundName = scanner.nextLine(); 

        MutualFundAccount accountToSell = loggedInCustomer.getAccountByName(fundName);
        if (accountToSell == null) {
            System.out.println("Sorry you don't own an account by that name.");
        } else {
            System.out.print("Enter the number of shares to sell or \"all\" to sell everything: ");
            if (!scanner.hasNextInt()) {
                scanner.next(); 
                loggedInCustomer.sellFund(fundName);
            } else {
                int sharesToSell = scanner.nextInt();
                if (sharesToSell > 0) {
                    loggedInCustomer.sellShares(fundName, sharesToSell); 
                } else {
                    System.out.println("The number of shares must be greater than 0.");
                    return;
                }
            }
            System.out.println("You own the following funds:");
            loggedInCustomer.printAccountsByName();
            System.out.println("Your current cash balance is $" + df.format(loggedInCustomer.getCash()));
        }
    }


    
    private static void displayCurrentFunds(Customer loggedInCustomer, Scanner scanner) {
        if (!loggedInCustomer.hasOpenAccounts()) {
            System.out.println("\nYou don't have any funds to display at this time.\n");
            return;
        }

        System.out.println("\nView Your Mutual Funds By:");
        System.out.println("1. Name");
        System.out.println("2. Value");
        System.out.print("\nEnter your choice (1 or 2): ");
        String choice = scanner.next();
        System.out.println();

        switch (choice) {
            case "1":
                loggedInCustomer.printAccountsByName();
                break;
            case "2":
            	System.out.println("deg 1");
                loggedInCustomer.printAccountsByValue();
                break;
            default:
                System.out.println("Invalid Choice!");
                break;
        }
    }
    
    
    private static void addCash(Customer loggedInCustomer, Scanner scanner) {
        System.out.println("Your current cash balance is $" + String.format("%.2f", loggedInCustomer.getCash()));
        System.out.print("Enter the amount of cash to add: $");
        double amount = Double.parseDouble(scanner.next());
        loggedInCustomer.updateCash(amount);
        System.out.println("Your current cash balance is $" + String.format("%.2f", loggedInCustomer.getCash()));
    }

}
