import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        FoodCenter foodCenter = new FoodCenter();
        Scanner in = new Scanner(System.in);

        while (true) {
            foodCenter.displayMenu();
            System.out.print("Enter your option: ");
            String option = in.nextLine().strip();

            switch (option) {
                case "100", "VFQ":
                    foodCenter.viewAllQueues();
                    break;

                case "101", "VEQ":
                    String[] emptyQues = foodCenter.viewEmptyQues();

                    boolean thereIsEmptyQue = false;

                    for (int i = 0; i < emptyQues.length; i++) {
                        if (emptyQues[i] != null) thereIsEmptyQue = true;
                    }


                    if (thereIsEmptyQue) {
                        System.out.println("Empty Ques are: ");
                        for (String emptyQue : emptyQues) {
                            if (emptyQue != null) System.out.println(emptyQue);
                        }
                    } else {
                        System.out.println("There is no empty que");
                    }
                    break;

                case "102", "ACQ":
                    String pattern = "^[a-zA-Z]+$";  // regex to validate customer input - takes only alphabet characters
                    System.out.print("Enter customer name: ");
                    String customerName = in.nextLine().strip();
                    if (customerName.matches(pattern)) foodCenter.addCustomerToQueue(customerName);
                    else System.out.println("Customer name can only contain alphabet characters");
                    break;

                case "103", "RCQ":

                    try {
                        System.out.print("please enter the queue number(1-3):");
                        int queNumber = in.nextInt();
                        if (queNumber > 0 && queNumber < 4) {
                            try {
                                System.out.print("please enter the queue index(que1=0-1,que2=0-2,que3=0-4):");
                                int index = in.nextInt();
                                in.nextLine(); // removing empty input line (better to use buffereader)
                                if (queNumber == 1) {
                                    if (index > 1) {
                                        System.out.println("invalid index(max2");
                                        break;
                                    } else {
                                        foodCenter.removeCustomerFromQue(queNumber, index);
                                        break;
                                    }
                                }
                                if (queNumber == 2) {
                                    if (index > 2) {
                                        System.out.println("invalid index(max3");
                                        break;
                                    } else {
                                        foodCenter.removeCustomerFromQue(queNumber, index);
                                        break;
                                    }
                                }
                                if (queNumber == 3) {
                                    if (index > 4) {
                                        System.out.println("invalid index(max5");
                                        break;
                                    } else {
                                        foodCenter.removeCustomerFromQue(queNumber, index);
                                        break;
                                    }
                                }

                            } catch (InputMismatchException ex) {
                                ex.printStackTrace();
                            }

                        } else System.out.println("Que number should be between 1-3");
                    } catch (InputMismatchException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "104", "PCQ":
                    try {
                        System.out.print("please enter the queue number(1-3):");
                        int queNumber = in.nextInt();
                        in.nextLine();
                        if (queNumber > 0 && queNumber < 4) foodCenter.serveCustomer(queNumber);
                        else System.out.println("Que number should be between 1-3");
                    } catch (InputMismatchException ex) {
                        in.nextLine();
                        ex.printStackTrace();
                    }
                    break;

                case "105", "VCS":
                    foodCenter.viewSortedCustomer();
                    break;

                case "106", "SPD":
                    foodCenter.storeData();
                    break;

                case "107", "LPD":
                    foodCenter.loadData();
                    break;

                case "108", "STK":
                    System.out.println("Remaining burger: " + foodCenter.getBurgerCount());
                    break;

                case "109", "AFS":
                    try {
                        System.out.print("please enter the number of burger:");
                        int burger = in.nextInt();
                        in.nextLine();
                        if (burger % 5 == 0) foodCenter.addBurgerStock(burger);

                        else System.out.println("Incremental count%5 should be 0");
                    } catch (InputMismatchException ex) {
                        in.nextLine();
                        ex.printStackTrace();
                    }
                    break;

                case "999", "EXIT":
                    System.out.println("Exiting the program...");
                    return;

                default:
                    System.out.println("Invalid input. Please input valid option again.");
                    break;
            }

            System.out.println();
        }
    }
}
