import java.util.*;

public class Order {
    private String orderID;
    private String customerID; 
    private DoubleLinkedList<String> productIDs; // quicker search by id
    private double totalPrice;
    private String orderDate; 
    private String status;

    /* scanner */
    private static Scanner in = new Scanner(System.in);
    private static DoubleLinkedList<Order> allOrders = new DoubleLinkedList<>();
    

    public Order(String orderID, String customerID, DoubleLinkedList<String> productIDs, double totalPrice, String orderDate, String status){
        this.orderID = orderID;
        this.customerID = customerID;
        this.productIDs = productIDs;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    /* setters & getters */
    public String getOrderID() {
         return orderID; 
    }

    public String getCustomerID() {
         return customerID; 
    }

    public DoubleLinkedList<String> getProductIDs() {
         return productIDs; 
    }

    public double getTotalPrice() {
         return totalPrice; 
    }

    public String getOrderDate() {
         return orderDate; 
    }

    public String getStatus() {
         return status; 
    }

    public void setStatus(String status) {
         this.status = status;
    }

       public static void setAllOrders(DoubleLinkedList<Order> orders) {
        allOrders = orders;
    }
    
    public static DoubleLinkedList<Order> getAllOrders() {
        return allOrders;
    }



    @Override
    public String toString() {
        return "Order ID: " + orderID + ", Customer: " + customerID + 
               ", Total: $" + totalPrice + ", Date: " + orderDate + ", Status: " + status;
    }



    
    
    
    public static void cancelOrder(DoubleLinkedList<Product> products) {
        System.out.print("Enter Order ID to cancel: ");
        String orderId = in.nextLine();
        
        Order order = searchOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        
        // Check if order is already cancelled
        if ("Cancelled".equalsIgnoreCase(order.getStatus())) {
            System.out.println("Order is already cancelled!");
            return;
        }
        
        // Check if order can be cancelled (only pending orders can be cancelled)
        if (!"Pending".equalsIgnoreCase(order.getStatus())) {
            System.out.println("Cannot cancel order. Current status: " + order.getStatus());
            return;
        }
   
        // Change order status to cancelled
        order.setStatus("Cancelled");
        
        // Get the products and increase stock
        DoubleLinkedList<String> productIds = order.getProductIDs();
        Node<String> productNode = productIds.getHead();
        while (productNode != null) {
            String productId = productNode.data;
            Product product = findProductById(products, productId);
            if (product != null) {
                product.setStock(product.getStock() + 1); // Increase stock by 1
                System.out.println("Stock increased for product: " + product.getName());
            }
            productNode = productNode.next;
        }
        
        System.out.println("Order " + orderId + " has been cancelled successfully!");
        System.out.println("Stock has been updated for all products in the order.");
    }


    

    public static void updateOrderStatus() {
        System.out.print("Enter Order ID: ");
        String orderId = in.nextLine();
        
        Order order = searchOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        
        System.out.println("Current status: " + order.getStatus());
        System.out.println("Available status: Pending, Shipped, Delivered, Cancelled");
        System.out.print("Enter new status: ");
        String newStatus = in.nextLine();
        
        // Validate status
        if (validStatus(newStatus)) {
            order.setStatus(newStatus);
            System.out.println("Order status updated successfully to: " + newStatus);
        } else {
            System.out.println("Invalid status! Please use: Pending, Shipped, Delivered, or Cancelled");
        }
    }



    public static Order searchOrderById(String orderId) {
        if (allOrders.isEmpty()) {
            return null;
        }
        
        Node<Order> current = allOrders.getHead();
        while (current != null) {
            if (current.data.getOrderID().equals(orderId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }


    public static void displayOrdersBetweenDates() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = in.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = in.nextLine();
        
        DoubleLinkedList<Order> ordersInRange = getOrdersBetweenDates(startDate, endDate);
        
        if (ordersInRange.isEmpty()) {
            System.out.println("No orders found between " + startDate + " and " + endDate);
            return;
        }
        
        System.out.println("\n=== ORDERS BETWEEN " + startDate + " AND " + endDate + " ===");
        Node<Order> current = ordersInRange.getHead();
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }





    public static Order createOrder(DoubleLinkedList<Product> products, String customerId) {
        System.out.print("Enter Order ID: ");
        String orderId = in.nextLine();
        
        // check if order ID already exists
        if (validOrder(orderId)) {
            System.out.println("Order ID already exists!");
            return null;
        }
        
        System.out.print("Enter Order Date (YYYY-MM-DD): ");
        String orderDate = in.nextLine();
        
        // select products 
        DoubleLinkedList<String> selectedProductIds = new DoubleLinkedList<>();
        double totalPrice = 0.0;
        
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.print("Enter Product ID to add to order (or 'done' to finish): ");
            String productId = in.nextLine();
            String input = productId;
            
            if ("done".equalsIgnoreCase(input)) {
                addingProducts = false;
                continue;
            }
            
            Product product = findProductById(products, productId);
            if (product == null) {
                System.out.println("Product not found!");
                continue;
            }
            
            if (product.getStock() == 0) {
                System.out.println("Product is out of stock!");
                continue;
            }
            
            // add product and update stock
            selectedProductIds.insert(productId);
            totalPrice += product.getPrice();
            product.setStock(product.getStock() - 1); // decrease stock by 1
            
            System.out.println("Product added: " + product.getName() + " - $" + product.getPrice());
            System.out.println("Continue adding products? (yes/no): ");
            String continueAdding = in.nextLine();
            if ("no".equalsIgnoreCase(continueAdding)) {
                addingProducts = false;
            }
        }
        
        if (selectedProductIds.isEmpty()) {
            System.out.println("No products selected. Order creation cancelled.");
            return null;
        }
        
        // create new order
        Order newOrder = new Order(orderId, customerId, selectedProductIds, totalPrice, orderDate, "Pending");
        allOrders.insert(newOrder);
        
        System.out.println("Order created successfully!");
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Status: Pending");
        
        return newOrder;
    }



        public static void displayAllOrders() {
        if (allOrders.isEmpty()) {
            System.out.println("No orders available!");
            return;
        }
        
        System.out.println("\n=== ALL ORDERS ===");
        Node<Order> current = allOrders.getHead();
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }


    /* helper methods */
    
      public static boolean validOrder(String orderId) { // checks if an order w this id exists
        return searchOrderById(orderId) != null;
    }


    public static DoubleLinkedList<Order> getOrdersBetweenDates(String startDate, String endDate) {
        DoubleLinkedList<Order> result = new DoubleLinkedList<>();
        
        if (allOrders.isEmpty()) {
            return result;
        }
        
        Node<Order> current = allOrders.getHead();
        while (current != null) {
            String orderDate = current.data.getOrderDate();
            // (assuming dates are in YYYY-MM-DD format)
            if (orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0) { //
                result.insert(current.data);
            }
            current = current.next;
        }
        
        return result;
    }


    private static Product findProductById(DoubleLinkedList<Product> products, String productId) {
        if (products.isEmpty()) {
            return null;
        }
        
        Node<Product> current = products.getHead();
        while (current != null) {
            if (current.data.getProductId().equals(productId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    private static boolean validStatus(String status) {
        return "Pending".equalsIgnoreCase(status) ||
               "Shipped".equalsIgnoreCase(status) ||
               "Delivered".equalsIgnoreCase(status) ||
               "Cancelled".equalsIgnoreCase(status);
    }



     



    
}
