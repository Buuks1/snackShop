// Creating a class for customers
class Customer {
    public String accountID;
    public String customerName;
    public int accountBal;

    //constructor for customer class
    public Customer (String accountID, String customerName) throws InvalidCustomerException, InsufficientBalanceException {
        this(accountID,customerName, 0);
    }
    // Constructor that takes values of customers and saves it
    public Customer (String accountID, String customerName, int accountBal) throws InvalidCustomerException, InsufficientBalanceException{
        if (!accountID.matches("\\w{6}")) { // checks if customerID is valid and accountBal is over 0
            throw new InvalidCustomerException("The account ID is invalid");
        }

        if (accountBal < 0 ) {
            throw new InsufficientBalanceException("This customer has £0.00");
        }
        this.accountID = accountID;
        this.customerName = customerName;
        this.accountBal = accountBal;
    }

    public String getAccountID() { // accessor for AccountID
        return accountID;
    }

    public String getCustomerName() { // accessor for CustomerName
        return customerName;
    }

    public int getAccountBal() { // accessor for AccountBal
        return accountBal;
    }

    public void addFunds(int amount) { // method for adding funds to accountBal
        if (amount > 0 ) {
            accountBal += amount;
        }

    }

    public int chargeAccount (int snackPrice) throws InsufficientBalanceException { // method for charging account to normal customer accountBal
        if (accountBal >= snackPrice) {
            accountBal -= snackPrice;
            return snackPrice;
        } else {
            throw new InsufficientBalanceException("Insufficient funds in account");
        }
    }

    // toString method for normal customers
    @Override
    public String toString() {
        return "Customer ID: " + accountID + ", Name: " + customerName + ", Balance: " + accountBal;
    }
}

