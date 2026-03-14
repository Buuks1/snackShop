// class for drinks
class Drink extends Snack {
    public enum SugarContent{HIGH, LOW, NONE} // creating an enum to store the possible values for sugarContent
    private SugarContent sugarContent;
    public Drink(String snackID, String name, int basePrice, SugarContent sugarContent) throws InvalidSnackException { // constructor for drinks that takes in all its values and storing it
        super(snackID, name, basePrice);
        if (!snackID.startsWith("D")) { // checks if snackID is invalid
            throw new InvalidSnackException("All drinks must start with D");
        }
        this.sugarContent = sugarContent;
    }

    public Drink(String snackID, String name, int basePrice) throws InvalidSnackException { // another constructor for drinks
        this(snackID, name, basePrice, SugarContent.NONE);
    }

    public SugarContent getSugarContent() { // accessor for SugarContent
        return sugarContent;
    }

    @Override
    public int calculatePrice() { // method to apply sugar tax to drink basePrice
        int price = getBasePrice();
        switch (sugarContent) {
            case HIGH:
                price += 24;
                break;
            case LOW:
                price += 18;
                break;
            case NONE:
                price += 0;
                break;
        }
        return (int) Math.ceil(price); // rounding price up
    }

    @Override
    public String toString() { // tostring for drink class
        return "Drink: " + super.toString() + ", Sugar Content: " + sugarContent;
    }
}