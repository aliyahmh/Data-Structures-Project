import java.util.Scanner;

public class Product {

    //------------------------ Attributes ------------------------
    private String productId;
    private String name;
    private double price;
    private int stock;

    private LinkedList<Review> reviews = new LinkedList<>();

//-------------------List of product from csvReader---------------
    private static LinkedList<Product> products;
    private static Scanner input = new Scanner(System.in);

    //------------------------ Constructors ------------------------
    public Product() {}

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    //------------------------ Getters and Setters ------------------------
    public String getProductId() { 
        return productId; }
    public void setProductId(String productId) {
         this.productId = productId; }

    public String getName() { 
        return name; }
    public void setName(String name) { 
        this.name = name; }

    public double getPrice() {
         return price; }
    public void setPrice(double price) { 
        this.price = price; }

    public int getStock() { 
        return stock; }
    public void setStock(int stock) {
         this.stock = stock; }

    public LinkedList<Review> getReviews() {
         return reviews; }

    //------------------------ Reviews Operations ------------------------
    //---------add new riv-----
    public void addReview(Review r) {
        reviews.insert(r);
    }

    //------------------------ Static Methods ---------------------------

    // -----------------------CSVReader------------------------------------
    public static void setProductList(LinkedList<Product> listFromCSV) {
        products = listFromCSV;
    }
//            *********************************************************
    // إضافة منتج جديد
    public static void addProduct() {
        System.out.print("Enter Product ID: ");
        String id = input.nextLine();

        while (checkProductID(id)) {
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

        System.out.println(" Product added successfully!");
    }

//            *********************************************************
    public static void removeProduct() {
        if (products.empty()) {
            System.out.println(" No products available!");
            return;
        }

        System.out.print("Enter Product ID to remove: ");
        String id = input.nextLine();

        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (p.getProductId().equals(id)) {
                products.remove();
                System.out.println(" Product removed!");
                return;
            }
            if (products.last()) break;
            products.findNext();
        }

        System.out.println(" Product not found!");
    }

//            *********************************************************

    public static void updateProduct() {
        if (products.empty()) {
            System.out.println(" No products available!");
            return;}
        System.out.print("Enter Product ID to update: ");
        String id = input.nextLine();
        Product p = searchById(id);
        if (p == null) {
            System.out.println("Product not found!");
            return;}
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
                break;
            case 2:
                System.out.print("Enter new price: ");
                p.setPrice(input.nextDouble());
                input.nextLine();
                break;
            case 3:
                System.out.print("Enter new stock: ");
                p.setStock(input.nextInt());
                input.nextLine();
                break;
            default:
                System.out.println(" Invalid choice!");}
        System.out.println(" Product updated!");}


//    ----------------search by id to update -------------
    public static Product searchById(String id) {
        if (products.empty()) return null;

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
//            *********************************************************

    public static Product searchByName(String name) {
        if (products.empty())
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

//            *********************************************************
    public static void showOutOfStock() {
        if (products.empty()) {
            System.out.println(" No products available!");
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
            System.out.println(" No out-of-stock products.");
    }

//            *********************************************************
// check dublicate id 
    public static boolean checkProductID(String id) {
    if (products == null || products.empty()) {
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



//            *********************************************************
  public static boolean checkProductID(String id) {
        if (products.empty()) {
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


   


//            *********************************************************
    @Override
    public String toString() {
        return "Product ID: " + productId +
               ", Name: " + name +
               ", Price: " + price +
               ", Stock: " + stock;
    }




}
