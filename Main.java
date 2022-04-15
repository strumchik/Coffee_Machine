package machine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);
        machine.mainMenu();
        while (machine.isActive()) {
            machine.readInput(scanner.nextLine());
            if (!machine.isActive()) break;
        }
    }
}
