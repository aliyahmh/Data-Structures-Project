package com.mycompany.datastructureproject;

import java.util.Comparator;


public class Product {

    //-----------attributes------------//
    private String productId;
    private String name;
    private double price;
    private int stock;
    
    // Reviews stored in AVL Tree
    private AVLTree<Review> reviews;

    //-----------constructors------------//
    public Product() {
        this.productId = "";
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
        this.reviews = new AVLTree<>(Comparator.comparing(Review::getReviewId));
    }

    public Product(String productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new AVLTree<>(Comparator.comparing(Review::getReviewId));
    }

    //-----------Business Logic Methods------------//
    
 
    public void addReview(Review r) {
        reviews.insert(r);
    }
    
  
    public int getReviewCount() {
        return reviews.size();
    }
    
  
    public LinkedList<Review> getReviewsList() {
        return reviews.inOrderTraversal();
    }
  
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        LinkedList<Review> reviewList = reviews.inOrderTraversal();
        double sum = 0;
        int count = 0;
        
        reviewList.findFirst();
        while (true) {
            Review r = reviewList.retrieve();
            sum += r.getRating();
            count++;
            if (reviewList.last()) break;
            reviewList.findNext();
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

    public AVLTree<Review> getReviews() {
        return reviews;
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



















//import java.util.Comparator;
//import java.util.Scanner;
//
//public class Product {
//
//    //-----------attributes------------//
//    private String productId;
//    private String name;
//    private double price;
//    private int stock;   
//    // Changed to AVL Tree with Comparator for reviews
//    private AVLTree<Review> reviews;
//    private static Scanner input = new Scanner(System.in);
//
//    //-----------constructors------------//
//    public Product() {
//        this.productId = "";
//        this.name = "";
//        this.price = 0.0;
//        this.stock = 0;
//        this.reviews = new AVLTree<>(Comparator.comparing(Review::getReviewId));
//    }
//
//    public Product(String productId, String name, double price, int stock) {
//        this.productId = productId;
//        this.name = name;
//        this.price = price;
//        this.stock = stock;
//        this.reviews = new AVLTree<>(Comparator.comparing(Review::getReviewId));
//    }
//
//    //-----------methods------------//
//    
//    //***** Reviews Operations *****//
//    // O(log n) - AVL insert
//    public void addReview(Review r) {
//        reviews.insert(r);
//    }
//    
//    //***** Product CRUD Operations *****//
//    
//    // Add new product - O(log n)
//    public static void addProduct(AVLTree<Product> products) {
//        System.out.print("Enter Product ID: ");
//        String id = input.nextLine();
//
//        // Create temp product to search
//        Product temp = new Product(id, "", 0, 0);
//        if (products.search(temp) != null) {
//            System.out.println("This ID already exists!");
//            return;
//        }
//
//        System.out.print("Enter Product Name: ");
//        String name = input.nextLine();
//
//        System.out.print("Enter Price: ");
//        double price = input.nextDouble();
//
//        System.out.print("Enter Stock: ");
//        int stock = input.nextInt();
//        input.nextLine();
//
//        Product p = new Product(id, name, price, stock);
//        products.insert(p);
//
//        System.out.println("Product added successfully!");
//    }
//
//    // Remove product - O(log n)
//    public static void removeProduct(AVLTree<Product> products) {
//        if (products.isEmpty()) {
//            System.out.println("No products available!");
//            return;
//        }
//
//        System.out.print("Enter Product ID to remove: ");
//        String id = input.nextLine();
//
//        Product temp = new Product(id, "", 0, 0);
//        if (products.remove(temp)) {
//            System.out.println("Product removed successfully!");
//        } else {
//            System.out.println("Product not found!");
//        }
//    }
//
//    // Update product - O(log n) for search
//    public static void updateProduct(AVLTree<Product> products) {
//        if (products.isEmpty()) {
//            System.out.println("No products available!");
//            return;
//        }
//
//        System.out.print("Enter Product ID to update: ");
//        String id = input.nextLine();
//
//        Product p = searchById(products, id);
//        if (p == null) {
//            System.out.println("Product not found!");
//            return;
//        }
//
//        System.out.println("Current Product: " + p);
//        System.out.println("1. Update Name");
//        System.out.println("2. Update Price");
//        System.out.println("3. Update Stock");
//        System.out.print("Enter choice: ");
//        int ch = input.nextInt();
//        input.nextLine();
//
//        switch (ch) {
//            case 1:
//                System.out.print("Enter new name: ");
//                p.setName(input.nextLine());
//                System.out.println("Product updated!");
//                break;
//            case 2:
//                System.out.print("Enter new price: ");
//                p.setPrice(input.nextDouble());
//                input.nextLine();
//                System.out.println("Product updated!");
//                break;
//            case 3:
//                System.out.print("Enter new stock: ");
//                p.setStock(input.nextInt());
//                input.nextLine();
//                System.out.println("Product updated!");
//                break;
//            default:
//                System.out.println("Invalid choice!");
//        }
//    }
//
//    // Search by ID - O(log n)
//    public static Product searchById(AVLTree<Product> products, String id) {
//        Product temp = new Product(id, "", 0, 0);
//        return products.search(temp);
//    }
//
//    // Search by Name - O(n) - requires traversal
//    public static Product searchByName(AVLTree<Product> products, String name) {
//        LinkedList<Product> allProducts = products.inOrderTraversal();
//        
//        if (allProducts.isEmpty()) return null;
//        
//        allProducts.findFirst();
//        while (true) {
//            Product p = allProducts.retrieve();
//            if (p.getName().equalsIgnoreCase(name)) {
//                return p;
//            }
//            if (allProducts.last()) break;
//            allProducts.findNext();
//        }
//        return null;
//    }
//
//    //***** Advanced Queries *****//
//    
//    // Range Query by Price - O(n) but optimized with AVL structure
//    public static void searchByPriceRange(AVLTree<Product> products, double minPrice, double maxPrice) {
//        System.out.println("\n=== Products in price range [" + minPrice + " - " + maxPrice + "] ===");
//        
//        LinkedList<Product> allProducts = products.inOrderTraversal();
//        
//        if (allProducts.isEmpty()) {
//            System.out.println("No products available!");
//            return;
//        }
//        
//        boolean found = false;
//        allProducts.findFirst();
//        while (true) {
//            Product p = allProducts.retrieve();
//            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
//                System.out.println(p);
//                found = true;
//            }
//            if (allProducts.last()) break;
//            allProducts.findNext();
//        }
//        
//        if (!found) {
//            System.out.println("No products found in this price range.");
//        }
//    }
//
//    // Show out of stock products - O(n)
//    public static void showOutOfStock(AVLTree<Product> products) {
//        if (products.isEmpty()) {
//            System.out.println("No products available!");
//            return;
//        }
//
//        LinkedList<Product> allProducts = products.inOrderTraversal();
//        
//        System.out.println("\n=== Out of Stock Products ===");
//        boolean found = false;
//        
//        allProducts.findFirst();
//        while (true) {
//            Product p = allProducts.retrieve();
//            if (p.getStock() == 0) {
//                System.out.println(p);
//                found = true;
//            }
//            if (allProducts.last()) break;
//            allProducts.findNext();
//        }
//
//        if (!found) {
//            System.out.println("No out-of-stock products.");
//        }
//    }
//
//    // Display all products sorted by ID - O(n)
//    public static void displayAllProducts(AVLTree<Product> products) {
//        if (products.isEmpty()) {
//            System.out.println("No products available.");
//            return;
//        }
//
//        System.out.println("\n=== All Products (Sorted by ID) ===");
//        LinkedList<Product> allProducts = products.inOrderTraversal();
//        
//        allProducts.findFirst();
//        while (true) {
//            System.out.println(allProducts.retrieve());
//            if (allProducts.last()) break;
//            allProducts.findNext();
//        }
//    }
//
//    // Show top 3 most reviewed products - O(n)
//    public static void showTopReviewedProducts(AVLTree<Product> products) {
//        if (products.isEmpty()) {
//            System.out.println("No products available!");
//            return;
//        }
//
//        LinkedList<Product> allProducts = products.inOrderTraversal();
//        
//        // Convert to array for sorting
//        Product[] arr = listToArray(allProducts);
//        
//        // Sort by review count (descending)
//        sortByReviewCount(arr);
//
//        System.out.println("\n=== Top 3 Most Reviewed Products ===");
//        int count = Math.min(3, arr.length);
//        for (int i = 0; i < count; i++) {
//            System.out.println((i + 1) + ". " + arr[i] + 
//                             " | Reviews: " + arr[i].getReviewCount());
//        }
//    }
//
//    // Helper: Convert LinkedList to Array
//    private static Product[] listToArray(LinkedList<Product> list) {
//        int size = 0;
//        list.findFirst();
//        while (true) {
//            size++;
//            if (list.last()) break;
//            list.findNext();
//        }
//
//        Product[] arr = new Product[size];
//        list.findFirst();
//        for (int i = 0; i < size; i++) {
//            arr[i] = list.retrieve();
//            if (!list.last()) list.findNext();
//        }
//        return arr;
//    }
//
//    // Helper: Sort by review count (descending) - Bubble Sort
//    private static void sortByReviewCount(Product[] arr) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - i - 1; j++) {
//                if (arr[j].getReviewCount() < arr[j + 1].getReviewCount()) {
//                    Product temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//        }
//    }
//
//    // Get review count
//    public int getReviewCount() {
//        return reviews.size();
//    }
//    
//    // Get all reviews as LinkedList
//    public LinkedList<Review> getReviewsList() {
//        return reviews.inOrderTraversal();
//    }
//
//    //-----------Setters------------//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public void setStock(int stock) {
//        this.stock = stock;
//    }
//
//    //-----------Getters------------//
//    public String getProductId() {
//        return productId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public int getStock() {
//        return stock;
//    }
//
//    public AVLTree<Review> getReviews() {
//        return reviews;
//    }
//
//    @Override
//    public String toString() {
//        return "Product ID: " + productId +
//               ", Name: " + name +
//               ", Price: " + price +
//               ", Stock: " + stock;
//    }
//}