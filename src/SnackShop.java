import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// public class for Snack Shop
public class SnackShop {
    private String shopName;
    private int turnover;
    private Snack[] snacks;
    private Customer[] customers;
    private int snackCount;
    private int customerCount;

    // Constructor for Snack Shop
    public SnackShop(String shopName) {
        this.shopName = shopName;
        this.turnover = 0;
        this.snacks = new Snack[10];
        this.customers = new Customer[10];
        this.snackCount = 0;
        this.customerCount = 0;
    }
    // method to add customers to snackshop
    public void addCustomer (Customer customer) {
        if (customerCount == customers.length) {
            customers = Arrays.copyOf(customers, customers.length + 1);
        }
        customers[customerCount++] = customer;
    }

    // method to add snacks to snackshop
    public void addSnacks(Snack snack) {
        if (snackCount == snacks.length) {
            snacks = Arrays.copyOf(snacks, snacks.length + 1);
        }
        snacks[snackCount++] = snack;
    }

    public String getShopName() { // accessor for ShopName
        return shopName;
    }

    public int getTurnover() { // accessor for Turnover
        return turnover;
    }

    public void setShopName(String newName) { // mutator for ShopName
        this.shopName = newName;
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException { // accessor for Customer that uses customerID to search
        Customer outputCustomer = null;
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getAccountID().equals(customerID)) {
                outputCustomer = customers[i];
                return outputCustomer;
            }
        }
        throw new InvalidCustomerException("CustomerID: " + customerID + " could not be found");

    }

    public Snack getSnack(String snackID) throws InvalidSnackException { // accessor for snacks that uses snackID to search
        Snack outputSnack = null;
        for (int i = 0;i < snackCount; i++) {
            if (snacks[i].getSnackID().equals(snackID)) {
                outputSnack = snacks[i];
                return outputSnack;
            }
        }
        throw new InvalidSnackException("Snack ID: " + snackID + " could not be found");
    }

    //method that gets customerID and snack ID and reduces accountBal from snack price
    public boolean processPurchase(String customerID, String snackID) throws InvalidSnackException, InvalidCustomerException, InsufficientBalanceException{
        Customer customer = getCustomer(customerID);
        Snack snack = getSnack(snackID);
        int snackPrice = snack.calculatePrice();
        int payment = customer.chargeAccount(snackPrice);
        turnover += payment; // adds payments to turnover
        return true;

    }

    // method for finding largest basePrice
    public int findLargestBasePrice() {
        int largest = 0;
        for (int i = 0; i < snackCount; i++) {
            if (snacks[i].getBasePrice() > largest) {
                largest = snacks[i].getBasePrice();
            }
        }
        return largest;
    }

    // method for the count of negative accounts
    public int countNegativeAccounts() {
        int negativeAccounts = 0;
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getAccountBal() < 0) {
                negativeAccounts++;
            }
        }
        return negativeAccounts;
    }

    // method for the median customer balance
    public int calculateMedianCustomerBalance() {

        /* creates array and adds customer balances to it.
        *sorting it then finding the median */
        int[] balances = new int[customerCount];
        for (int i = 0; i < customerCount; i++) {
            balances[i] = customers[i].getAccountBal();
        }
        Arrays.sort(balances);
        int median;
        if (balances.length % 2 == 1) {
            median = balances[balances.length / 2];
        } else {
            int mid1 = balances.length / 2 - 1;
            int mid2 = balances.length / 2;
            median = (balances[mid1] + balances[mid2]) / 2;
        }

        return median;
    }

    // toString for SnackShop
    @Override
    public String toString() {
        return "SnackShop: " + shopName + ", Turnover: " + turnover;
    }
}
