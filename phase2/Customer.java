public class Customer {
    
    //-----------attributes------------//
    
    private String customerid;
    private String name;
    private String email;
    // Customer still stores his OWN orders as a linked list 
    private LinkedList<Order> ordersList ;
    private OrderAVL orderavl = new OrderAVL();
    //-----------constructors------------//
          
    public Customer(){
       this.customerid ="";
       this.name = "";
       this.email = "";
       this.ordersList  = new LinkedList<>();
    }
    
   public Customer(String id, String name, String email){
       this.customerid = id;
       this.name = name;
       this.email = email;
       this.ordersList  = orderavl.getOrdersByCustomerId(id);
   }
   
   
    //*****Place new order for customer*****//
   
   public void placeOrder(Order order) {
        if (order != null && order.getCustomerID().equals(this.customerid)) {
            ordersList.insert(order);
        } else {
            System.out.println("Order does not belong to this customer.");
        }
    }

public LinkedList<Order> getOrdersList() {
        return ordersList;
    }


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


   //----------- Setters ------------//
    
   public void setCustomerId(String id){
       customerid = id;
   }
   
   public void setName(String cname){
       name = cname;
   }
   
   public void setEmail(String cemail){
       email = cemail;
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
   

   @Override
   public String toString() {
    return "Customer [customerid=" + customerid + ", name=" + name + ", email=" + email + "]";
   }
    
   
}
