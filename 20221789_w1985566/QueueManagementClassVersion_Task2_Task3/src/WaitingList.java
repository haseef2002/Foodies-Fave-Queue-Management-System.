import exception.EmptyQueueException;

import java.io.Serializable;

public class WaitingList implements Serializable {
    private Customer[] queue;
    private int front;
    private int rear;
    private int size;

    //constructor
    public WaitingList(int k) {
        queue = new Customer[k];
        front = -1;
        rear = -1;
        size = 0;
    }

    public boolean enQueue(Customer customer) {
        if (isFull()) {
            return false;
        }
        if (isEmpty()) {
            front = 0;
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = customer;
        size++;
        return true;
    }

    public Customer deQueue() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Unable to dequeue.");
        }
        Customer customer = queue[front];
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % queue.length;
        }
        size--;
        return customer;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    public Customer[] getQueue() {
        return queue;
    }

    public int getFront() {
        return front;
    }

    public int getSize() {
        return size;
    }
}
