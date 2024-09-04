import exception.EmptyQueueException;
import exception.FullQueueException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FoodCenter implements Serializable {
    private final FoodQueue smallFoodQueue = new FoodQueue(2, "Small");
    private final FoodQueue mediumFoodQueue = new FoodQueue(3, "Medium");
    private final FoodQueue largeFoodQueue = new FoodQueue(5, "Large");
    private final FoodQueue[] FOODQUEUES = {smallFoodQueue, mediumFoodQueue, largeFoodQueue};
    private final int MAX_STOCK_LIMIT = 100;
    private int burgersAvailable; // the burger stock maxed to 100
    private final double burgerPrice = 650.00;
    private final List<Customer> servedCustomers = new ArrayList<>();
    private  WaitingList waitingList = new WaitingList(10);

    public FoodCenter() {
        this.burgersAvailable = MAX_STOCK_LIMIT;
    }

    private void serveBurgers(Customer customer) {
        this.burgersAvailable -= customer.getReqBurgers();
        warnBurgerAvailability();
    }

    public Customer serveCustomer(int foodQueueNumber) {
        FoodQueue foodQueue = getFoodQueueWithIndex(foodQueueNumber);
        Customer customer = foodQueue.dequeue();
        servedCustomers.add(customer);
        foodQueue.updateIncome(burgerSale(customer.getReqBurgers()));

        try {
            Customer waitingCustomer  = waitingList.deQueue();
            foodQueue.enqueue(waitingCustomer);
            System.out.println("Customer from waiting que added the queue");
        }
        catch (EmptyQueueException e)  {
            System.out.println("Error in waiting list dequeue operation: " + e.getMessage());
        }

        return customer;
    }

    private void warnBurgerAvailability() {
        if (burgersAvailable <= 10) System.out.println("Remaining Burger: " + getBurgersAvailable());
    }


    public int addCustomerToQueue(Customer customer) throws FullQueueException {
        if (burgersAvailable < customer.getReqBurgers()) {
            throw new RuntimeException("Not enough burger available in the stock");
        }
        else {
            for (int queueNumber = 0; queueNumber < 5; queueNumber++) {
                for (int foodQueueIndex = 0; foodQueueIndex < FOODQUEUES.length; foodQueueIndex++) {
                    if (queueNumber < FOODQUEUES[foodQueueIndex].getMaxSize()) {
                        if (FOODQUEUES[foodQueueIndex].peek(queueNumber) == null) {
                            FOODQUEUES[foodQueueIndex].enqueue(customer);
                            serveBurgers(customer);
                            return foodQueueIndex + 1;
                        }
                    }
                }
            }

            if (waitingList.enQueue(customer)) {
                throw new FullQueueException("Queues is full. Unable to enqueue. Customer " + customer.name() + " added to the waiting list ");
            }
            throw new FullQueueException("Queues is full. Unable to enqueue " + customer.name());
        }
    }


    public int[] emptyQueues() {
        int[] emptyQues = new int[FOODQUEUES.length];

        for (int i = 0; i < FOODQUEUES.length; i++) {
            if (FOODQUEUES[i].isEmpty()) {
                emptyQues[i] = 1;
            }
        }

        return emptyQues;
    }


    public Customer removeCustomerFromQue(int queNumber, int index) {
        FoodQueue foodQueue = FOODQUEUES[queNumber];

        Customer customer = foodQueue.removeCustomer(index);
        burgersAvailable += customer.getReqBurgers();

        try {
            Customer waitingCustomer  = waitingList.deQueue();
            foodQueue.enqueue(waitingCustomer);
            System.out.println("Customer from waiting que added the queue");
        }
        catch (EmptyQueueException e)  {
            System.out.println("Error in waiting list dequeue operation: " + e.getMessage());
        }

        return customer;
    }


    public Customer[] viewSortedCustomer() {
        // list all the customers in the ques in alphabetical order
        Customer[] allQueCustomers = new Customer[10];
        int count = 0;

        for (FoodQueue foodQueue : FOODQUEUES) {
            for (Customer customer : foodQueue.getCustomers()) {
                if (customer != null) {
                    allQueCustomers[count] = customer;
                    count++;
                }

            }
        }
        // Sort the list using compareToIgnoreCase()
        bubbleSort(allQueCustomers, count);

        return allQueCustomers;
    }

    private void bubbleSort(Customer[] customers, int count) {
        int n = count;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (customers[j].compareTo(customers[j + 1]) > 0) {
                    // Swap array[j] and array[j+1]
                    Customer temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                // If no elements were swapped in the inner loop, the array is already sorted
                break;
            }
        }
    }

    public double queIncome(int foodQueueNum) {
        return FOODQUEUES[foodQueueNum].getIncome();
    }


    public int stockBurger(int burgers) throws IllegalArgumentException {
        int initialStock = burgersAvailable;
        burgersAvailable += burgers;

        if (burgersAvailable > MAX_STOCK_LIMIT) {
            int extraBurgers = burgersAvailable - MAX_STOCK_LIMIT;
            burgersAvailable = initialStock;
            String errorMessage = "Burger stock limit is " + MAX_STOCK_LIMIT + ", " + extraBurgers + " extra burgers provided";
            throw new IllegalArgumentException(errorMessage);
        }
        return burgersAvailable;
    }

    private double burgerSale(int burgers) {
        return burgerPrice * burgers;
    }

    public FoodQueue getFoodQueueWithIndex(int foodQueueNumber) {
        return FOODQUEUES[foodQueueNumber];
    }
    // Save data to a text file
    public void saveData(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            // Save burger stock
            writer.println(burgersAvailable);

            // Save served customers
            for (Customer customer : servedCustomers) {
                writer.println("servedCustomer,"+ customer.getFirstName() + "," + customer.getLastName() + "," + customer.getReqBurgers());
            }

            // Save waiting list customers
            for (int i = 0; i < waitingList.getSize(); i++) {
                Customer customer = waitingList.getQueue()[(waitingList.getFront() + i) % waitingList.getQueue().length];
                writer.println("waitingList,"+ customer.getFirstName() + "," + customer.getLastName() + "," + customer.getReqBurgers());
            }


            // Save food queue customers
            for (FoodQueue foodQueue : FOODQUEUES) {
                for (Customer customer : foodQueue.getCustomers()) {
                    if (customer != null) {
                        writer.println(foodQueue.getHeader() + "," + customer.getFirstName() + "," +
                                customer.getLastName() + "," + customer.getReqBurgers());
                    }
                }
            }

            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Reset the state
            servedCustomers.clear();
            waitingList = new WaitingList(10);
            for (FoodQueue foodQueue : FOODQUEUES) {
                foodQueue = new FoodQueue(foodQueue.getMaxSize(), foodQueue.getHeader());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String dataIdentifier = parts[0].trim();

                if (dataIdentifier.equals("servedCustomer") && parts.length == 4) {
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    int reqBurgers = Integer.parseInt(parts[3].trim());
                    Customer customer = new Customer(firstName, lastName, reqBurgers);
                    servedCustomers.add(customer);
                } else if (dataIdentifier.equals("waitingList") && parts.length == 4) {
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    int reqBurgers = Integer.parseInt(parts[3].trim());
                    Customer customer = new Customer(firstName, lastName, reqBurgers);
                    waitingList.enQueue(customer);
                } else if (parts.length == 4) {
                    String header = dataIdentifier;
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    int reqBurgers = Integer.parseInt(parts[3].trim());
                    Customer customer = new Customer(firstName, lastName, reqBurgers);

                    for (FoodQueue foodQueue : FOODQUEUES) {
                        if (foodQueue.getHeader().equals(header)) {
                            foodQueue.enqueue(customer);
                            break;
                        }
                    }
                }
            }

            System.out.println("Data loaded from " + filename);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    public int getBurgersAvailable() {
        return burgersAvailable;
    }

    public FoodQueue[] getFOODQUEUES() {
        return FOODQUEUES;
    }
}