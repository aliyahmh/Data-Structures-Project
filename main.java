import java.util.Scanner;

public class main {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

//Loading data from CSV

        DoubleLinkedList<Product> products = CSVReader.readProducts("prodcuts.csv");
        DoubleLinkedList<Customer> customers = CSVReader.readCustomers("customers.csv");
        DoubleLinkedList<Order> orders = CSVReader.readOrders("orders.csv");
        DoubleLinkedList<Review> reviews = CSVReader.readReviews("reviews.csv");

        Product.setProductList(products);
        Order.setAllOrders(orders);
        
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

            switch (choice) { // main switch 
                case 1:
                    productsMenu();
                    break;

                    case 2:
                   //////CustomersMenu(products);
                    break;

                case 3:
                    ordersMenu(products);
                    break;

                case 4:
                    reviewsMenu(products);
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
    public static void productsMenu() {
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
                case 1: Product.addProduct(); 
                break;

                case 2: Product.removeProduct(); 
                break;

                case 3: Product.updateProduct();
                 break;

                case 4:
                    System.out.print("Enter Product ID: ");
                    String id = input.nextLine();
                    Product foundById = Product.searchById(id);
                    System.out.println(foundById != null ? foundById : "Product not found!");
                    break;

                case 5:
                    System.out.print("Enter Product Name: ");
                    String name = input.nextLine();
                    Product foundByName = Product.searchByName(name);
                    System.out.println(foundByName != null ? foundByName : "Product not found!");
                    break;

                case 6: 
                Product.showOutOfStock(); break;
                case 7:
                 Product.displayAllProducts(); break;
                case 8: 
                System.out.println("Returning to main menu..."); break;
                default: 
                System.out.println("Invalid choice!");
            }
        } while (choice != 8);
    }


    // ------------------ ORDERS MENU ------------------
    public static void ordersMenu(DoubleLinkedList<Product> products) {
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
                    Order.createOrder(products, customerId);
                    break;

                case 2:
                    Order.cancelOrder(products);
                    break;

                case 3:
                    Order.updateOrderStatus();
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    String orderId = input.nextLine();
                    Order foundOrder = Order.searchOrderById(orderId);
                    System.out.println(foundOrder != null ? foundOrder : "Order not found!");
                    break;

                case 5:
                    Order.displayOrdersBetweenDates();
                    break;

                case 6:
                    Order.displayAllOrders();
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
    public static void reviewsMenu(DoubleLinkedList<Product> products) {
        int choice;
        do {
            System.out.println("\n===== REVIEWS MENU =====");
            System.out.println("1. Add Review");
            System.out.println("2. Update Review");
            System.out.println("3. Show Reviews by Customer");
            System.out.println("4. Show Top 3 Products");
            System.out.println("5. Common High Rated Products (2 Customers)");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String cid = input.nextLine();
                    System.out.print("Enter Product ID: ");
                    String pid = input.nextLine();
                    Review.addReview(cid, pid);
                    break;
                case 2: 
                Review.updateReview(); break;
                case 3:
                 Review.showReviewsByCustomer(); break;
                case 4:
                 Review.showTop3Products(products); break;
                case 5:
                 Review.showCommonHighRatedProducts(); break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }
}
