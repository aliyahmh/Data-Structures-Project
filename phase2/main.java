import java.util.Scanner;

public class DataStructureProject {

    private static ProductAVL productManager;
    private static CustomerAVL customerManager;
    private static OrderAVL orderManager;
    private static ReviewsAVL reviewsManager;
    
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        // Initialize managers
        productManager = new ProductAVL();
        customerManager = new CustomerAVL();
        orderManager = new OrderAVL();
        reviewsManager = new ReviewsAVL();

        

        int choice;
        do {
            System.out.println("==========================");
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
                    productMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    orderMenu();
                    break;
                case 4:
                    advancedQueriesMenu();
                    break;
                case 5:
                    System.out.println("\n✓ Thank you for using the system!");
                    break;
                default:
                    System.out.println("✗ Invalid choice!");
            }
        } while (choice != 5);
    }

    // ------------------ PRODUCTS MENU ------------------//
    
    public static void productMenu() {
        int choice;
        do {
            System.out.println("\n===== PRODUCTS MENU =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Search Product by ID");
            System.out.println("5. Search Product by Name");
            System.out.println("6. Display All Products");
            System.out.println("7. Show Out of Stock Products");
            System.out.println("8. Show the Top 3 Most Reviewed");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    productManager.updateProduct();
                    break;
                case 3:
                    productManager.removeProduct();
                    break;
                case 4:
                    productManager.searchProductById();
                    break;
                case 5:
                    productManager.searchProductByName();
                    break;
                case 6:
                    productManager.displayAllProducts();
                    break;
                case 7:
                    productManager.showOutOfStock();
                    break;
                case 8:
                    productManager.showTop3MostReviewed();
                    break;
                case 9:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("✗ Invalid choice!");
            }
        } while (choice != 9);
    }
    
    // ------------------ CUSTOMERS MENU ------------------//
    public static void customersMenu() {
        int choice;
        Scanner input = new Scanner(System.in);
        Customer helper = new Customer(); 
    
        do {
            System.out.println("\n===== CUSTOMERS MENU =====");
            System.out.println("1. Register New Customer");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: 
                
                    break;

                case 2: 
            
                    break;
                
                case 3: 
                    
                    break;
                    
                case 4:
                
                System.out.println("Returning to main menu...");
                break;

            default:
                
                System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    // ------------------ ORDERS MENU ------------------//
    public static void ordersMenu() {
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
                    
                    break;

                case 2:
                    
                    break;

                case 3:
                    
                    break;

                case 4:
                    
                    break;

                case 5:
                    
                    break;

                case 6:
                    
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
    public static void reviewsMenu() {
        
        int choice;
        do {
            System.out.println("\n===== REVIEWS MENU =====");
            System.out.println("1. Add Review");
            System.out.println("2. Update Review");
            System.out.println("3. Show Reviews by Customer");
            System.out.println("4. Get Average Rating For a Product");
            System.out.println("5. Show Top 3 Products");
            System.out.println("6. Common High Rated Products (2 Customers)");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    
                    
                    break;
                    
                case 2: 
                    
                    
                    break;
                    
                case 3:
                    
                    
                    break;

                case 4:
                    
                    
                break;

                case 5:
                    
                    
                    break;
                 
                case 6:
                    
                    
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
