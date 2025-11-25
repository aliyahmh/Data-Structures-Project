import java.util.Scanner;

public class CustomerAVL {

    // ---------- Attributes ----------
    private AVLTree<Customer> customers = CSVReader.readCustomersAVL("datasets/customers.csv");
    private Scanner input = new Scanner(System.in);

    // ---------- Add Customer (O(log n)) ----------
    public void addCustomer() {
        System.out.print("Enter Customer ID: ");
        String id = input.nextLine();

        // check duplicate
        Customer temp = new Customer(id, "", "");
        if (customers.search(temp) != null) {
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


    // --------- search inside the tree ----------
    private Customer searchById(String id) {
        Customer temp = new Customer(id, "", "");
        return customers.search(temp);
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

    // ---------- Display All Customers ----------
    public void displayAllCustomers() {
        LinkedList<Customer> list = customers.inOrderTraversal();

        if (list.isEmpty()) {
            System.out.println("No customers available!");
            return;
        }

        System.out.println("\n=== All Customers (Sorted by ID) ===");
        list.findFirst();
        while (true) {
            System.out.println(list.retrieve());
            if (list.last()) break;
            list.findNext();
        }
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

        System.out.println("\n Order History for: " + c.getName() + "");
        c.viewOrdersHistory();
    }

    // ---------- Utility ----------
    public AVLTree<Customer> getCustomerTree() {
        return customers;
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public int size() {
        return customers.size();
    }

}


