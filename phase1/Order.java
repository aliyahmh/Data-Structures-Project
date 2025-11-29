import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Order {
    
    //-----------attributes------------//
    
    private String orderID;
    private String customerID; 
    private LinkedList<String> productIDs; // quicker search by id
    private double totalPrice;
    private String orderDate; 
    private String status;

    // scanner //
    private static Scanner in = new Scanner(System.in);
    
    
    //-----------constructors------------//
    
    public Order() {
        this.orderID = "";
        this.customerID = "";
        this.totalPrice = 0.0;
        this.orderDate = "";
        this.status = "";
    }
    
    public Order(String orderID, String customerID, LinkedList<String> productIDs, double totalPrice, String orderDate, String status){
        this.orderID = orderID;
        this.customerID = customerID;
        this.productIDs = productIDs;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    //-----------methods------------//
    
    public static void cancelOrder(LinkedList<Order> allOrders,LinkedList<Product> products) {
        System.out.print("Enter Order ID to cancel: ");
        String orderId = in.nextLine();
        
        Order order = searchOrderById(allOrders,orderId);
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
        LinkedList<String> productIds = order.getProductIDs();
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


    public static void updateOrderStatus(LinkedList<Order> allOrders) {
        System.out.print("Enter Order ID: ");
        String orderId = in.nextLine();
        
        Order order = searchOrderById(allOrders,orderId);
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



    public static Order searchOrderById(LinkedList<Order> allOrders,String orderId) {
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


    public static void displayOrdersBetweenDates(LinkedList<Order> allOrders) {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = in.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = in.nextLine();
        
        LinkedList<Order> ordersInRange = getOrdersBetweenDates(allOrders,startDate, endDate);
        
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





    public static Order createOrder(LinkedList<Product> products,LinkedList<Order> allOrders, String customerId) {
        System.out.print("Enter Order ID: ");
        String orderId = in.nextLine();
        
        // check if order ID already exists
        if (validOrder(allOrders,orderId)) {
            System.out.println("Order ID already exists!");
            return null;
        }
        
        System.out.print("Enter Order Date (YYYY-MM-DD): ");
        String orderDate = in.nextLine();
        
        // select products 
        LinkedList<String> selectedProductIds = new LinkedList<>();
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



    public static void displayAllOrders(LinkedList<Order> allOrders) {
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


    // helper methods //
    
    // checks if an order w this id exists 
    
    public static boolean validOrder(LinkedList<Order> allOrders,String orderId) { 
        return searchOrderById(allOrders,orderId) != null;
    }


    public static LinkedList<Order> getOrdersBetweenDates(LinkedList<Order> allOrders,String startDate, String endDate) {
        LinkedList<Order> result = new LinkedList<>();



        try {
            LocalDate start = LocalDate.parse(startDate); // used it for easier validation
            LocalDate end = LocalDate.parse(endDate);
        
            if (start.isAfter(end)) {
                System.out.println("Error: Start date must be before end date.");
                return result;
            }
        
        
            Node<Order> current = allOrders.getHead();
            while (current != null) {
                Order order = current.data;
                String orderDateStr = order.getOrderDate();
            
                if (orderDateStr != null) {
                    try {
                        LocalDate orderDate = LocalDate.parse(orderDateStr);
                        if (!orderDate.isBefore(start) && !orderDate.isAfter(end)) {
                            result.insert(order);
                        }
                    } catch (DateTimeParseException e) {
                    // skip orders with invalid dates
                    }
                }
                current = current.next;
            }
        
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD (e.g., 2025-01-15).");
            return result;
        }
    
        return result;    
    }


    private static Product findProductById(LinkedList<Product> products, String productId) {
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
    
    //----------- Getters ------------//
    
    public String getOrderID() {
         return orderID; 
    }

    public String getCustomerID() {
         return customerID; 
    }

    public LinkedList<String> getProductIDs() {
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

    //----------- Setters ------------//
    
    public void setStatus(String status) {
         this.status = status;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setProductIDs(LinkedList<String> productIDs) {
        this.productIDs = productIDs;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    

    @Override
    public String toString() {
        return "Order ID: " + orderID + ", Customer: " + customerID + 
               ", Total: $" + totalPrice + ", Date: " + orderDate + ", Status: " + status;
    }
   
}
