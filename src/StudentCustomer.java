// Class for Students
class StudentCustomer extends Customer {
    private static final double STUDENT_DISCOUNT = 0.05;
    private static final int MAX_NEGATIVE_BALANCE = -500;

    // Constructor for Student customers
    public StudentCustomer(String accountID, String customerName) throws InvalidCustomerException, InsufficientBalanceException {
        super(accountID,customerName);
    }
    // Constructor to store values for student customers
    public StudentCustomer(String accountID, String customerName, int accountBal) throws InvalidCustomerException, InsufficientBalanceException {
        super(accountID,customerName,accountBal);
    }

    // Method to charge account if student has enough money (even if in overdraft)
    @Override
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException {
        double discount = snackPrice * STUDENT_DISCOUNT; // Calculate discount
        int discountedPrice = (int) Math.ceil(snackPrice - discount); // Round to ensure positive integer
        if (accountBal - discountedPrice >= MAX_NEGATIVE_BALANCE) { // Check against negative balance limit
            accountBal -= discountedPrice;
            return discountedPrice;
        } else {
            throw new InsufficientBalanceException("Insufficient funds in account");
        }
    }

    public static double getDiscountAmount() { // accessor for DiscountAmount
        return STUDENT_DISCOUNT;
    }
    public String toString() { // toString for student customers
        return "Student " + super.toString();
    }
}