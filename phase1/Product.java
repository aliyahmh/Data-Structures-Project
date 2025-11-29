import java.util.Scanner;

public class Product {

    //-----------attributes------------//
    private String productId;
    private String name;
    private double price;
    private int stock;

    private LinkedList<Review> reviews = new LinkedList<>();


    private static Scanner input = new Scanner(System.in);

    //-----------constructors------------//
   
    public Product() {
        this.productId = "";
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
    }

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    
    //-----------methods------------//
   
    
    //---------add new riv-----
    public void addReview(Review r) {
        reviews.insert(r);
    }
    
    // إضافة منتج جديد
    public static void addProduct(LinkedList<Product>products) {
        System.out.print("Enter Product ID: ");
        String id = input.nextLine();

        while (checkProductID(products,id)) {
            System.out.print("This ID already exists, enter another: ");
            id = input.nextLine();
        }

        System.out.print("Enter Product Name: ");
        String name = input.nextLine();

        System.out.print("Enter Price: ");
        double price = input.nextDouble();

        System.out.print("Enter Stock: ");
        int stock = input.nextInt();
        input.nextLine();

        Product p = new Product(id, name, price, stock);
        products.insert(p);

        System.out.println("Product added successfully!");
    }

    
    public static void removeProduct(LinkedList<Product>products) {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        System.out.print("Enter Product ID to remove: ");
        String id = input.nextLine();

        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (p.getProductId().equals(id)) {
                products.remove();
                System.out.println("Product removed!");
                return;
            }
            if (products.last()) break;
            products.findNext();
        }

        System.out.println("Product not found!");
    }


    public static void updateProduct(LinkedList<Product>products) {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        System.out.print("Enter Product ID to update: ");
        String id = input.nextLine();

        Product p = searchById(products,id);
        if (p == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("1. Update Name");
        System.out.println("2. Update Price");
        System.out.println("3. Update Stock");
        System.out.print("Enter choice: ");
        int ch = input.nextInt();
        input.nextLine();

        switch (ch) {
            case 1:
                System.out.print("Enter new name: ");
                p.setName(input.nextLine());
                System.out.println("Product updated!");
                break;
            case 2:
                System.out.print("Enter new price: ");
                p.setPrice(input.nextDouble());
                input.nextLine();
                System.out.println("Product updated!");
                break;
            case 3:
                System.out.print("Enter new stock: ");
                p.setStock(input.nextInt());
                input.nextLine();
                System.out.println("Product updated!");
                break;
            default:
                System.out.println("Invalid choice!");
        }

    }


    //  ----------------search by id to update -------------
    
    public static Product searchById(LinkedList<Product>products,String id) {
        if (products.isEmpty()) return null;

        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (p.getProductId().equals(id))
                return p;

            if (products.last()) break;
            products.findNext();
        }
        return null;
    }
    // *********************************************************

    public static Product searchByName(LinkedList<Product>products,String name) {
        if (products.isEmpty())
         return null;

        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (p.getName().equalsIgnoreCase(name))
                return p;

            if (products.last())
             break;
            products.findNext();
        }
        return null;
    }

    // *********************************************************
    
    public static void showOutOfStock(LinkedList<Product>products) {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        boolean found = false;
        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (p.getStock() == 0) {
                System.out.println(p);
                found = true;
            }
            if (products.last()) break;
            products.findNext();
        }

        if (!found)
            System.out.println("No out-of-stock products.");
    }

    // *********************************************************
    
    // check dublicate id 
    public static boolean checkProductID(LinkedList<Product>products,String id) {
    if (products == null || products.isEmpty()) {
        return false;
    }

    products.findFirst();
    while (true) {
        if (products.retrieve().getProductId().equalsIgnoreCase(id)) {
            return true;
        }
        if (products.last()) break;
        products.findNext();
    }

    return false;
    }

    // *********************************************************
    
    public static void displayAllProducts(LinkedList<Product>products) {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        products.findFirst();
        while (true) {
            System.out.println(products.retrieve());
            if (products.last()) break;
            products.findNext();
        }
    }
    //----------- Setters ------------//
    
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
   
   //----------- Getters ------------//
   
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
   
   public LinkedList<Review> getReviews() {
         return reviews; 
   }
   
    @Override
    public String toString() {
        return "Product ID: " + productId +
               ", Name: " + name +
               ", Price: " + price +
               ", Stock: " + stock;
    }

}
