import java.util.*;

public class Order {
    private String orderId;
    private Customer customer; 
    private LinkedList<Product> products; // quicker search by id
    private double totalPrice;
    private String orderDate; 
    private String status;



    private static LinkedList<Order> orders = new LinkedList<>();
    private static Queue<Order> pendingOrders = new LinkedList<>(); // orders are processed using FIFO logic
    
    


    public Order(String orderId, Customer customer, String orderDate){
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        status = "pending";
        totalPrice = 0;
        products = new LinkedList<>();

    }


    public Order createOrder(String orderId, Customer customer, String orderDate){
        if (findOrderById(orderId) == null){
            Order o = new Order(orderId, customer, orderDate);
            orders.add(o);
            pendingOrders.add(o);
            System.out.println("order No. "+orderId+" was successfully created!");
            return o;
        }
        else {
            System.out.println("An order with this ID already exists.");
            return null ;
        }

    }


    
    /*public Order findOrderById(String id){



    }*/







    
}
