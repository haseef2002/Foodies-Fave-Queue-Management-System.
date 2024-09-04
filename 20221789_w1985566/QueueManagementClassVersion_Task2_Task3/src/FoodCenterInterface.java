public interface FoodCenterInterface {
    void displayMenu();
    void viewAllQues();
    void viewEmptyQues();
    void addCustomer(Customer customer);
    void removeCustomer(int queNumber, int queIndex);
    void serveCustomer(int foodQueueNumber);
    void viewAllCustomersAlphabet();
    void storeData(String fileName);
    void loadData(String fileName);
    void viewAvailableBurgers();
    void stockBurger(int burgers);
    void queIncome(int queueNum);
}
