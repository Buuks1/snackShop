import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Simulation {
    public static void main(String[] args) { // main method
        try { // tries to find files using pathways provided and catches error if file not found
            File customerFile = new File ("customers.txt");
            File snackFile = new File ("snacks.txt");
            File transactionFile = new File ("transactions.txt");
            
            // initialises the shop and simulates the transactions in transactions.txt
            SnackShop shop = initialiseShop("Bukunmi's Corner Shop", snackFile, customerFile);
            simulateShopping(shop, transactionFile);


        } catch (Exception e) {
        System.out.println("A " + e + " error has occured while simulating");
        }

    }
    /* Initialise shop method that takes in shopName, snackFile and customer File
    * reads the files and uses the files to add customers and snacks to snackshop */
    public static SnackShop initialiseShop(String shopName, File snackFile, File customerFile) throws Exception {
        SnackShop shop = new SnackShop(shopName);

        //reads snackfile
        try (BufferedReader br = new BufferedReader(new FileReader(snackFile))) {
            String line;

            //categories the values of the snacks
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("@");
                String snackID = parts[0];
                String name = parts[1];

                if (snackID.startsWith("F")) {
                    String hotCold = parts[2];
                    int basePrice = Integer.parseInt(parts[3]);
                    shop.addSnacks(new Food(snackID, name, basePrice, hotCold));
                } else {
                    Drink.SugarContent sugarContent = Drink.SugarContent.valueOf(parts[2].toUpperCase());
                    int basePrice = Integer.parseInt(parts[3]);
                    shop.addSnacks(new Drink(snackID, name, basePrice, sugarContent));
                }
            }
        }

        //reads customerFile
        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String line;

            //categories the values of the customers
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                String customerID = parts[0];
                String customerName = parts[1];
                int accountBal = Integer.parseInt(parts[2]);
                if (parts.length == 3) {
                    shop.addCustomer(new Customer(customerID, customerName, accountBal));
                } else if (parts.length == 4) {
                    String customerType = parts[3];
                    if (customerType.equals("STUDENT")) {
                        shop.addCustomer(new StudentCustomer(customerID, customerName, accountBal));
                    } else if (customerType.equals("STAFF")) {
                        StaffCustomer.Schools schools = StaffCustomer.Schools.OTHER;
                        shop.addCustomer(new StaffCustomer(customerID, customerName, accountBal, schools));
                    }
                } else if(parts.length == 5) {
                    StaffCustomer.Schools schools = StaffCustomer.Schools.valueOf(parts[4]);
                    shop.addCustomer(new StaffCustomer(customerID, customerName, accountBal, schools));
                } else {
                    System.out.println("There are too many values associated with this customer");
                }
            }
        }
        return shop;
    }

    // Simulates transactions from the transactionFile
    public static void simulateShopping(SnackShop shop, File transactionFile) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String action = parts[0];
                switch (action) {
                    // Processes the purchases if the first value is "PURCHASE"
                    case "PURCHASE":
                        String accountID = parts[1];
                        String snackID = parts[2];
                        try {
                            Customer customer = shop.getCustomer(accountID);
                            Snack snacks = shop.getSnack(snackID);
                            int accountBal = customer.getAccountBal();
                            int basePrice = snacks.getBasePrice();
                            shop.processPurchase(parts[1], parts[2]);
                            System.out.println( accountID + " has officially purchased " + snackID);
                         } catch (Exception e) {
                            System.out.println("Failed to process purchase for CustomerID " + accountID + " " + e.getMessage());
                        }
                        break;

                    // creates a new customer if the first value is "NEW_CUSTOMER"
                    case "NEW_CUSTOMER":
                        accountID = parts[1];
                        String customerName = parts[2];
                        try {
                            if (parts.length == 4){
                                int accountBal = Integer.parseInt(parts[3]);
                                shop.addCustomer(new Customer(accountID, customerName, accountBal));
                            } else if (parts.length == 5) {
                                String customerType = parts[3];
                                if (customerType.equals("STAFF")) {
                                    StaffCustomer.Schools schools = StaffCustomer.Schools.OTHER;
                                    int accountBal = Integer.parseInt(parts[4]);
                                    shop.addCustomer(new StaffCustomer(accountID, customerName, accountBal, schools));
                                } else if (customerType.equals("STUDENT")) {
                                    int accountBal = Integer.parseInt(parts[4]);
                                    shop.addCustomer(new StudentCustomer(accountID, customerName, accountBal));
                                }
                            } else {
                                String customerType = parts[3];
                                if (customerType.equals("STAFF")) {
                                    StaffCustomer.Schools schools = StaffCustomer.Schools.valueOf(parts[4]);
                                    int accountBal = Integer.parseInt(parts[5]);
                                    shop.addCustomer(new StaffCustomer(accountID, customerName, accountBal, schools));
                                }
                            }
                            System.out.println(customerName + " has been added to the Customer List");
                        } catch (Exception e) {
                            System.out.println(customerName + " was unable to be added to the Customer List" + e.getMessage());
                        }
                        break;

                    // adds funds to account if the first value is "ADD_FUNDS"
                    case "ADD_FUNDS":
                        accountID = parts[1];
                        int amount = Integer.parseInt(parts[2]);
                        try {
                            Customer customer = shop.getCustomer(accountID);
                        customer.addFunds(amount);
                        System.out.println(amount + " has been added to " + accountID);
                        } catch (Exception e) {
                            System.out.println("Unable to add " + amount + " to " + accountID);
                        }
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + action);
                }
            }
        }

        // uses accessors to get the methods to be displayed
        int largestBasePrice = shop.findLargestBasePrice();
        int numberOfNegativeBalances = shop.countNegativeAccounts();
        int medianAccountNumber = shop.calculateMedianCustomerBalance();
        int turnover = shop.getTurnover();

        // prints out the results of all the methods
        System.out.println("The largest Base Price is " + largestBasePrice);
        System.out.println("There are " + numberOfNegativeBalances + " accounts with a negative balance");
        System.out.println("The median account number is " + medianAccountNumber);
        System.out.println("Shop Turnover: " + turnover);

    }
}
