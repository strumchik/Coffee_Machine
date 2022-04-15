package machine;

enum state {
    MENU, BUY, WATER, MILK, BEANS, CAPS, OFF
}

enum TextsOfMachine {
    MAKING_COFFEE("I have enough resources, making you a coffee!"),
    NO_BEANS("Sorry, not enough beans!"),
    NO_MILK("Sorry, not enough milk!"),
    NO_WATER("Sorry, not enough water!"),
    MENU("\nWrite action (buy, fill, take, remaining, exit):"),
    COFFEE_OPTIONS("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"),
    ASK_FOR_WATER_AMOUNT("Write how many ml of water you want to add:"),
    ASK_FOR_MILK_AMOUNT("Write how many ml of milk you want to add:"),
    ASK_FOR_BEANS_AMOUNT("Write how many grams of coffee beans you want to add:"),
    ASK_FOR_CUPS_AMOUNT("Write how many disposable cups of coffee you want to add:"),
    CURRENCY("$"),
    USER_COMMAND_BACK("back"),
    I_GAVE("I gave you "),
    MACHINE_HAS("The coffee machine has:\n"),
    ML_OF_WATER(" ml of water\n"),
    ML_OF_MILK(" ml of milk\n"),
    G_OF_BEANS(" g of coffee beans\n"),
    CUPS(" disposable cups\n"),
    OF_MONEY(" of money");

    static final String user_command_buy = "buy";
    static final String user_command_fill = "fill";
    static final String user_command_take = "take";
    static final String user_command_remaining = "remaining";
    static final String user_command_exit = "exit";

    final String text;
    TextsOfMachine(String text){
        this.text = text;
    }
}

public class CoffeeMachine {
    private int amountOfWater = 400;
    private int amountOfMilk = 540;
    private int amountOfBeans = 120;
    private int amountOfCups = 9;
    private int money = 550;
    state curState;

    private void showInfo() {
        System.out.println(TextsOfMachine.MACHINE_HAS.text +
                this.amountOfWater + TextsOfMachine.ML_OF_WATER.text +
                this.amountOfMilk + TextsOfMachine.ML_OF_MILK.text +
                this.amountOfBeans + TextsOfMachine.G_OF_BEANS.text +
                this.amountOfCups + TextsOfMachine.CUPS.text +
                TextsOfMachine.CURRENCY.text + this.money + TextsOfMachine.OF_MONEY.text);
    }

    private void sell(String nextLine) {
        if (nextLine.equals(TextsOfMachine.USER_COMMAND_BACK.text)) {
            return;
        }
        Coffee order =  Coffee.values()[Integer.parseInt(nextLine) - 1];
        if (this.amountOfWater >= order.water) {
            if (this.amountOfMilk >= order.milk) {
                if (this.amountOfBeans >= order.beans) {
                    System.out.println(TextsOfMachine.MAKING_COFFEE.text);
                    this.amountOfWater -= order.water;
                    this.amountOfMilk -= order.milk;
                    this.amountOfBeans -= order.beans;
                    this.money += order.price;
                    this.amountOfCups -= CoffeeType.getCups();
                } else {
                    System.out.println(TextsOfMachine.NO_BEANS.text);
                }
            } else {
                System.out.println(TextsOfMachine.NO_MILK.text);
            }
        } else {
            System.out.println(TextsOfMachine.NO_WATER.text);
        }
    }


    private void cashOut() {
        System.out.println(TextsOfMachine.I_GAVE.text + this.money + TextsOfMachine.CURRENCY.text);
        this.money = 0;
    }

    private void menu(String command) {
        switch (command) {
            case TextsOfMachine.user_command_buy:
                this.curState = state.BUY;
                System.out.println(TextsOfMachine.COFFEE_OPTIONS.text);
                return;
            case TextsOfMachine.user_command_fill:
                this.curState = state.WATER;
                System.out.println(TextsOfMachine.ASK_FOR_WATER_AMOUNT.text);
                return;
            case TextsOfMachine.user_command_take:
                this.cashOut();
                mainMenu();
                return;
            case TextsOfMachine.user_command_remaining:
                this.showInfo();
                mainMenu();
                return;
            case TextsOfMachine.user_command_exit:
                this.curState = state.OFF;
                return;
            default:
                mainMenu();
        }
    }

    public void mainMenu() {
        this.curState = state.MENU;
        System.out.println(TextsOfMachine.MENU.text);
    }

    public CoffeeMachine() {
        this.curState = state.MENU;
    }

    public boolean isActive() {
        return !this.curState.equals(state.OFF);
    }


    public void readInput(String nextLine) {
        switch (curState) {
            case MENU:
                menu(nextLine);
                return;
            case BUY:
                this.sell(nextLine);
                mainMenu();
                return;
            case WATER:
                this.amountOfWater += Integer.parseInt(nextLine);
                this.curState = state.MILK;
                System.out.println(TextsOfMachine.ASK_FOR_MILK_AMOUNT.text);
                return;
            case MILK:
                this.amountOfMilk += Integer.parseInt(nextLine);
                this.curState = state.BEANS;
                System.out.println(TextsOfMachine.ASK_FOR_BEANS_AMOUNT.text);
                return;
            case BEANS:
                this.amountOfBeans += Integer.parseInt(nextLine);
                this.curState = state.CAPS;
                System.out.println(TextsOfMachine.ASK_FOR_CUPS_AMOUNT);
                return;
            case CAPS:
                this.amountOfCups += Integer.parseInt(nextLine);
                mainMenu();
                return;
            default:
                mainMenu();
        }
    }
}
