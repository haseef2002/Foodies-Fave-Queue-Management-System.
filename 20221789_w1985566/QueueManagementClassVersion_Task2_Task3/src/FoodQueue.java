import exception.EmptyQueueException;
import exception.FullQueueException;

import java.io.Serializable;

public class FoodQueue implements Serializable {
    private int maxSize; // maximum number of elements the queue can hold
    private Customer[] customers; // array stores the customers
    private int currentSize; // current number of elements in the queue
    private String header;

    private double income;

    // Constructor
    public FoodQueue(int size, String header) {
        maxSize = size;
        customers = new Customer[maxSize];
        currentSize = 0;
        this.header = header;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return currentSize == 0;
    }

    // Check if the queue is full
    public boolean isFull() {
        return currentSize == maxSize;
    }

    // Get the number of elements in the queue
    public int size() {
        return currentSize;
    }

    // Insert an element at the rear of the queue
    public void enqueue(Customer customer) throws FullQueueException {
        if (isFull()) {
            throw new FullQueueException("Queue is full. Unable to enqueue " + customer.name());
        } else {
            customers[currentSize] = customer;
            currentSize++;
        }
    }

    // Remove and return the element at the front of the queue
    public Customer dequeue() throws EmptyQueueException{
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Unable to dequeue.");
        } else {
            Customer customer = customers[currentSize - 1];

            // Left shift the elements
            for (int i = 0; i < currentSize-1; i++) {
                customers[i] = customers[i + 1];
            }
            customers[currentSize - 1] = null;
            currentSize--;
            return customer;
        }
    }

    // Peek at the element at the front of the queue
    public Customer peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Empty queue. Unable to peek.");
        } else {
            return customers[0];
        }
    }


    public Customer peek(int index) {
        if (index < 0 || index >= maxSize) {
            throw new IllegalArgumentException("Invalid index");
        }
        else {
            return customers[index];
        }
    }


    // Print the elements of the queue
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.print("Queue (front to rear): ");
            for (int i = currentSize - 1; i >= 0; i--) {
                System.out.print(customers[i].name() + " ");
            }
            System.out.println();
        }
    }


    public Customer removeCustomer(int index) {
        if (index < 0 || index >= currentSize) {//handle the error
            throw new IllegalArgumentException("Invalid index");
        }

        Customer customer = customers[index];

        // Left shift the elements
        for (int i = index; i < currentSize - 1; i++) {
            customers[i] = customers[i + 1];
        }

        customers[currentSize - 1] = null;
        currentSize--;

        return customer;
    }

    public double updateIncome(double burgerSale) {
        this.income += burgerSale;

        return income;
    }






    public Customer[] getCustomers() {
        return customers;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getHeader() {
        return header;
    }

    public double getIncome() {
        return income;
    }
}

