

import java.util.Comparator;

public class Order {
    
    //-----------attributes------------//
    private String orderID;
    private String customerID; 
    private LinkedList<String> productIDs;
    private double totalPrice;
    private String orderDate; 
    private String status;

    //-----------constructors------------//
    public Order() {
        this.orderID = "";
        this.customerID = "";
        this.productIDs = new LinkedList<>();
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

    //-----------Business Logic Methods------------//
    
    public boolean isPending() {
        return "Pending".equalsIgnoreCase(status);
    }
    
    public boolean isCancelled() {
        return "Cancelled".equalsIgnoreCase(status);
    }
    
    public boolean isDelivered() {
        return "Delivered".equalsIgnoreCase(status);
    }
    
    public boolean isShipped() {
        return "Shipped".equalsIgnoreCase(status);
    }
    
    public boolean containsProduct(String productId) {
        if (productIDs.isEmpty()) return false;
        
        productIDs.findFirst();
        while (true) {
            if (productIDs.retrieve().equals(productId)) {
                return true;
            }
            if (productIDs.last()) break;
            productIDs.findNext();
        }
        return false;
    }
    
    public int getProductCount() {
        return productIDs.size();
    }

    //-----------Setters------------//
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

    public void setStatus(String status) {
        this.status = status;
    }

    //-----------Getters------------//
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

    @Override
    public String toString() {
        return "Order ID: " + orderID + 
               ", Customer: " + customerID + 
               ", Total: $" + totalPrice + 
               ", Date: " + orderDate + 
               ", Status: " + status +
               ", Products: " + getProductCount();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return orderID.equals(order.orderID);
    }
    
    @Override
    public int hashCode() {
        return orderID.hashCode();
    }
}