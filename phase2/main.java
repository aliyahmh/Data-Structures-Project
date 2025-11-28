import java.util.Scanner;

public class main {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        // Loading data from CSV into AVL trees
        ProductAVL products = new ProductAVL();
        CustomerAVL customers = new CustomerAVL();
        OrderAVL orders = new OrderAVL();
        ReviewAVL reviews = new ReviewAVL();

        System.out.println("=== Loading data into AVL trees ===");

        int choice;
        do {
            System.out.println("\n==========================");
            System.out.println("         MAIN MENU");
            System.out.println("==========================");
            System.out.println("1. Products");
            System.out.println("2. Customers");
            System.out.println("3. Orders");
            System.out.println("4. Reviews");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); 

            switch (choice) {
                case 1:
                    productsMenu(products);
                    break;

                case 2:
                    customersMenu(customers, products, orders);
                    break;

                case 3:
                    ordersMenu(products, orders);
                    break;

                case 4:
                    reviewsMenu(products, reviews);
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }

    // ------------------ PRODUCTS MENU ------------------
    
    public static void productsMenu(ProductAVL products) {
        int choice;
        do {
            System.out.println("\n===== PRODUCTS MENU =====");
            System.out.println("1. Add new product");
            System.out.println("2. Remove product");
            System.out.println("3. Update product");
            System.out.println("4. Search by ID");
            System.out.println("5. Search by Name");
            System.out.println("6. Show Out-of-Stock Products");
            System.out.println("7. Show All Products");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: 
                    products.addProduct();
                    break;

                case 2: 
                    products.removeProduct();
                    break;

                case 3: 
                    products.updateProduct();
                    break;
                    
                case 4:
                    products.searchProductById();
                    break;

                case 5:
                    products.searchProductByName();
                    break;

                case 6: 
                    products.showOutOfStock();
                    break;
                    
                case 7:
                    products.displayAllProducts();
                    break;
                    
                case 8: 
                    System.out.println("Returning to main menu..."); 
                    break;
                
                default: 
                    System.out.println("Invalid choice!");
            }
            
        } while (choice != 8);
    }

    // ------------------ CUSTOMERS MENU ------------------
    public static void customersMenu(CustomerAVL customers, ProductAVL products, OrderAVL orders) {
        int choice;
        do {
            System.out.println("\n===== CUSTOMERS MENU =====");
        System.out.println("1. Register New Customer");
        System.out.println("2. Search Customer by ID");
        System.out.println("3. Search Customer by Name");
        System.out.println("4. Update Customer");
        System.out.println("5. Remove Customer");
        System.out.println("6. Place Order");
        System.out.println("7. View Order History");
        System.out.println("8. Display All Customers (by ID)");
        System.out.println("9. Display Customers Alphabetically");
        System.out.println("10. Back to Main Menu");
        System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: 
                customers.addCustomer();
                    break;

                case 2: 
                 customers.searchCustomerById();                   
                    break;
                
                case 3: 
                customers.searchCustomerByName();
                    break;
                    
                case 4:
                customers.updateCustomer();
                    break;

                      case 5:
                customers.removeCustomer();
                break;

                 case 6: 
                // Place Order
                System.out.print("Enter Customer ID: ");
                String customerId = input.nextLine();
                
                // Check if customer exists
                if (!customers.checkCustomerId(customerId)) {
                    System.out.println("Customer not found! Please register first.");
                } else {
                    // Create order using OrderAVL
                    Order newOrder = orders.createOrder(products, customerId);
                    if (newOrder != null) {
                        customers.placeOrderForCustomer(customerId, newOrder);
                    }
                }
                break;

                 case 7: 
                customers.showCustomerOrders();
                break;
                
            case 8:
                customers.displayAllCustomers();
                break;
                
            case 9:
                customers.displayCustomersAlphabetically();
                break;
                    
            case 10:
                System.out.println("Returning to main menu...");
                break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 10);
    }

    // ------------------ ORDERS MENU ------------------
    public static void ordersMenu(ProductAVL products, OrderAVL orders) {
        int choice;
        do {
            System.out.println("\n===== ORDERS MENU =====");
            System.out.println("1. Place New Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Search Order by ID");
            System.out.println("5. Display Orders Between Dates");
            System.out.println("6. Show All Orders");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID for new order: ");
                    String customerId = input.nextLine();
                    orders.createOrder(products, customerId);
                    break;

                case 2:
                    orders.cancelOrder(products);
                    break;

                case 3:
                    orders.updateOrderStatus();
                    break;

                case 4:
                    orders.searchOrderById();
                    break;

                case 5:
                    orders.searchOrdersByDateRange();
                    break;

                case 6:
                    orders.displayAllOrders();
                    break;

                case 7:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);
    }

    // ------------------ REVIEWS MENU ------------------
    public static void reviewsMenu(ProductAVL products, ReviewAVL reviews) {
        int choice;
        do {
            System.out.println("\n===== REVIEWS MENU =====");
            System.out.println("1. Add Review");
            System.out.println("2. Search Review by ID");
            System.out.println("3. Show Reviews by Customer");
            System.out.println("4. Get Average Rating For a Product");
            System.out.println("5. Show Top 3 Products");
            System.out.println("6. Show Reviews for a Product");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    reviews.addReview();
                    break;
                    
                case 2: 
                    reviews.searchReview();
                    break;
                    
                case 3:
                    reviews.showCustomerReviews();
                    break;

                case 4:
                    reviews.showAverageRating();
                    break;

                case 5:
                    reviews.showTop3Products();
                    break;
                 
                case 6:
                    reviews.showProductReviews();
                    break;
                 
                case 7:
                    System.out.println("Returning to main menu...");
                    break;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);
    }
}

