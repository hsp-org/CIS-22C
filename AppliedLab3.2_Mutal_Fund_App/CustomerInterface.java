
/**
 * CustomerInterface.java
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Applied Lab 3
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CustomerInterface {

	public static void main(String[] args) {
		BST<MutualFundAccount> accountValue = new BST<>();
		BST<MutualFundAccount> accountName = new BST<>();
		LinkedList<MutualFund> funds = new LinkedList<>();

		loadMutualFunds(funds);
		
		
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		System.out.println("Welcome to Mutual Fund InvestorTrack (TM)!\n");

		while (running) {
			displayMainMenu();
			String choice = scanner.next().toUpperCase();

			switch (choice) {
			case "A":
				purchaseFund(funds, accountName, accountValue, scanner);
				break;
			case "B":
				sellFund(funds, accountName, accountValue, scanner);
				break;
			case "C":
				displayCurrentFunds(accountName, accountValue, scanner);
				break;
			case "X":
				System.out.println("\nGoodbye!");
				running = false;
				break;
			default:
				System.out.println("Invalid menu option. Please enter A-C or X to exit.");
				break;
			}
		}

        scanner.close();
	}

	private static void loadMutualFunds(LinkedList<MutualFund> funds) {
	    File file = new File("mutual_funds.txt");
	    try (Scanner fileScanner = new Scanner(file)) {
	        while (fileScanner.hasNextLine()) {
	            String fundName = "";
	            while (fileScanner.hasNextLine() && (fundName = fileScanner.nextLine()).isEmpty());
	            if (fundName.isEmpty()) {
	                break;
	            }
	            String ticker = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
	            if (fileScanner.hasNextDouble()) {
	                double pricePerShare = fileScanner.nextDouble();
	                if (!ticker.isEmpty() && pricePerShare >= 0) {
	                    MutualFund mutualFund = new MutualFund(fundName, ticker, pricePerShare);
	                    funds.addLast(mutualFund);
	                }
	            }
	            if (fileScanner.hasNext()) {
	                fileScanner.nextLine();
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("Error: File 'mutual_funds.txt' not found.");
	    }
	}

	
//	private static void loadMutualFunds(LinkedList<MutualFund> funds) {
//	    File file = new File("mutual_funds.txt");
//	    try (Scanner fileScanner = new Scanner(file)) {
//	        while (fileScanner.hasNextLine()) {
//	            System.out.println("Checking for next fund name...");
//	            String fundName = "";
//	            while (fileScanner.hasNextLine() && (fundName = fileScanner.nextLine()).isEmpty())
//	                ;
//	            if (fundName.isEmpty()) {
//	                System.out.println("End of file reached or empty line found. Breaking loop.");
//	                break;
//	            }
//	            System.out.println("Fund name found: " + fundName);
//
//	            String ticker = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
//	            System.out.println("Ticker found: " + ticker);
//
//	            if (!fileScanner.hasNextDouble()) {
//	                System.out.println("Expected price per share not found. Skipping to next line.");
//	                continue;
//	            }
//
//	            double pricePerShare = fileScanner.nextDouble();
//	            // Make sure to consume the newline after the double if your file structure requires it.
//	            if (fileScanner.hasNextLine()) fileScanner.nextLine(); 
//	            System.out.println("Price per share found: " + pricePerShare);
//
//	            if (!ticker.isEmpty() && pricePerShare > 0) {
//	                MutualFund mutualFund = new MutualFund(fundName, ticker, pricePerShare);
//	                funds.addLast(mutualFund);
//	                System.out.println("Mutual fund added: " + mutualFund.toString());
//	            } else {
//	                System.out.println("Invalid mutual fund data. Skipping...");
//	            }
//	        }
//	    } catch (FileNotFoundException e) {
//	        System.err.println("Error: File 'mutual_funds.txt' not found.");
//	    }
//	}


	private static void displayMainMenu() {
		System.out.println("Please select from the following options:\n");
		System.out.println("A. Purchase a Fund");
		System.out.println("B. Sell a Fund");
		System.out.println("C. Display Your Current Funds");
		System.out.println("X. Exit\n");
		System.out.print("Enter your choice: ");
	}

	private static void purchaseFund(LinkedList<MutualFund> funds, BST<MutualFundAccount> accountName,
			BST<MutualFundAccount> accountValue, Scanner scanner) {
		if (funds.isEmpty()) {
			System.out.println("There are no funds available for purchase at this time.");
			return;
		}

		System.out.println("\nPlease select from the options below:\n");
		funds.positionIterator();
		int index = 1;
		while (!funds.offEnd()) {
			MutualFund fund = funds.getIterator();
			String formattedSharedPrice = String.format("%.2f", fund.getPricePerShare());
			System.out.println(index + ". " + fund.getFundName() + "\n" + fund.getTicker() + "\nShare Price: $"
					+ formattedSharedPrice + "\n");
			funds.advanceIterator();
			index++;
		}

		System.out.print("Enter your choice: (1-" + (index - 1) + "): ");
		int choice = 0;
		while (true) {
			String temp = scanner.next();
			try {
				choice = Integer.parseInt(temp);
				if (choice >= 1 && choice <= funds.getLength()) {
					break;
				} else {
					System.out.print("Invalid choice. Please enter a number between 1 and " + (index - 1) + ": ");
				}
			} catch (NumberFormatException e) {
				System.out.print("Invalid input. Please enter a valid number: ");
			}
		}

		funds.positionIterator();
		for (int i = 1; i < choice; i++) {
			funds.advanceIterator();
		}
		MutualFund selectedFund = funds.getIterator();

		System.out.print("Enter the number of shares to purchase: ");
		double numShares = -1;
		while (true) {
			String numSharesStr = scanner.next();
			try {
				numShares = Double.parseDouble(numSharesStr);
				if (numShares > 0) {
					break;
				} else {
					System.out.print(
							"The number of shares must be greater than 0. Please enter a valid number of shares: ");
				}
			} catch (NumberFormatException e) {
				System.out.print("Invalid input. Please enter a valid number of shares: ");
			}
		}

		MutualFundAccount newAccount = new MutualFundAccount(selectedFund, numShares);
		accountName.insert(newAccount, new NameComparator());
		accountValue.insert(newAccount, new ValueComparator());
	}

	private static void sellFund(LinkedList<MutualFund> funds, BST<MutualFundAccount> accountName,
			BST<MutualFundAccount> accountValue, Scanner scanner) {
		if (accountName.isEmpty()) {
			System.out.println("\nYou don't have any funds to sell at this time.\n");
			return;
		}
		
		System.out.println("You own the following mutual funds: ");
		System.out.println(accountName.inOrderString());
		
		
		System.out.print("Enter the name of the fund to sell:");
		System.out.println();
		scanner.nextLine();
		String fundName = scanner.nextLine().trim();

		MutualFund fundToSell = helpSellFund(funds, fundName);
		if (fundToSell == null) {
			System.out.println("Fund not found: " + fundName);
			return;
		}
		MutualFundAccount searchAccount = new MutualFundAccount(fundToSell);

		MutualFundAccount accountInNameBST = accountName.search(searchAccount, new NameComparator());
		if (accountInNameBST == null) {
			System.out.println("You do not own any shares in: " + fundName);
			return;
		}

		System.out.println("Enter the number of shares to sell or \"all\" to sell everything:");
		String sharesInput = scanner.next();

		double sharesToSell;

		if (sharesInput.equalsIgnoreCase("all")) {
			sharesToSell = accountInNameBST.getNumShares();
		} else {
			try {
				sharesToSell = Double.parseDouble(sharesInput);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input for shares. Please enter a number or 'all'.");
				return;
			}
		}

		if (sharesToSell <= 0 || sharesToSell > accountInNameBST.getNumShares()) {
			System.out.println("Invalid number of shares to sell.");
			return;
		}

		if (sharesToSell < accountInNameBST.getNumShares()) {
			accountInNameBST.updateShares(-sharesToSell);
			accountName.remove(accountInNameBST, new NameComparator());
			accountValue.remove(accountInNameBST, new ValueComparator());
			accountName.insert(accountInNameBST, new NameComparator());
			accountValue.insert(accountInNameBST, new ValueComparator());
		} else {
			accountName.remove(accountInNameBST, new NameComparator());
			accountValue.remove(accountInNameBST, new ValueComparator());
		}
	}

	private static MutualFund helpSellFund(LinkedList<MutualFund> funds, String fundName) {
		funds.positionIterator();
		while (!funds.offEnd()) {
			MutualFund currentFund = funds.getIterator();
			if (currentFund.getFundName().equalsIgnoreCase(fundName)) {
				return currentFund;
			}
			funds.advanceIterator();
		}
		return null;
	}

	private static void displayCurrentFunds(BST<MutualFundAccount> accountName, BST<MutualFundAccount> accountValue,
			Scanner scanner) {
		if (accountName.isEmpty()) {
			System.out.println("\nYou don't have any funds to display at this time.\n");
			return;
		}

		System.out.println("\nView Your Mutual Funds By:");
		System.out.println("1. Name");
		System.out.println("2. Value");
		System.out.print("\nEnter your choice (1 or 2): ");
		String choice = scanner.next().trim();
		System.out.println();

		switch (choice) {
		case "1":
			System.out.println(accountName.inOrderString());
			break;
		case "2":
			System.out.println(accountValue.inOrderString());
			break;
		default:
			System.out.println("Invalid Choice!");
			break;
		}
	}

}