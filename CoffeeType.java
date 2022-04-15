package machine;

class CoffeeType {
    private int water;
    private int milk;
    private int beans;
    private int price;
    private static int cups = 1;

    public CoffeeType(int waterInput, int milkInput, int beansInput, int priceInput) {
        water = waterInput;
        milk = milkInput;
        beans = beansInput;
        price = priceInput;
    }

    public int getWater() {
        return water;
    }

    public int getBeans() {
        return beans;
    }

    public static int getCups() {
        return cups;
    }

    public int getMilk() {
        return milk;
    }

    public int getPrice() {
        return price;
    }
}
