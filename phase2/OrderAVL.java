import java.time.LocalDate;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;

public class OrderAVL {
    
    //-----------attributes------------//
    private AVLTree<Order> orders = CSVReader.readOrdersAVL("datasets/orders.csv");
    private Scanner input = new Scanner(System.in);
    
    //----------- Methods ------------//

    //*************** CRUD Operations ***************//
    public Order createOrder(ProductAVL products, String customerId) {
    System.out.print("Enter Order ID: ");
    String orderId = input.nextLine();
    
    // Check if order ID already exists - O(log n)
    if (searchById(orderId) != null) {
        System.out.println("Order ID already exists!");
        return null;
    }
    
    System.out.print("Enter Order Date (YYYY-MM-DD): ");
    String orderDate = input.nextLine();
    
    // Select products 
    LinkedList<String> selectedProductIds = new LinkedList<>();
    double totalPrice = 0.0;
    
    boolean addingProducts = true;
    while (addingProducts) {
        System.out.print("Enter Product ID to add to order (or 'done' to finish): ");
        String productId = input.nextLine();
        
        if ("done".equalsIgnoreCase(productId)) {
            addingProducts = false;
            continue;
        }
        
        Product product = products.searchById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            continue;
        }
        
        if (product.isOutOfStock()) {
            System.out.println("Product is out of stock!");
            continue;
        }
        
        // Add product and update stock
        selectedProductIds.insert(productId);
        totalPrice += product.getPrice();
        product.reduceStock(1); // Decrease stock by 1
        
        System.out.println("Product added: " + product.getName() + " - $" + product.getPrice());
        System.out.print("Continue adding products? (yes/no): ");
        String continueAdding = input.nextLine();
        if ("no".equalsIgnoreCase(continueAdding)) {
            addingProducts = false;
        }
    }
    
    if (selectedProductIds.isEmpty()) {
        System.out.println("No products selected. Order creation cancelled.");
        return null;
    }
    
    // Create new order
    Order newOrder = new Order(orderId, customerId, selectedProductIds, totalPrice, orderDate, "Pending");
    orders.insert(newOrder); // O(log n)
    
    System.out.println("Order created successfully!");
    System.out.println("Order ID: " + orderId);
    System.out.println("Total Price: $" + totalPrice);
    System.out.println("Status: Pending");
    
