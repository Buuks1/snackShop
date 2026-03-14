class Food extends Snack { // extended class for foods
    private boolean hotCold;
    public final int surchargePercent = 10;

    public Food(String snackID, String name, int basePrice, String hotCold) throws InvalidSnackException { // constructor that saves the foods and their values
        super(snackID, name, basePrice);
        if (!snackID.startsWith("F")) { // checks if snackID is valid and throws exception if not
            throw new InvalidSnackException("All foods must start with F");
        }

        // checks if food snack is hot or cold and categorises it accordingly
        if (hotCold.equalsIgnoreCase("hot")) {
            this.hotCold = true;
        } else if (hotCold.equalsIgnoreCase("cold")) {
            this.hotCold = false;
        } else {
            throw new InvalidSnackException("Invalid hot/cold status" + hotCold);
        }

    }

    public void setHotCold(boolean hotCold) { // mutator that sets hotCold to what ever boolean is entered
        this.hotCold = hotCold;
    }

    @Override
    public int calculatePrice() { // method to apply surcharge if food is hot
        int price = getBasePrice();
        if (hotCold) {
            price += (price * surchargePercent/100);
        }
        int roundedCalculatedPrice = (int) Math.ceil(price);
        return roundedCalculatedPrice; // rounding price up
    }

    // toString for the food class
    @Override
    public String toString() {
        return "Food: " + super.toString() + ", hot: " + hotCold;
    }
}