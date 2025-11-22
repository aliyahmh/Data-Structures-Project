import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
     
    
    //-------------------Product Reader-------------------//
    
    public static LinkedList<Product> readProducts(String filename) {
        LinkedList<Product> products = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String productId = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int stock = Integer.parseInt(data[3].trim());
                    
                    products.insert(new Product(productId, name, price, stock));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products file: " + e.getMessage());
        }
        
        return products;
    }
    
    //-------------------Customers Reader-------------------//
    
    public static LinkedList<Customer> readCustomers(String filename) {
        LinkedList<Customer> customers = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String customerId = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    
                    customers.insert(new Customer(customerId, name, email));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customers file: " + e.getMessage());
        }
        
        return customers;
    }
    
    //-------------------Orders Reader-------------------//
    
    
    public static LinkedList<Order> readOrders(String filename) {
        LinkedList<Order> orders = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String orderId = data[0].trim();
                    String customerId = data[1].trim();
                    
                    // Parse product IDs (semicolon separated)
                    LinkedList<String> productIds = new LinkedList<>();
                    String[] ids = data[2].replace("\"", "").split(";");
                    for (String id : ids) {
                        productIds.insert(id.trim());
                    }
                    
                    double totalPrice = Double.parseDouble(data[3].trim());
                    String orderDate = data[4].trim();
                    String status = data[5].trim();
                    
                    orders.insert(new Order(orderId, customerId, productIds, totalPrice, orderDate, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file: " + e.getMessage());
        }
        
        return orders;
    }
    
    //-------------------Reviews Reader-------------------//
    
    
    public static LinkedList<Review> readReviews(String filename) {
        LinkedList<Review> reviews = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",", 5); // Split into max 5 parts
                if (data.length >= 5) {
                    String reviewId = data[0].trim();
                    String productId = data[1].trim();
                    String customerId = data[2].trim();
                    int rating = Integer.parseInt(data[3].trim());
                    String comment = data[4].trim().replace("\"", "");
                    
                    reviews.insert(new Review(reviewId, productId, customerId, rating, comment));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file: " + e.getMessage());
        }
        
        return reviews;
    }
}
