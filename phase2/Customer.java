public class Customer {
    
    //-----------attributes------------//
    
    private String customerid;
    private String name;
    private String email;
    // Customer still stores his OWN orders as a linked list 
    private LinkedList<Order> ordersList ;
    
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
        this.ordersList  = new LinkedList<>();
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





