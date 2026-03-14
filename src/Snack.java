abstract class Snack {
    /* Creating variables and placing them in the abstract class "Snack".
    These variables can be used in all the constructors and extended class within this .java
     */
    private String snackID;
    private String name;
    private int basePrice;

    public Snack(String snackID, String name, int basePrice) throws InvalidSnackException { // Constructor that saves the value of a snack
        if (!snackID.matches("[A-Z]/\\d{7}")) { // checks if the snackID & basePrice is valid and throws Exception if not
            throw new InvalidSnackException(snackID + " is a invalid snack ID");
        }
        if (basePrice < 0) {
            throw new InvalidSnackException("The base price can not be less than 1p");
        }

        this.snackID = snackID;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getSnackID() { // accessor to get SnackID
        return snackID;
    }
    public String getName() { // accessor to get Name
        return name;
    }
    public int getBasePrice() { // accessor to get basePrice
        return basePrice;
    }
    public abstract int calculatePrice();

    @Override
    public String toString() { // toString constructor for Snack abstract class
        return "Snack: " + name + ", ID: " + snackID + ", Base Price: " + basePrice;
    }
}

