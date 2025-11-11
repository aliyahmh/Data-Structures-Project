public class Customer {
    
    //-----------attributes------------//
    
    private String customerid;
    private String name;
    private String email;
    private DoubleLinkedList<Order> ordersList;
    
    //-----------constructor------------//
    
            //*register customer*//
    
   public Customer(String id, String name, String email){
       this.customerid = id;
       this.name = name;
       this.email = email;
       this.ordersList= new DoubleLinkedList<>();              
   }
   
   //-----------methods------------//
   
   //*****Place new order for customer*****//

    public void placeOrder(Order order) {
        if (order != null && order.getCustomerID().equals(this.customerid)) {
            ordersList.insert(order);
            System.out.println("Order " + order.getOrderID() + " placed successfully for customer " + this.name);
        } else {
            System.out.println("Order does not belong to this customer or is invalid.");
        }
    }
   
   //*****View order history*****//
   
   public void viewOrdersHistory(){
       Node <Order> order = ordersList.getHead();
       if (order == null){
           System.out.println("No Orders found for customer "+this.name);
           return;
       }
       while (order != null){
           Order o = order.data;
           System.out.println("Order ID: " + o.getOrderID() +
                               " | Status: " + o.getStatus() +
                               " | Date: " + o.getOrderDate() +
                               " | Total: " + o.getTotalPrice());
           order = order.next;
       }
   }
   
   //----------- Getters ------------//
   
   public String getCustomerId(){
       return customerid;
   }
   
   public String getName(){
       return name;
   }
   
   public String getEmail(){
       return email;
   }
   
   public DoubleLinkedList<Order> getOrderHistory(){
       return ordersList;             
   }

   @Override
   public String toString() {
    return "Customer [customerid=" + customerid + ", name=" + name + ", email=" + email + "]";
   }
    


   
}
