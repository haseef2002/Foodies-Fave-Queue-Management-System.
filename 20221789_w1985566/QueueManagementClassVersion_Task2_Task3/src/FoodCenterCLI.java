import exception.EmptyQueueException;
import exception.FullQueueException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodCenterCLI implements FoodCenterInterface {
    private FoodCenter foodCenter = new FoodCenter();

    @Override
    public void displayMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("---------------------Menu Choices-----------------------------------------------\n");
        menu.append(String.format("%-5s %-5s %s\n", "Code", "Menu", "Description"));
        menu.append(String.format("%-5s %-5s %s\n", "100", "VFQ", "View All Queues"));
        menu.append(String.format("%-5s %-5s %s\n", "101", "VEQ", "View All Empty Queues"));
        menu.append(String.format("%-5s %-5s %s\n", "102", "ACQ", "Add Customer to a Queue"));
        menu.append(String.format("%-5s %-5s %s\n", "103", "RCQ", "Remove a Customer from a Queue (from a specific location)"));
        menu.append(String.format("%-5s %-5s %s\n", "104", "PCQ", "Remove a Served Customer"));
        menu.append(String.format("%-5s %-5s %s\n", "105", "VCS", "View Customers Sorted in Alphabetical Order"));
        menu.append(String.format("%-5s %-5s %s\n", "106", "SPD", "Store Program Data into File"));
        menu.append(String.format("%-5s %-5s %s\n", "107", "LPD", "Load Program Data from File"));
        menu.append(String.format("%-5s %-5s %s\n", "108", "STK", "View Remaining Burger Stock"));
        menu.append(String.format("%-5s %-5s %s\n", "109", "AFS", "Add Burgers to Stock"));
        menu.append(String.format("%-5s %-5s %s\n", "110", "IFQ", "View que income"));
        menu.append(String.format("%-5s %-5s %s\n", "999", "EXT", "Exit the Program"));
        menu.append("-----------------------------------------------------------------------------\n");

        System.out.println(menu.toString());
    }

    @Override
    //overriding the viewAllQues method
    public void viewAllQues() {
        final String NOT_OCCUPIED_SYMBOL = "x";
        final String OCCUPIED_SYMBOL = "0";
        final String QUEUE_HEADER_PREFIX = "Queue ";

        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        FoodQueue[] foodQueues = foodCenter != null ? foodCenter.getFOODQUEUES() : null;

        if (foodQueues == null || foodQueues.length == 0) {
            System.out.println("No food queues available.");
            return;
        }

        StringBuilder outputBuilder = new StringBuilder();
        for (int queIndex = 0; queIndex < 5; queIndex++) {
            outputBuilder.append(" ");
            for (int queueIndex = 0; queueIndex < foodQueues.length; queueIndex++) {
                if (foodQueues[queueIndex] == null) {
                    outputBuilder.append(" ");
                } else {
                    FoodQueue foodQueue = foodQueues[queueIndex];
                    if (foodQueue.getMaxSize() > queIndex) {
                        if (foodQueue.peek(queIndex) == null) {
                            outputBuilder.append(NOT_OCCUPIED_SYMBOL);
                        } else {
                            outputBuilder.append(OCCUPIED_SYMBOL);
                        }
                    } else {
                        outputBuilder.append(" ");
                    }
                }
                outputBuilder.append("      ");
            }
            outputBuilder.append("\n");
        }

        System.out.println(outputBuilder.toString());
        System.out.println(NOT_OCCUPIED_SYMBOL + " – Not Occupied, " + OCCUPIED_SYMBOL + " – Occupied");
        for (int queueIndex = 0; queueIndex < foodQueues.length; queueIndex++) {
            System.out.println(QUEUE_HEADER_PREFIX + (queueIndex + 1) + ": " + foodQueues[queueIndex].getHeader());
        }
    }


    @Override
    //overriding the viewEmptyQues method
    public void viewEmptyQues() {
        int[] emptyQueues = foodCenter.emptyQueues();

        for (int i = 0; i < emptyQueues.length; i++) {
            if (emptyQueues[i] == 1) System.out.println("Queue " + (i + 1) + " is empty");
        }

    }

    @Override
    //overriding the addCustomer method

    public void addCustomer(Customer customer) {
        try {
            int queue = foodCenter.addCustomerToQueue(customer);
            System.out.println(customer.name() + " added to queue : " + queue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    //overriding the removeCustomer method
    public void removeCustomer(int foodQueueNumber, int queNumber) {
        Customer customer = foodCenter.removeCustomerFromQue(foodQueueNumber - 1, queNumber - 1);
        System.out.println(customer.name() + " removed from que " + foodQueueNumber);
    }

    @Override
    //overriding the serveCustomer method
    public void serveCustomer(int foodQueueNumber) {
        try {
            Customer customer = foodCenter.serveCustomer(foodQueueNumber-1);
            System.out.println(customer.name() + " served ");
        } catch (EmptyQueueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    //overriding the viewAllCustomersAlphabet
    public void viewAllCustomersAlphabet() {
        Customer[] customers = foodCenter.viewSortedCustomer();
        for (Customer customer :
                customers) {
            if (customer != null) System.out.println(customer.name());
        }

    }

    @Override
    //overriding the storeData method
    public void storeData(String fileName) {
        foodCenter.saveData("data.txt");

    }

    @Override
    //overriding the loadData method
    public void loadData(String fileName) {
        foodCenter.loadData("data.txt");

    }

    @Override
    //overriding the viewAvailableBurgers method
    public void viewAvailableBurgers() {
        System.out.println("Available Burgers: " + foodCenter.getBurgersAvailable());

    }

    @Override
    //overriding the stockBurger method
    public void stockBurger(int burgers) {
        try {
            foodCenter.stockBurger(burgers);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    //overriding the queIncome method
    public void queIncome(int queueNumb) {
        System.out.println("Income from queue " + queueNumb + ": " + "Rs" + foodCenter.queIncome(queueNumb-1));
    }

    /*
    to make sure input name will be in alphabetic
      @param name, name of the user(input)
      return name
     */
    private static String validateName(String name)
    {
        if (name.matches("[A-Za-z ]+")) {
            return name;
        } else {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
    }

    /*
        to make sure input burger number will be in integer
        @param input, number of burger
        return input
    */
    private static int validateBurgerInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for required burgers: " + input);
        }
    }

    /*
        creating a customer objects
        @param Scanner attributes of  customers
        return Customer(firstName, lastName, reqBurger)
    */
    public Customer createCustomer(Scanner scanner) {
        try {
            System.out.print("Enter first name: ");
            String firstName = validateName(scanner.nextLine().trim());

            System.out.print("Enter last name: ");
            String lastName = validateName(scanner.nextLine().trim());

            System.out.print("Enter required burgers: ");
            String input = scanner.nextLine().trim();
            int reqBurger = validateBurgerInput(input);

            return new Customer(firstName, lastName, reqBurger);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
    }


    public static void main(String[] args) {//choose an option
        FoodCenterCLI foodCenterCLI = new FoodCenterCLI();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            foodCenterCLI.displayMenu();
            System.out.print("Enter your option: ");
            if (scanner.hasNextLine()) { // Check if there is a line available to read
                String option = scanner.nextLine().trim();

                if (!option.isEmpty()) {

                    switch (option) {
                        case "100","VFQ":
                            foodCenterCLI.viewAllQues();
                            break;

                        case "101","VEQ":
                            foodCenterCLI.viewEmptyQues();
                            break;

                        case "102","ACQ":
                            try {
                                Customer customer = foodCenterCLI.createCustomer(scanner);
                                foodCenterCLI.addCustomer(customer);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "103","RCQ":
                            System.out.print("Enter a food que number: ");
                            int queNumber = 0;
                            try {
                                queNumber = Integer.parseInt(scanner.nextLine().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (queNumber >= 1 && queNumber <= 3) {
                                System.out.print("Enter a que index: ");
                                int queIndex = 0;
                                try {
                                    queIndex = Integer.parseInt(scanner.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    foodCenterCLI.removeCustomer(queNumber, queIndex);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Invalid input. Number must be between 1 and 3 (inclusive).");
                            }
                            break;

                        case "104","PCQ":
                            System.out.print("Enter a food que number: ");
                            queNumber = 0;
                            try {
                                queNumber = Integer.parseInt(scanner.nextLine().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (queNumber >= 1 && queNumber <= 3) {
                                try {
                                    foodCenterCLI.serveCustomer(queNumber);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Invalid input. Number must be between 1 and 3 (inclusive).");
                            }
                            break;

                        case "105","VCS":
                            foodCenterCLI.viewAllCustomersAlphabet();
                            break;

                        case "106","SPD":
                            foodCenterCLI.storeData("data.txt");
                            break;

                        case "107","LPD":
                            //foodCenterCLI.loadData("data.txt");
                            break;

                        case "108","STK":
                            foodCenterCLI.viewAvailableBurgers();
                            break;

                        case "109","AFS":
                            try {
                                System.out.print("Please enter the number of burgers: ");
                                int burger = Integer.parseInt(scanner.nextLine().trim());
                                foodCenterCLI.stockBurger(burger);
                                System.out.println(burger+"burgers added");
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "110","IFQ":
                            System.out.print("Enter a food que number: ");
                            queNumber = 0;
                            try {
                                queNumber = Integer.parseInt(scanner.nextLine().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (queNumber >= 1 && queNumber <= 3) {
                                foodCenterCLI.queIncome(queNumber);
                            } else {
                                System.out.println("Invalid input. Number must be between 1 and 3 (inclusive).");
                            }
                            break;

                        case "999","EXIT":
                            System.out.println("Exiting the program...");
                            return;

                        default:
                            System.out.println("Invalid input. Please input a valid option again.");
                            break;
                    }
                }
            }
        }

    }
}
