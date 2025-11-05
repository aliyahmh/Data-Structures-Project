import java.util.*;

public class Order {
    private String orderID;
    private String customerID; 
    private DoubleLinkedList<String> productIDs; // quicker search by id
    private double totalPrice;
    private String orderDate; 
    private String status;
    

    public Order(String orderID, String customerID, DoubleLinkedList<String> productIDs, double totalPrice, String orderDate, String status){
        this.orderID = orderID;
        this.customerID = customerID;
        productIDs = new DoubleLinkedList<>();
        totalPrice = 0;
        this.orderDate = orderDate;
        status = "pending";
    }

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


    
}
