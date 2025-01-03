import java.util.*;
import java.util.stream.Collectors;

public class WalletSystemVersion2{ 
    // Using ArrayList to store dynamic data because the size of the list can change as needed.
    static List<Wallet> PERSONAL_WALLETS = new ArrayList<>();
    static List<Wallet> GROUP_WALLETS = new ArrayList<>();
    static List<Wallet> SAVINGS_WALLETS = new ArrayList<>();
    static List<Wallet> DEBTS_WALLETS = new ArrayList<>();
    static List<Transaction> TRANSACTIONS = new ArrayList<>();
    private static Scanner globalScanner = new Scanner(System.in);

    public static class Wallet {
        private String name;
        private String purpose;
    
        public Wallet(String name, String purpose) {
            this.name = name;
            this.purpose = purpose;
        }
    
        public String getName() {
            return name;
        }

        public String getPurpose() {
            return purpose;
        }
    }
    //This class will store details about a specific transactions.
    public static class Transaction {
        String walletType;
        String walletName;
        String contributor;
        String description;
        double amount;
        String date;
        String debtor;
        String creditor;

        public Transaction(String walletType, String walletName, String contributor, String description, double amount, String date, String debtor, String creditor) {
            this.walletType = walletType;
            this.walletName = walletName;
            this.contributor = contributor;
            this.description = description;
            this.amount = amount;
            this.date = date;
            this.debtor = debtor;
            this.creditor = creditor;
        }

        public String getWalletName() {
            return walletName;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }

        public String getContributor() {
            return contributor;
        }

        public String getDebtor() {
            return debtor;
        }

        public String getCreditor() {
            return creditor; 
        }
    }
    
    // This method retrieves a lis of wallets based on the specified wallet type. 
    static List<Wallet> getWallets(String walletType) {
        
        // use a switch case to determine which wallet list to return based on walletType.
        switch (walletType) {
            case "PERSONAL":
                return PERSONAL_WALLETS;
            case "GROUP":
                return GROUP_WALLETS;
            case "SAVINGS":
                return SAVINGS_WALLETS;
            case "DEBTS":
                return DEBTS_WALLETS;
            default:
                System.out.println("Invalid wallet type");
                return new ArrayList<>(); //Return an empty list to indicate no valid wallets are found.
        }
    }

    static List<Transaction> getTransactions(String walletType, String walletName) {
        return TRANSACTIONS.stream() //using stream to filter the list of transactions
            .filter(transaction -> transaction.walletType.equals(walletType))
            .filter(transaction -> transaction.walletName.equals(walletName))
            .collect(Collectors.toList()); //collect the filtered transactions into a new list
    }

    static void createWallet(String walletType) {
        List<Wallet> particularWallet = getWallets(walletType);
        globalScanner.nextLine();//clear the scanner to prevent input issue
        String name;
        int nameExist = 0; // to check if the wallet name already exist

        while(true){
            System.out.print("Enter Wallet Name: ");
            name = globalScanner.nextLine();
            
            // check if the entered name already exist in the lists of wallets.
            for(int i = 0; i < particularWallet.size(); i++){
                if(particularWallet.get(i).getName().contains(name)){
                    System.out.println("Wallet name already exist. Please try again.\n");
                    nameExist = 1;
                    break;
                } else {
                    nameExist = 0; //this resets the flag if the name is unique
                }
            }

            if(nameExist == 0) break; //exit the loop if the entered name is unique 
        }

        System.out.print("Enter Wallet Purpose: ");
        String purpose = globalScanner.nextLine();

        Wallet wallet = new Wallet(name, purpose);
        
        //will add the created wallet to the appopriate list based on the wallet type. 
        switch (walletType) {
            case "PERSONAL":
                PERSONAL_WALLETS.add(wallet);
                break;
            case "GROUP":
                GROUP_WALLETS.add(wallet);
                break;
            case "SAVINGS":
                SAVINGS_WALLETS.add(wallet);
                break;
            case "DEBTS":
                DEBTS_WALLETS.add(wallet);
                break;
            default:
                System.out.println("Invalid wallet type");
                return;
        }
        System.out.println("Wallet created successfully!");
    }