    return newOrder; // Return the created order
}

    public void cancelOrder(ProductAVL products) {
        System.out.print("Enter Order ID to cancel: ");
        String orderId = input.nextLine();
        
        Order order = searchById(orderId); // O(log n)
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        
        // Check if order is already cancelled
        if (order.isCancelled()) {
            System.out.println("Order is already cancelled!");
            return;
        }
        
        // Check if order can be cancelled (only pending orders can be cancelled)
        if (!order.isPending()) {
            System.out.println("Cannot cancel order. Current status: " + order.getStatus());
            return;
        }
        
        // Change order status to cancelled
        order.setStatus("Cancelled");
        
        // Get the products and increase stock
        LinkedList<String> productIds = order.getProductIDs();
        productIds.findFirst();
        while (true) {
            String productId = productIds.retrieve();
            Product product = products.searchById(productId);
            if (product != null) {
                product.addStock(1); // Increase stock by 1
                System.out.println("Stock increased for product: " + product.getName());
            }
            if (productIds.last()) break;
            productIds.findNext();
        }
        
        System.out.println("Order " + orderId + " has been cancelled successfully!");
        System.out.println("Stock has been updated for all products in the order.");
    }

    public void updateOrderStatus() {
        System.out.print("Enter Order ID: ");
        String orderId = input.nextLine();
        
        Order order = searchById(orderId); // O(log n)
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        
        System.out.println("Current status: " + order.getStatus());
        System.out.println("Available status: Pending, Shipped, Delivered, Cancelled");
        System.out.print("Enter new status: ");
        String newStatus = input.nextLine();
        
        // Validate status
        if (isValidStatus(newStatus)) {
            order.setStatus(newStatus);
            System.out.println("Order status updated successfully to: " + newStatus);
        } else {
            System.out.println("Invalid status! Please use: Pending, Shipped, Delivered, or Cancelled");
        }
    }

    //*************** Search Operations ***************//
    
    public void searchOrderById() {
        System.out.print("Enter Order ID: ");
        String orderId = input.nextLine();
        
        Order order = searchById(orderId);
        if (order != null) {
            System.out.println("\nOrder Found:");
            displayOrderDetails(order);
        } else {
            System.out.println("Order not found!");
        }
    }
    
    private Order searchById(String orderId) {
        Order temp = new Order();
        temp.setOrderID(orderId);
        return orders.search(temp); // O(log n)
    }
    
    public void searchOrdersByCustomerId(String id) {
        
        LinkedList<Order> customerOrders = getOrdersByCustomerId(id);
        
        if (customerOrders.isEmpty()) {
            System.out.println("No orders found for customer: " + id);
            return;
        }
        
        System.out.println("\n=== Orders for Customer " + id + " ===");
        displayOrderTable(customerOrders, "Customer Orders");
    }
    
    public LinkedList<Order> getOrdersByCustomerId(String customerId) {
        LinkedList<Order> result = new LinkedList<>();
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return result;
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            if (order.getCustomerID().equals(customerId)) {
                result.insert(order);
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return result;
    }

    //*************** Advanced Queries ***************//
    
    public void searchOrdersByDateRange() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = input.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = input.nextLine();
        
        LinkedList<Order> ordersInRange = getOrdersBetweenDates(startDate, endDate);
        
        if (ordersInRange.isEmpty()) {
            System.out.println("No orders found between " + startDate + " and " + endDate);
            return;
        }
        
        System.out.println("\n=== Orders Between " + startDate + " and " + endDate + " ===");
        displayOrderTable(ordersInRange, "Date Range Orders");
    }
    
    private LinkedList<Order> getOrdersBetweenDates(String startDate, String endDate) {
        LinkedList<Order> result = new LinkedList<>();

        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
        
            if (start.isAfter(end)) {
                System.out.println("Error: Start date must be before end date.");
                return result;
            }
            
            // Get all orders using in-order traversal
            LinkedList<Order> allOrders = orders.inOrderTraversal();
            
            // Filter by date range
            allOrders.findFirst();
            while (true) {
                Order order = allOrders.retrieve();
                String orderDateStr = order.getOrderDate();
            
                if (orderDateStr != null) {
                    try {
                        LocalDate orderDate = LocalDate.parse(orderDateStr);
                        if (!orderDate.isBefore(start) && !orderDate.isAfter(end)) {
                            result.insert(order);
                        }
                    } catch (DateTimeParseException e) {
                        // Skip orders with invalid dates
                    }
                }
                if (allOrders.last()) break;
                allOrders.findNext();
            }
        
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD (e.g., 2025-01-15).");
        }
    
        return result;    
    }
    
    public void searchOrdersByStatus() {
        System.out.println("Available status: Pending, Shipped, Delivered, Cancelled");
        System.out.print("Enter status to search: ");
        String status = input.nextLine();
        
        if (!isValidStatus(status)) {
            System.out.println("Invalid status!");
            return;
        }
        
        LinkedList<Order> statusOrders = getOrdersByStatus(status);
        
        if (statusOrders.isEmpty()) {
            System.out.println("No orders found with status: " + status);
            return;
        }
        
        System.out.println("\n=== Orders with Status: " + status + " ===");
        displayOrderTable(statusOrders, "Status Orders");
    }
    
    private LinkedList<Order> getOrdersByStatus(String status) {
        LinkedList<Order> result = new LinkedList<>();
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return result;
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            if (order.getStatus().equalsIgnoreCase(status)) {
                result.insert(order);
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return result;
    }
    
    public void searchOrdersByPriceRange() {
        System.out.print("Enter minimum price: ");
        double minPrice = input.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = input.nextDouble();
        input.nextLine(); // Clear buffer
        
        LinkedList<Order> priceOrders = getOrdersInPriceRange(minPrice, maxPrice);
        
        if (priceOrders.isEmpty()) {
            System.out.println("No orders found in price range $" + minPrice + " - $" + maxPrice);
            return;
        }
        
        System.out.println("\n=== Orders in Price Range $" + minPrice + " - $" + maxPrice + " ===");
        displayOrderTable(priceOrders, "Price Range Orders");
    }
    
    private LinkedList<Order> getOrdersInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Order> result = new LinkedList<>();
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return result;
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            double price = order.getTotalPrice();
            if (price >= minPrice && price <= maxPrice) {
                result.insert(order);
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return result;
    }

    //*************** Display Methods ***************//
    
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("\n=== All Orders (Sorted by Order ID) ===");
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        displayOrderTable(allOrders, "All Orders");
    }
    
    public void displayOrderTable(LinkedList<Order> orderList, String title) {
        if (orderList.isEmpty()) {
            System.out.println("No orders to display.");
            return;
        }
        
        System.out.println("┌─────┬────────────┬────────────┬──────────┬────────────┬───────────┬─────────┐");
        System.out.println("│ No. │ Order ID   │ Customer ID│ Total    │ Date       │ Status    │ Products│");
        System.out.println("├─────┼────────────┼────────────┼──────────┼────────────┼───────────┼─────────┤");
        
        int count = 0;
        orderList.findFirst();
        while (true) {
            count++;
            Order order = orderList.retrieve();
            System.out.printf("│ %-3d │ %-10s │ %-10s │ $%-7.2f │ %-10s │ %-9s │ %-7d │\n",
                count, order.getOrderID(), order.getCustomerID(),
                order.getTotalPrice(), order.getOrderDate(),
                order.getStatus(), order.getProductCount());
            
            if (orderList.last()) break;
            orderList.findNext();
        }
        
        System.out.println("└─────┴────────────┴────────────┴──────────┴────────────┴───────────┴─────────┘");
        System.out.println("\nTotal: " + count + " orders");
    }
    
    private void displayOrderDetails(Order order) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                       ORDER DETAILS                     │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.printf("│ Order ID:    %-40s   │\n", order.getOrderID());
        System.out.printf("│ Customer ID: %-40s   │\n", order.getCustomerID());
        System.out.printf("│ Total Price: $%-38.2f    │\n", order.getTotalPrice());
        System.out.printf("│ Date:        %-40s   │\n", order.getOrderDate());
        System.out.printf("│ Status:      %-40s   │\n", order.getStatus());
        System.out.printf("│ Products:    %-40d   │\n", order.getProductCount());
        System.out.println("├─────────────────────────────────────────────────────────┤");
        
        // Display products in this order
        if (!order.getProductIDs().isEmpty()) {
            System.out.println("│ Products in this order:                                 │");
            order.getProductIDs().findFirst();
            while (true) {
                System.out.printf("│   - %-45s       │\n", order.getProductIDs().retrieve());
                if (order.getProductIDs().last()) break;
                order.getProductIDs().findNext();
            }
        }
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    //*************** Statistics Methods ***************//
    
    public void displayOrderStatistics() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("\n=== ORDER STATISTICS ===");
        System.out.println("Total Orders: " + getOrderCount());
        System.out.printf("Total Revenue: $%.2f\n", getTotalRevenue());
        System.out.println("Pending Orders: " + getOrderCountByStatus("Pending"));
        System.out.println("Shipped Orders: " + getOrderCountByStatus("Shipped"));
        System.out.println("Delivered Orders: " + getOrderCountByStatus("Delivered"));
        System.out.println("Cancelled Orders: " + getOrderCountByStatus("Cancelled"));
        
        // Display top 3 highest value orders
        displayTop3HighestValueOrders();
    }
    
    private double getTotalRevenue() {
        double total = 0.0;
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return 0.0;
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            // Only count delivered orders for revenue
            if (order.isDelivered()) {
                total += order.getTotalPrice();
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return total;
    }
    
    private int getOrderCountByStatus(String status) {
        int count = 0;
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return 0;
        
        allOrders.findFirst();
        while (true) {
            if (allOrders.retrieve().getStatus().equalsIgnoreCase(status)) {
                count++;
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return count;
    }
    
    private void displayTop3HighestValueOrders() {
        LinkedList<Order> allOrders = orders.inOrderTraversal();
        
        if (allOrders.isEmpty()) return;
        
        // Convert to array for sorting
        Order[] orderArray = listToArray(allOrders);
        
        // Sort by total price (descending)
        sortByTotalPrice(orderArray);
        
        System.out.println("\n=== Top 3 Highest Value Orders ===");
        int count = Math.min(3, orderArray.length);
        for (int i = 0; i < count; i++) {
            System.out.printf("%d. Order %s - $%.2f\n", 
                i + 1, orderArray[i].getOrderID(), orderArray[i].getTotalPrice());
        }
    }

    //*************** Helper Methods ***************//
    
    private boolean isValidStatus(String status) {
        return "Pending".equalsIgnoreCase(status) ||
               "Shipped".equalsIgnoreCase(status) ||
               "Delivered".equalsIgnoreCase(status) ||
               "Cancelled".equalsIgnoreCase(status);
    }
    
    private Order[] listToArray(LinkedList<Order> orderList) {
        if (orderList.isEmpty()) {
            return new Order[0];
        }
        
        // First, count the elements
        int size = 0;
        orderList.findFirst();
        while (true) {
            size++;
            if (orderList.last()) break;
            orderList.findNext();
        }

        // Create array and populate it
        Order[] arr = new Order[size];
        orderList.findFirst();
        for (int i = 0; i < size; i++) {
            arr[i] = orderList.retrieve();
            if (!orderList.last()) orderList.findNext();
        }
        return arr;
    }

    private void sortByTotalPrice(Order[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].getTotalPrice() < arr[j + 1].getTotalPrice()) {
                    Order temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    //*************** Utility Methods ***************//
    
    public boolean isEmpty() {
        return orders.isEmpty();
    }
    
    public int getOrderCount() {
        return orders.size();
    }
    
    public AVLTree<Order> getOrdersTree() {
        return orders;
    }
    
    public LinkedList<Order> getAllOrders() {
        return orders.inOrderTraversal();
    }
}
