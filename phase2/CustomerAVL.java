import java.util.Scanner;

public class CustomerAVL {

    // ---------- Attributes ----------
    private AVLTree<Customer> customers = CSVReader.readCustomersAVL("customers.csv");
    private Scanner input = new Scanner(System.in);

    // ---------- Customer Management Methods ----------
    
    public boolean checkCustomerId(String id) {
        Customer temp = new Customer(id, "", "");
        return customers.search(temp) != null;
    }

    public Customer findCustomerForOrder(String customerId) {
        Customer temp = new Customer(customerId, "", "");
        return customers.search(temp);
    }

    public void placeOrderForCustomer(String customerId, Order order) {
        Customer customer = findCustomerForOrder(customerId);
        if (customer != null) {
            customer.placeOrder(order);
            System.out.println("Order placed successfully for customer: " + customer.getName());
        } else {
            System.out.println("Customer not found! Please register first.");
        }
    }

    // ---------- Add Customer (O(log n)) ----------
    public void addCustomer() {
        System.out.print("Enter Customer ID: ");
        String id = input.nextLine();

        // check duplicate
        if (checkCustomerId(id)) {
            System.out.println("Customer ID already exists!");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = input.nextLine();
        System.out.print("Enter Email: ");
        String email = input.nextLine();

        Customer c = new Customer(id, name, email);
        customers.insert(c);
        System.out.println("Customer added successfully!");
    }

    // ---------- Remove Customer (O(log n)) ----------
    public void removeCustomer() {
        System.out.print("Enter Customer ID to remove: ");
        String id = input.nextLine();

        Customer temp = new Customer(id, "", "");
        if (customers.remove(temp)) {
            System.out.println("✓ Customer removed!");
        } else {
            System.out.println("✗ Customer not found!");
        }
    }

    // ---------- Update Customer (O(log n)) ----------
    public void updateCustomer() {
        System.out.print("Enter Customer ID to update: ");
        String id = input.nextLine();

        Customer c = searchById(id);
        if (c == null) {
            System.out.println("✗ Customer not found!");
            return;
        }

        System.out.println("\nCurrent: " + c);
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.print("Enter choice: ");
        int ch = input.nextInt();
        input.nextLine(); // clear buffer

        switch (ch) {
            case 1:
                System.out.print("Enter new name: ");
                c.setName(input.nextLine());
                System.out.println("Name updated!");
                break;
            case 2:
                System.out.print("Enter new email: ");
                c.setEmail(input.nextLine());
                System.out.println("Email updated!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // ---------- Search by ID (O(log n)) ----------
    public void searchCustomerById() {
        System.out.print("Enter Customer ID: ");
        String id = input.nextLine();

        Customer c = searchById(id);
        if (c != null) {
            System.out.println("Customer Found:\n" + c);
        } else {
            System.out.println("Customer not found");
        }
    }

    // ---------- Search by Name (O(n)) ----------
    public void searchCustomerByName() {
        System.out.print("Enter Customer Name: ");
        String name = input.nextLine();

        Customer c = searchByName(name);
        if (c != null) {
            System.out.println("Customer Found:\n" + c);
        } else {
            System.out.println("Customer not found");
        }
    }

    // ---------- Display All Customers ----------
    public void displayAllCustomers() {
        LinkedList<Customer> list = customers.inOrderTraversal();
        if (list.isEmpty()) {
            System.out.println("No customers available!");
            return;
        }
        displayCustomerTable(list, "All Customers (Sorted by ID)");
    }


    // ---------- Display Customers Alphabetically ----------
    public void displayCustomersAlphabetically() {
        LinkedList<Customer> list = customers.inOrderTraversal();

        if (list.isEmpty()) {
            System.out.println("No customers available!");
            return;
        }

        AVLTree<Customer> nameAVL = new AVLTree<>(
            (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName())
        );

        list.findFirst();
        while (true) {
            nameAVL.insert(list.retrieve());
            if (list.last()) break;
            list.findNext();
        }

        LinkedList<Customer> sortedByName = nameAVL.inOrderTraversal();

        displayCustomerTable(sortedByName, "Customers Sorted Alphabetically");
    }



    // ---------- Get Order History ----------
    public void showCustomerOrders() {
        System.out.print("Enter Customer ID: ");
        String id = input.nextLine();

        Customer c = searchById(id);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }  

        System.out.println("\nOrder History for: " + c.getName() + "");
        c.viewOrdersHistory();
    }

    // ---------- Private Helper Methods ----------
    private Customer searchById(String id) {
        Customer temp = new Customer(id, "", "");
        return customers.search(temp);
    }

    private Customer searchByName(String name) {
        LinkedList<Customer> list = customers.inOrderTraversal();

        if (list.isEmpty()) return null;

        list.findFirst();
        while (true) {
            Customer c = list.retrieve();
            if (c.getName().equalsIgnoreCase(name))
                return c;
            if (list.last()) break;
            list.findNext();
        }
        return null;
    }

    // ---------- Utility Methods ----------
    public AVLTree<Customer> getCustomerTree() {
        return customers;
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public int size() {
        return customers.size();
    }
    
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
    
    public void displayCustomerTable(LinkedList<Customer> customerList, String title) {
    if (customerList.isEmpty()) {
        System.out.println("No customers to display.");
        return;
    }

    System.out.println("\n=== " + title + " ===");
    System.out.println("┌─────┬────────────┬──────────────────────────┬──────────────────────────┐");
    System.out.println("│ No. │ CustomerID │ Name                     │ Email                    │");
    System.out.println("├─────┼────────────┼──────────────────────────┼──────────────────────────┤");

    int count = 0;
    customerList.findFirst();
    while (true) {
        count++;
        Customer c = customerList.retrieve();

        System.out.printf("│ %-3d │ %-10s │ %-24s │ %-24s │\n",
                count,
                c.getCustomerId(),
                truncate(c.getName(), 24),
                truncate(c.getEmail(), 24)
        );

        if (customerList.last()) break;
        customerList.findNext();
    }

    System.out.println("└─────┴────────────┴──────────────────────────┴──────────────────────────┘");
    System.out.println("Total: " + count + " customers\n");
}


}