    static void createTransaction(String walletType) {
        List<Wallet> wallets = getWallets(walletType);
        
        //check if there are any wallets available for the specified type.
        if(!wallets.isEmpty()){
            //Display the available wallets for the given type. 
            System.out.println("\nAVAILABLE " + walletType + " WALLETS:");
            for (int i = 0; i < wallets.size(); i++) {
                System.out.println((i + 1) + " = " + wallets.get(i).getName() + " (" + wallets.get(i).getPurpose() + ")");
            }

            int creating = 1; //prevent transaction creation loop
            while(creating == 1) {
                globalScanner.nextLine(); //clear the scanner to prevent input issue

                //Display option for the user
                System.out.println("\n1. Enter Wallet ID#");
                System.out.println("2. Exit");
                System.out.print("Enter Action: ");
                int walletOption = globalScanner.nextInt();

                if (walletOption <= 2 && walletOption >= 1){ //validate user's input choice
                    if(walletOption == 1){
                        while(true) {
                            globalScanner.nextLine();
                            System.out.print("\nEnter ID#: ");
                            int walletID = globalScanner.nextInt();

                            if(walletID <= wallets.size() && walletID > 0) {
                                globalScanner.nextLine();
                                //prompt for transaction details 
                                System.out.print("Enter transaction amount: ");
                                double amount = globalScanner.nextDouble();
                                globalScanner.nextLine();
                        
                                System.out.print("Enter transaction date (e.g., YYYY-MM-DD): ");
                                String date = globalScanner.nextLine();

                                Transaction transaction;

                                //Handle transactions based on the wallet type. 
                                if(walletType == "PERSONAL"){
                                    System.out.print("Enter transaction description: ");
                                    String description = globalScanner.nextLine();
                                    transaction = new Transaction(walletType, wallets.get(walletID - 1).getName(), "", description, amount, date, "", "");
                                    TRANSACTIONS.add(transaction);
                                } else if(walletType == "GROUP"){
                                    System.out.print("Enter transaction description: ");
                                    String description = globalScanner.nextLine();

                                    System.out.print("Enter contributor: ");
                                    String contributor = globalScanner.nextLine();
                                    transaction = new Transaction(walletType, wallets.get(walletID - 1).getName(), contributor, description, amount, date, "","");
                                    TRANSACTIONS.add(transaction);
                                } else if (walletType == "SAVINGS") {
                                    transaction = new Transaction(walletType, wallets.get(walletID - 1).getName(), "", "", amount, date,"","" );
                                    TRANSACTIONS.add(transaction);
                                } else { //for DEBTS
                                    
                                    System.out.print("Is this a Receivable or Payable: ");
                                    System.out.print("1. Receivable ");
                                    System.out.print("2. Payable ");
                                    int recPay = globalScanner.nextInt();
                                    globalScanner.nextLine();
                                        if (recPay != 1 && recPay != 2) {
                                            System.out.println("Invalid. Please try again.");
                                            return;
                                        }
                                        String type = (recPay == 1) ? "Receivables" : "Payables"; 
                                            System.out.print("Enter Name (Debtor/Creditor): ");
                                            String name = globalScanner.nextLine();
                                            System.out.print("Enter Amount: ");
                                            double amountDebts = globalScanner.nextDouble();
                                            globalScanner.nextLine(); // Consume newline
                                            System.out.print("Enter Date (YYYY-MM-DD): ");
                                            String dateDebts = globalScanner.nextLine();

                                            transaction = new Transaction(walletType, wallets.get(walletID - 1).getName(), "", "",amountDebts, date, "", "" );
                                            TRANSACTIONS.add(transaction);
                                    
                                } 
                                    
                                
                                System.out.println("Transaction added successfully!");
                                break;
                            } else {
                                System.out.println("Invalid ID#. Please try again");
                            }
                        }
                    } else {
                        creating = 0; //exists the outer loop
                    }
                }
            }
        } else {
            System.out.println("No wallets yet.");
        }
    }

