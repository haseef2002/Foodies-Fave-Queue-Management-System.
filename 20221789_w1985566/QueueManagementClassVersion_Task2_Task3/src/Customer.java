import java.io.Serializable;

public class Customer implements Comparable<Customer>, Serializable {
    private String firstName;
    private String lastName;
    private int reqBurgers;

    private Customer() {

    }

    //// Constructor
    public Customer(String firstName, String lastName, int reqBurgers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reqBurgers = reqBurgers;
    }

    public String name() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getReqBurgers() {
        return reqBurgers;
    }

    public void setReqBurgers(int reqBurgers) {
        this.reqBurgers = reqBurgers;
    }

    @Override
    public int compareTo(Customer otherCustomer) {
        String thisFullName = this.firstName + this.lastName;
        String otherFullName = otherCustomer.firstName + otherCustomer.lastName;
        return compareStrings(thisFullName, otherFullName);
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
}

