import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class FoodCenter {
    // a store has three cashiers with 3 ques.
    //declaration of the array.
    private String[] cashierQueueMax2 = new String[2];
    private String[] cashierQueueMax3 = new String[3];
    private String[] cashierQueueMax5 = new String[5];


    private int burgerCount = 50; // the burger stock maxed to 50

    public FoodCenter() {
    }

    //assigning burger to customer.
    public void serveBurgers() {
        this.burgerCount -= 5;

        if (burgerCount == 10) {
            System.out.println("!!! remaining burger stock is :10 !!!");
        }
    }

    public void viewAllQueues() {
        // displays sort of diagram of the que
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        for (int i = 0; i < 5; i++) {
            if (i < 2) {
                if (cashierQueueMax2[i] != null) System.out.print("0    ");
                else System.out.print("x    ");
                if (cashierQueueMax3[i] != null) System.out.print("0    ");
                else System.out.print("x    ");
                if (cashierQueueMax5[i] != null) System.out.print("0");
                else System.out.print("x");
            } else if (i < 3) {
                if (cashierQueueMax3[i] != null) System.out.print("     0    ");
                else System.out.print("     x    ");
                if (cashierQueueMax5[i] != null) System.out.print("0");
                else System.out.print("x");
            } else {
                if (cashierQueueMax5[i] != null) System.out.print("          0");
                else System.out.print("          x");
            }
            System.out.println();
        }
        System.out.println("X – Not Occupied, O – Occupied");

    }

    /*
      assign customer name to the queue with minimum length
      @param name, name of the customer

     */
    public void addCustomerToQueue(String customerName) {
        // customer added to the que in cashier order
        // first customer goes to que 1
        // second customers goes to que 2.....
        if (this.burgerCount > 0) {
            for (int queIndex = 0; queIndex < 5; queIndex++) {
                if (queIndex < 2) {
                    if (this.cashierQueueMax2[queIndex] == null) {
                        this.cashierQueueMax2[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "first queue");
                        serveBurgers();
                        return;
                    } else if (this.cashierQueueMax3[queIndex] == null) {
                        this.cashierQueueMax3[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "Second queue");
                        serveBurgers();
                        return;
                    } else if (this.cashierQueueMax5[queIndex] == null) {
                        this.cashierQueueMax5[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "Third queue");
                        serveBurgers();
                        return;
                    }
                } else if (queIndex < 3) {

                    if (this.cashierQueueMax3[queIndex] == null) {
                        this.cashierQueueMax3[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "Second que");
                        serveBurgers();
                        return;
                    } else if (this.cashierQueueMax5[queIndex] == null) {
                        this.cashierQueueMax5[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "Third que");
                        serveBurgers();
                        return;
                    }
                } else {
                    if (this.cashierQueueMax5[queIndex] == null) {
                        this.cashierQueueMax5[queIndex] = customerName;
                        System.out.println(customerName + " added to " + "Third que");
                        serveBurgers();
                        return;
                    }
                }
            }
            System.out.println("Que is full -- " + customerName + " not added to the que");
        } else {
            System.out.println("burger stock not available." + customerName + "not added to the que");
        }

    }

    /*
      check the queues with empty customers

      @return queue number with empty customer
     */
    public String[] viewEmptyQues() {
        // returns ques without a customer
        String[] emptyQues = new String[3];
        if (cashierQueueMax2[0] == null) {
            emptyQues[0] = "Que1";
        }
        if (cashierQueueMax3[0] == null) {
            emptyQues[1] = "Que2";
        }
        if (cashierQueueMax5[0] == null) {
            emptyQues[2] = "Que3";
        }
        return emptyQues;
    }

    /*
      removing the served customer from given queue.
      @param number of the queue

     */
    public void serveCustomer(int queNumber) {
        if (queNumber == 1) {
            if (cashierQueueMax2[0] != null) {
                System.out.println(cashierQueueMax2[0] + " is served from first queue ");
                shiftQueueLeft(cashierQueueMax2);
            } else {
                System.out.println("No customer in the provided que1");
            }
        } else if (queNumber == 2) {
            if (cashierQueueMax3[0] != null) {
                System.out.println(cashierQueueMax3[0] + " is served from second queue");
                shiftQueueLeft(cashierQueueMax3);
            } else {
                System.out.println("No customer in the provided que2");
            }
        } else if (queNumber == 3) {
            if (cashierQueueMax5[0] != null) {
                System.out.println(cashierQueueMax5[0] + " is served from third queue ");
                shiftQueueLeft(cashierQueueMax5);
            } else {
                System.out.println("No customer in the provided que3");
            }
        }
    }

    /*removing a customer  from given specific index and queue number,rearrange the given queue
      @pram number of the queue and index of the specific queue

     */
    public void removeCustomerFromQue(int queNumber, int index) {
        // customer is removed from given que and the index
        // left shift is triggered from the given index to the array
        if (queNumber == 1) {
            if (cashierQueueMax2[index] != null) {
                System.out.println(cashierQueueMax2[index] + " removed from que1 ");
                shiftQueueLeftFromIndex(cashierQueueMax2, index);
                this.burgerCount += 5;
            } else {
                System.out.println("No customer in the provided que1");
            }
        } else if (queNumber == 2) {
            if (cashierQueueMax3[index] != null) {
                shiftQueueLeftFromIndex(cashierQueueMax3, index);
                System.out.println(cashierQueueMax3[index] + " removed from que2 ");
                this.burgerCount += 5;
            } else {
                System.out.println("No customer in the provided que2");
            }
        } else if (queNumber == 3) {
            if (cashierQueueMax5[index] != null) {
                System.out.println(cashierQueueMax5[index] + " removed from que3 ");
                shiftQueueLeftFromIndex(cashierQueueMax5, index);
                this.burgerCount += 5;
            } else {
                System.out.println("No customer in the provided que3");
            }
        }
    }

    private void shiftQueueLeftFromIndex(String[] cashierQueue, int index) {
        // perform left shift form a specific index
        for (int i = index; i < cashierQueue.length - 1; i++) {
            cashierQueue[i] = cashierQueue[i + 1];
        }
        cashierQueue[cashierQueue.length - 1] = null;
    }


    private void shiftQueueLeft(String[] cashierQueue) {
        for (int index = 0; index < cashierQueue.length - 1; index++) {
            cashierQueue[index] = cashierQueue[index + 1];
        }
        cashierQueue[cashierQueue.length - 1] = null;
    }

    public void viewSortedCustomer() {
        // list all the customers in the ques in alphabetical order
        String[] allQueCustomers = new String[10];
        int count = 0;
        for (String customer :
                cashierQueueMax2) {
            if (customer != null) {
                allQueCustomers[count] = customer;
                count++;
            }
        }
        for (String customer :
                cashierQueueMax3) {
            if (customer != null) {
                allQueCustomers[count] = customer;
                count++;
            }
        }
        for (String customer :
                cashierQueueMax5) {
            if (customer != null) {
                allQueCustomers[count] = customer;
                count++;
            }
        }

        // Sort the list using compareToIgnoreCase()
        bubbleSort(allQueCustomers);

        // Print the sorted list
        for (String customer : allQueCustomers) {
            if (customer != null) System.out.println(customer);
        }

        if (count <= 0) {
            System.out.println("The que is empty");
        }
    }

    private void bubbleSort(String[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (compareStrings(array[j], array[j + 1]) > 0) {
                    // Swap array[j] and array[j+1]
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                // If no elements were swapped in the inner loop, the array is already sorted
                break;
            }
        }
    }

    public static int compareStrings(String a, String b) {
        // Handle null values
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return 1;
        } else if (b == null) {
            return -1;
        }

        // Convert the strings to lowercase for case-insensitive comparison
        String lowerA = a.toLowerCase();
        String lowerB = b.toLowerCase();

        // Compare the lowercase strings
        int result = lowerA.compareTo(lowerB);

        // If the lowercase comparison is equal, use the original strings for case-sensitive comparison
        if (result == 0) {
            result = a.compareTo(b);
        }

        return result;
    }


    public void displayMenu() {
        System.out.println("---------------------Menu choices-----------------------------------------------");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue (from a specific location)");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burger Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("999 or EXT: Exit the Program");
    }

    public void addBurgerStock(int num) {
        // burger count should not reach more than 50
        if (this.burgerCount + num > 50) {
            System.out.println("The Burger count reached the limit of 50 . 50 < " + (this.burgerCount + num));
        } else this.burgerCount += num;
    }

    public void storeData() {
        try {
            File file = new File("Data.txt");
            FileWriter writer = new FileWriter(file);

            //storing the data(customers in their specific queue string,numbers of remaining burgers in stock)

            writer.write("customers in queue1 :" + Arrays.toString(this.cashierQueueMax2) + "\n");
            writer.write("customers in queue2 :" + Arrays.toString(this.cashierQueueMax3) + "\n");
            writer.write("customers in queue3 :" + Arrays.toString(this.cashierQueueMax5) + "\n");
            writer.write("Burger Stock :" + this.burgerCount);

            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }

    public void loadData() {
        try {
            File file = new File("Data.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Reading the data from the file and updating the queue arrays and burgerCount
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Parse the line and update the relevant Arrays
                if (line.startsWith("customers in queue1")) {
                    String[] values = line.split(":");
                    this.cashierQueueMax2 = parseArray(values[1]);
                    replaceNullWithValue(this.cashierQueueMax2, null);
                } else if (line.startsWith("customers in queue2")) {
                    String[] values = line.split(":");
                    this.cashierQueueMax3 = parseArray(values[1]);
                    replaceNullWithValue(this.cashierQueueMax3, null);
                } else if (line.startsWith("customers in queue3")) {
                    String[] values = line.split(":");
                    this.cashierQueueMax5 = parseArray(values[1]);
                    replaceNullWithValue(this.cashierQueueMax5, null);
                } else if (line.startsWith("Burger Stock")) {
                    String[] values = line.split(":");
                    this.burgerCount = Integer.parseInt(values[1].trim());
                }
            }
            bufferedReader.close();
            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading program data: " + e.getMessage());
        }
    }

    private void replaceNullWithValue(Object[] array, Object value) {
        for (int i = 0; i < array.length; i++) {
            if ("null".equals(array[i])) {
                array[i] = value;
            }
        }
    }

    public String[] parseArray(String arrayString) {
        String[] elements = arrayString.replaceAll("\\[|\\]", "").split(", ");
        String[] array = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            array[i] = elements[i];
        }
        return array;
    }


    public String[] getCashierQueueMax2() {
        return cashierQueueMax2;
    }

    public void setCashierQueueMax2(String[] cashierQueueMax2) {
        this.cashierQueueMax2 = cashierQueueMax2;
    }

    public String[] getCashierQueueMax3() {
        return cashierQueueMax3;
    }

    public void setCashierQueueMax3(String[] cashierQueueMax3) {
        this.cashierQueueMax3 = cashierQueueMax3;
    }

    public String[] getCashierQueueMax5() {
        return cashierQueueMax5;
    }

    public void setCashierQueueMax5(String[] cashierQueueMax5) {
        this.cashierQueueMax5 = cashierQueueMax5;
    }

    public int getBurgerCount() {
        return burgerCount;
    }

    public void setBurgerCount(int burgerCount) {
        this.burgerCount = burgerCount;
    }
}
