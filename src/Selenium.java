import java.util.Random;
import java.util.Scanner;

public class Selenium {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Input number between 1-3:" + "\n" + "Number 1 - create natural person," + "\n" + "Number 2 - create sole proprietorship," + "\n" + "Number 3 - create company." + "\n" + "Type 'r' for random.");

        Random r = new Random();
        int number = 0;
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        if (userInput.equals("r") || userInput.equals("R")) {
            System.out.println("Generating random client...");
            number = r.nextInt(3) + 1;
        }

        if (number == 1 || userInput.equals("1")) {
            System.out.println("The number is: 1. Generating Natural Person...");
            Natural natural = new Natural();
            natural.run();
        } else if (number == 2 || userInput.equals("2")) {
            System.out.println("The number is: 2. Generating Sole Proprietorship...");
            Sole sole = new Sole();
            sole.run();
        } else if (number == 3 || userInput.equals("3")) {
            System.out.println("The number is: 3. Generating Company...");
            Company company = new Company();
            company.run();
        } else {
            System.out.println("Wrong client type. Terminating...");
            Thread.currentThread().interrupt();
        }
    }
}
