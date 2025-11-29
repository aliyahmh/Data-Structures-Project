import java.util.Comparator;


public class Product {

    //-----------attributes------------//
    private String productId;
    private String name;
    private double price;
    private int stock;
    
    // Reviews stored in AVL Tree
    private LinkedList<Review> reviews;
    private ReviewAVL revavl = new ReviewAVL();

    //-----------constructors------------//
    public Product() {
        this.productId = "";
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
        this.reviews = new LinkedList<>();
    }

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = revavl.getReviewsByProduct(productId);
    }

    //-----------Business Logic Methods------------//
    
 
    public void addReview(Review r) {
        reviews.insert(r);
    }
    
  
    public int getReviewCount() {
        return reviews.size();
    }
    
  
    public LinkedList<Review> getReviewsList() {
        return reviews;
    }
  
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        
        double sum = 0;
        int count = 0;
        
        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            sum += r.getRating();
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        return count > 0 ? sum / count : 0.0;
    }
    

    public boolean isOutOfStock() {
        return stock == 0;
    }

    public boolean isInPriceRange(double minPrice, double maxPrice) {
        return price >= minPrice && price <= maxPrice;
    }
    
 
    public boolean reduceStock(int quantity) {
        if (quantity > stock) {
            return false;
        }
        stock -= quantity;
        return true;
    }
    

    public void addStock(int quantity) {
        stock += quantity;
    }

    //-----------Setters------------//
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //-----------Getters------------//
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }


    @Override
    public String toString() {
        return "Product ID: " + productId +
               ", Name: " + name +
               ", Price: $" + price +
               ", Stock: " + stock +
               ", Reviews: " + getReviewCount() +
               ", Avg Rating: " + String.format("%.1f", getAverageRating());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId.equals(product.productId);
    }
    
    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
