// Class for staff customers
class StaffCustomer extends Customer {
    public enum Schools{CMP,MTH,BIO,OTHER} // Enum to store the possible values for the different schools
    private Schools schools;
    // Constructor to store values of staff customers
    public StaffCustomer(String accountID, String customerName, int accountBal, Schools schools) throws InvalidCustomerException, InsufficientBalanceException {
        super(accountID,customerName, accountBal);
        this.schools = schools;

    }
    // constructor for staff customers
    public StaffCustomer(String accountID, String customerName, Schools schools) throws InvalidCustomerException, InsufficientBalanceException {
        super (accountID,customerName,0);
    }

    //method to charge staff account after applying staff discount
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException {
        double discountPercent = 0;
        switch (schools) {
            case CMP:
                discountPercent = 0.10; // 10% discount
                break;
            case BIO:
                discountPercent = 0.02; // 2% discount
                break;
            case MTH:
                discountPercent = 0.02; // 2% discount
                break;
            case OTHER:
                discountPercent = 0; // No discount
                break;
        }
        int discountAmount = (int) (Math.ceil(snackPrice * discountPercent)); // Apply and round discount
        int finalPrice = (int) (Math.ceil(snackPrice - discountAmount)); // Final price after discount
        if (accountBal >= finalPrice) {
            accountBal -= finalPrice; // Charge the customer
            return finalPrice;
        } else {
            throw new InsufficientBalanceException("Insufficient funds in account");
        }
    }
    public Schools getSchool() { // accessor for school
        return schools;
    }
    @Override
    public String toString() { // toString for staff customer
        return "Staff " + super.toString() + ", School: " + schools;
    }
}