    public static void main(String[] args) {
        int runEngine = 1;

        while (runEngine == 1) {
            System.out.println("\u001B[32m" + "\n**** WALLET SYSTEM MAIN MENU ****" + "\u001B[0m");
            System.out.println("\nChoose Wallet Type:");
            System.out.println("1. Personal");
            System.out.println("2. Group");
            System.out.println("3. Savings");
            System.out.println("4. Debts");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int walletChoice = globalScanner.nextInt();

            //* choice must be 1 to 5 only */
            while (walletChoice <= 5 && walletChoice >= 1) {
                if(walletChoice == 1){
                    System.out.println("\nChoose Personal Actions:");
                    System.out.println("1. Create Wallet");
                    System.out.println("2. New Transaction");
                    System.out.println("3. View Transaction");
                    System.out.println("4. Exit");

                    System.out.print("Enter Action: ");
                    int personalChoice = globalScanner.nextInt();

                    while(personalChoice <= 4 && personalChoice >= 1) {
                        if(personalChoice == 1) {
                            createWallet("PERSONAL");
                            break;
                        } else if(personalChoice == 2) {
                            createTransaction("PERSONAL");
                            break;
                        } else if(personalChoice == 3) {
                            var wallets = getWallets("PERSONAL");
            
                            if(!wallets.isEmpty()){
                                System.out.println("\nPERSONAL TRANSACTIONS ***");                              

                                for(int i = 0; i < wallets.size(); i++){
                                    var transactions = getTransactions("PERSONAL", wallets.get(i).getName());
                                    double overallSum = 0;

                                    for(int b = 0; b < transactions.size(); b++){
                                        overallSum += transactions.get(b).getAmount();
                                    }

                                    System.out.println("\nWallet: " + wallets.get(i).getName() + " -- Overall Sum: " + overallSum);
                                    //Print Details 
                                    for(int b = 0; b < transactions.size(); b++){
                                        System.out.println("\nDescription: " + transactions.get(b).getDescription());
                                        System.out.println("Amount: " + transactions.get(b).getAmount());
                                        System.out.println("Date: " + transactions.get(b).getDate());
                                    }
                                }    
                            } else {
                                System.out.println("\nNo transactions yet.");
                            }
                            
                            break;
                        } else {
                            walletChoice = -1;
                            break;
                        }
                    }
                } else if(walletChoice == 2) {
                    System.out.println("\nChoose Group Actions:");
                    System.out.println("1. Create Wallet");
                    System.out.println("2. New Transaction");
                    System.out.println("3. View Transaction");
                    System.out.println("4. Exit");

                    System.out.print("Enter Action: ");
                    int groupChoice = globalScanner.nextInt();

                    while(groupChoice <= 4 && groupChoice >= 1) {
                        if(groupChoice == 1) {
                            createWallet("GROUP");
                            break;
                        } else if(groupChoice == 2) {
                            createTransaction("GROUP");
                            break;
                        } else if(groupChoice == 3) {
                            var wallets = getWallets("GROUP");
            
                            if(!wallets.isEmpty()){
                                System.out.println("\nGROUP TRANSACTIONS ***");                              

                                for(int i = 0; i < wallets.size(); i++){
                                    var transactions = getTransactions("GROUP", wallets.get(i).getName());
                                    double overallSum = 0;

                                    for(int b = 0; b < transactions.size(); b++){
                                        overallSum += transactions.get(b).getAmount();
                                    }

                                    System.out.println("\nWallet: " + wallets.get(i).getName() + " -- Overall Sum: " + overallSum);

                                    for(int b = 0; b < transactions.size(); b++){
                                        System.out.println("\nDescription: " + transactions.get(b).getDescription());
                                        System.out.println("Amount: " + transactions.get(b).getAmount());
                                        System.out.println("Date: " + transactions.get(b).getDate());
                                    }
                                }    
                            } else {
                                System.out.println("\nNo transactions yet.");
                            }
                            
                            break;
                        } else {
                            walletChoice = -1;
                            break;
                        }
                    }
                } else if(walletChoice == 3) {
                    System.out.println("\nChoose Savings Actions:");
                    System.out.println("1. Create Wallet");
                    System.out.println("2. New Transaction");
                    System.out.println("3. View Transaction");
                    System.out.println("4. Exit");

                    System.out.print("Enter Action: ");
                    int groupChoice = globalScanner.nextInt();

                    while(groupChoice <= 4 && groupChoice >= 1) {
                        if(groupChoice == 1) {
                            createWallet("SAVINGS");
                            break;
                        } else if(groupChoice == 2) {
                            createTransaction("SAVINGS");
                            break;
                        } else if(groupChoice == 3) {
                            var wallets = getWallets("SAVINGS");
            
                            if(!wallets.isEmpty()){
                                System.out.println("\nSAVINGS TRANSACTIONS ***");                              

                                for(int i = 0; i < wallets.size(); i++){
                                    var transactions = getTransactions("SAVINGS", wallets.get(i).getName());
                                    double overallSum = 0;

                                    for(int b = 0; b < transactions.size(); b++){
                                        overallSum += transactions.get(b).getAmount();
                                    }

                                    System.out.println("\nWallet: " + wallets.get(i).getName() + " -- Overall Sum: " + overallSum);

                                    for(int b = 0; b < transactions.size(); b++){
                                        System.out.println("Amount: " + transactions.get(b).getAmount() + "\n");
                                        System.out.println("Date: " + transactions.get(b).getDate());
                                    }
                                }    
                            } else {
                                System.out.println("\nNo transactions yet.");
                            }
                            
                            break;
                        } else {
                            walletChoice = -1;
                            break;
                        }
                    }
                } else if(walletChoice == 4) {
                    
                    System.out.println("\nChoose Debts Actions:");
                    System.out.println("1. Add Transactions");
                    System.out.println("2. View Transaction");
                    System.out.println("3. Exit");

                    System.out.print("Enter Action: ");
                    int debtChoice = globalScanner.nextInt();

                    while (debtChoice <= 4 && debtChoice >= 1) {
                        if (debtChoice == 1 ) {
                            createTransaction("DEBTS");
                            break;        
                        } else if (debtChoice == (2)) {
                            var wallets = getWallets("DEBTS");

                            if(!wallets.isEmpty()){
                                System.out.println("\nDEBTS TRANSACTIONS ***");                              

                                for(int i = 0; i < wallets.size(); i++){
                                    var transactions = getTransactions("DEBTS", wallets.get(i).getName());
                                    double overallSum = 0;

                                    for(int b = 0; b < transactions.size(); b++){
                                        overallSum += transactions.get(b).getAmount();
                                    }

                                    System.out.println("\nWallet: " + wallets.get(i).getName() + " -- Overall Sum: " + overallSum);

                                    for(int b = 0; b < transactions.size(); b++){
                                        System.out.println("Amount: " + transactions.get(b).getAmount() + "\n");
                                        System.out.println("Date: " + transactions.get(b).getDate());
                                    }
                                }    
                            } else {
                                System.out.println("\nNo transactions yet.");
                            }
                            break;
                        } else {
                            walletChoice = -1;
                            break;
                        }
                        }

                } else {
                    runEngine = 0;
                    break;
                }
            }
        }
    }
}