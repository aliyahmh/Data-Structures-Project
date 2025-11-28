import java.util.Scanner;

public class main3 {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        //Loading data from CSV

        LinkedList<Product> products = CSVReader.readProducts("prodcuts.csv");
        LinkedList<Customer> customers = CSVReader.readCustomers("customers.csv");
        LinkedList<Order> orders = CSVReader.readOrders("orders.csv");
        LinkedList<Review> reviews = CSVReader.readReviews("reviews.csv");

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
                    productsMenu(products);
                    break;

                case 2:
                    customersMenu(customers, products,orders);

                    break;

                case 3:
                    ordersMenu(products,orders);
                    break;

                case 4:
                    reviewsMenu(products,reviews);
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
    
    public static void productsMenu(LinkedList<Product> products) {
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
                    
                    Product.addProduct(products); 
                    break;

                case 2: 
                    
                    Product.removeProduct(products); 
                    break;

                case 3: 
                    
                    Product.updateProduct(products);
                    break;
                    
                case 4:
                    
                    System.out.print("Enter Product ID: ");
                    String id = input.nextLine();
                    Product foundById = Product.searchById(products,id);
                    System.out.println(foundById != null ? foundById : "Product not found!");
                    break;

                case 5:
                    
                    System.out.print("Enter Product Name: ");
                    String name = input.nextLine();
                    Product foundByName = Product.searchByName(products,name);
                    System.out.println(foundByName != null ? foundByName : "Product not found!");
                    break;

                case 6: 
                    
                    Product.showOutOfStock(products); 
                    break;
                    
                case 7:
                    
                    Product.displayAllProducts(products); 
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
    public static void customersMenu(LinkedList<Customer> customers, LinkedList<Product> products,LinkedList<Order> orders) {
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
            case 1: {
                System.out.print("Enter Customer ID: ");
                String customerId = input.nextLine();

                Customer checker = new Customer(); 
                boolean exists = checker.checkCustomerId(customerId, customers); 

                if (!exists) {
                    System.out.print("Enter Customer Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter Customer Email: ");
                    String email = input.nextLine();

                    Customer newCustomer = new Customer(customerId, name, email);
                    newCustomer.registerCustomer(newCustomer,customers);

                    System.out.println("Customer registered successfully!");
                } else {
                    System.out.println("Customer already exists!");
                }
                break;

        }

        case 2: {
            
            System.out.print("Enter Customer ID: ");
            String customerId = input.nextLine();

            // find the customer in the list
            Customer foundCustomer = null;
            Node<Customer> current = customers.getHead();
            while (current != null) {
                if (current.data.getCustomerId().equals(customerId)) {
                    foundCustomer = current.data;
                    break;
                }
                    current = current.next;
            }

            if (foundCustomer == null) {
                System.out.println("Customer not found! Please register first.");
            } else {
                // use existing Order.createOrder() to create order
                Order newOrder = Order.createOrder(products,orders, customerId);
                if (newOrder != null) {
                    foundCustomer.placeOrder(newOrder);
                }
            }
            break;
        }

        case 3: {
            
            System.out.print("Enter Customer ID: ");
            String customerId = input.nextLine();

            // find the customer and view their order history
            Customer foundCustomer = null;
            Node<Customer> current = customers.getHead();
            while (current != null) {
                if (current.data.getCustomerId().equals(customerId)) {
                    foundCustomer = current.data;
                    break;
                }
                current = current.next;
            }

            if (foundCustomer != null) {
                foundCustomer.viewOrdersHistory();
            } else {
                System.out.println("Customer not found!");
            }
                break;
            }

            case 4:
                
                System.out.println("Returning to main menu...");
                break;

            default:
                
                System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    // ------------------ ORDERS MENU ------------------
    public static void ordersMenu(LinkedList<Product> products,LinkedList<Order> orders) {
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
                    Order.createOrder(products,orders, customerId);
                    break;

                case 2:
                    Order.cancelOrder(orders,products);
                    break;

                case 3:
                    Order.updateOrderStatus(orders);
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    String orderId = input.nextLine();
                    Order foundOrder = Order.searchOrderById(orders,orderId);
                    System.out.println(foundOrder != null ? foundOrder : "Order not found!");
                    break;

                case 5:
                    Order.displayOrdersBetweenDates(orders);
                    break;

                case 6:
                    Order.displayAllOrders(orders);
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
    public static void reviewsMenu(LinkedList<Product> products,LinkedList<Review> reviews) {
        
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
                    
                    System.out.print("Enter Customer ID: ");
                    String cid = input.nextLine();
                    System.out.print("Enter Product ID: ");
                    String pid = input.nextLine();
                    Review.addReview(reviews,cid, pid);
                    break;
                    
                case 2: 
                    
                    Review.updateReview(reviews); 
                    break;
                    
                case 3:
                    
                    Review.showReviewsByCustomer(reviews); 
                    break;

                case 4:
                    
                    System.out.print("Enter Product ID: ");
                    String id = input.nextLine();
                    System.out.print("Average rating for product " + id +" is: ");
                    System.out.println(Review.getAverageRatingForProduct(reviews,id)); 
                break;

                case 5:
                    
                    Review.showTop3Products(reviews,products); 
                    break;
                 
                case 6:
                    
                    Review.showCommonHighRatedProducts(reviews); 
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
