import java.util.Scanner;

public class tstOrder {
 
    






    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // إنشاء قائمة المنتجات التجريبية
        DoubleLinkedList<Product> products = new DoubleLinkedList<>();
        products.insert(new Product("101", "Laptop", 2500, 3));
        products.insert(new Product("102", "Mouse", 50, 10));
        products.insert(new Product("103", "Keyboard", 120, 5));

        // إنشاء قائمة الطلبات
        DoubleLinkedList<Order> orders = new DoubleLinkedList<>();
        Order.setAllOrders(orders); // نربطها بالكلاس

        int choice;
        do {
            System.out.println("\n========= ORDER MENU =========");
            System.out.println("1. Place New Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order (Status)");
            System.out.println("4. Search Order By ID (linear)");
            System.out.println("5. All Orders Between Two Dates");
            System.out.println("6. Display All Orders");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            in.nextLine(); // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter Customer ID: ");
                    String customerId = in.nextLine();
                    Order.createOrder(products, customerId);
                    break;
                }

                case 2:
                    Order.cancelOrder(products);
                    break;

                case 3:
                    Order.updateOrderStatus();
                    break;

                case 4: {
                    System.out.print("Enter Order ID: ");
                    String id = in.nextLine();
                    Order o = Order.searchOrderById(id);
                    if (o != null)
                        System.out.println("✅ Found: " + o);
                    else
                        System.out.println("❌ Order not found.");
                    break;
                }

                case 5:
                    Order.displayOrdersBetweenDates();
                    break;

                case 6:
                    Order.displayAllOrders();
                    break;

                case 7:
                    System.out.println("Exiting... 👋");
                    break;

                default:
                    System.out.println("⚠️ Invalid choice! Try again.");
            }

        } while (choice != 7);

        in.close();
    }
}






