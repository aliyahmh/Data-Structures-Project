package com.mycompany.datastructureproject;

import java.util.Comparator;
import java.util.Scanner;

public class ProductAVL {
    
    //-----------attributes------------//
    private AVLTree<Product> products = CSVReader.readProducts("prodcuts.csv");
    private Scanner input = new Scanner(System.in);
    
    //----------- Methods ------------//

    public void addProduct() {
        System.out.print("Enter Product ID: ");
        String id = input.nextLine();

        // Check if product exists - O(log n)
        Product temp = new Product(id, "", 0, 0);
        if (products.search(temp) != null) {
            System.out.println("This ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = input.nextLine();

        System.out.print("Enter Price: ");
        double price = input.nextDouble();

        System.out.print("Enter Stock: ");
        int stock = input.nextInt();
        input.nextLine(); // Clear buffer

        Product p = new Product(id, name, price, stock);
        products.insert(p); // O(log n)

        System.out.println("✓ Product added successfully!");
    }

    public void removeProduct() {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        System.out.print("Enter Product ID to remove: ");
        String id = input.nextLine();

        Product temp = new Product(id, "", 0, 0);
        if (products.remove(temp)) { // O(log n)
            System.out.println("✓ Product removed successfully!");
        } else {
            System.out.println("✗ Product not found!");
        }
    }

 
    public void updateProduct() {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        System.out.print("Enter Product ID to update: ");
        String id = input.nextLine();

        Product p = searchById(id); // O(log n)
        if (p == null) {
            System.out.println("✗ Product not found!");
            return;
        }

        System.out.println("\nCurrent Product: " + p);
        System.out.println("\n--- Update Options ---");
        System.out.println("1. Update Name");
        System.out.println("2. Update Price");
        System.out.println("3. Update Stock");
        System.out.println("4. Add Stock");
        System.out.print("Enter choice: ");
        int ch = input.nextInt();
        input.nextLine(); // Clear buffer

        switch (ch) {
            case 1:
                System.out.print("Enter new name: ");
                p.setName(input.nextLine());
                System.out.println("✓ Product name updated!");
                break;
            case 2:
                System.out.print("Enter new price: ");
                p.setPrice(input.nextDouble());
                input.nextLine();
                System.out.println("✓ Product price updated!");
                break;
            case 3:
                System.out.print("Enter new stock: ");
                p.setStock(input.nextInt());
                input.nextLine();
                System.out.println("✓ Product stock updated!");
                break;
            case 4:
                System.out.print("Enter quantity to add: ");
                int quantity = input.nextInt();
                input.nextLine();
                p.addStock(quantity);
                System.out.println("✓ Stock added! New stock: " + p.getStock());
                break;
            default:
                System.out.println("✗ Invalid choice!");
        }
    }

    //*************** Search ***************//
 
    public void searchProductByName() {
        System.out.print("Enter Product Name: ");
        String name = input.nextLine();
        
        Product p = searchByName(name);
        if (p != null) {
            System.out.println("\n✓ Product Found:");
            System.out.println(p);
        } else {
            System.out.println("✗ Product not found!");
        }
    }
    
    private Product searchByName(String name) {
        LinkedList<Product> allProducts = products.inOrderTraversal();
        
        if (allProducts.isEmpty()) return null;
        
        allProducts.findFirst();
        while (true) {
            Product p = allProducts.retrieve();
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        return null;
    }
    
    public void searchProductById() {
        System.out.print("Enter Product ID: ");
        String id = input.nextLine();
        
        Product p = searchById(id);
        if (p != null) {
            System.out.println("\n✓ Product Found:");
            System.out.println(p);
        } else {
            System.out.println("✗ Product not found!");
        }
    }
    
    private Product searchById(String id) {
        Product temp = new Product(id, "", 0, 0);
        return products.search(temp); // O(log n)
    }
    
   
    public void searchByPriceRangeInteractive() {
        System.out.print("Enter minimum price: ");
        double min = input.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = input.nextDouble();
        input.nextLine(); // Clear buffer
        
        searchByPriceRange(min, max);
    }
    
    private void searchByPriceRange(double minPrice, double maxPrice) {
        System.out.println("\n=== Products in Price Range [$" + minPrice + " - $" + maxPrice + "] ===");
        
        LinkedList<Product> allProducts = products.inOrderTraversal();
        
        if (allProducts.isEmpty()) {
            System.out.println("No products available!");
            return;
        }
        
        boolean found = false;
        allProducts.findFirst();
        while (true) {
            Product p = allProducts.retrieve();
            if (p.isInPriceRange(minPrice, maxPrice)) {
                System.out.println(p);
                found = true;
            }
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        if (!found) {
            System.out.println("No products found in this price range.");
        }
    }

    
    public void showOutOfStock() {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        LinkedList<Product> allProducts = products.inOrderTraversal();
        
        System.out.println("\n=== Out of Stock Products ===");
        boolean found = false;
        
        allProducts.findFirst();
        while (true) {
            Product p = allProducts.retrieve();
            if (p.isOutOfStock()) {
                System.out.println(p);
                found = true;
            }
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        if (!found) {
            System.out.println("✓ All products are in stock!");
        }
    }

    
    public void displayAllProducts() {
    if (products.isEmpty()) {
        System.out.println("No products available.");
        return;
    }

    System.out.println("\n=== All Products (Sorted by ID) ===");
    LinkedList<Product> allProducts = products.inOrderTraversal();
    
    displayProductTable(allProducts, "All Products");
}

// Reusable method for displaying products in table format
public void displayProductTable(LinkedList<Product> productList, String title) {
    if (productList.isEmpty()) {
        System.out.println("No products to display.");
        return;
    }
    
    System.out.println("┌─────┬────────────┬────────────────────┬──────────┬───────┬─────────┬───────────┐");
    System.out.println("│ No. │ Product ID │ Name               │ Price    │ Stock │ Reviews │ Avg Rating│");
    System.out.println("├─────┼────────────┼────────────────────┼──────────┼───────┼─────────┼───────────┤");
    
    int count = 0;
    productList.findFirst();
    while (true) {
        count++;
        Product product = productList.retrieve();
        System.out.printf("│ %-3d │ %-10s │ %-18s │ $%-7.2f │ %-5d │ %-7d │ %-9.1f │\n",
            count, product.getProductId(), 
            truncate(product.getName(), 18),
            product.getPrice(), product.getStock(),
            product.getReviewCount(), product.getAverageRating());
        
        if (productList.last()) break;
        productList.findNext();
    }
    
    System.out.println("└─────┴────────────┴────────────────────┴──────────┴───────┴─────────┴───────────┘");
    System.out.println("\nTotal: " + count + " products");
}

private String truncate(String text, int maxLength) {
    return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
}
    
    
    public void showTop3MostReviewed() {
        showTopReviewedProducts(3);
    }
    
    private void showTopReviewedProducts(int topN) {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        LinkedList<Product> allProducts = products.inOrderTraversal();
        
        // Convert to array for sorting
        Product[] arr = listToArray(allProducts);
        
        // Sort by review count (descending)
        sortByReviewCount(arr);

        System.out.println("\n=== Top " + topN + " Most Reviewed Products ===");
        int count = Math.min(topN, arr.length);
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + arr[i]);
        }
    }

    //-----------Helper Methods------------//
    
    // Fixed: Added parameter
    private Product[] listToArray(LinkedList<Product> productList) {
        if (productList.isEmpty()) {
            return new Product[0];
        }
        
        // First, count the elements
        int size = 0;
        productList.findFirst();
        while (true) {
            size++;
            if (productList.last()) break;
            productList.findNext();
        }

        // Create array and populate it
        Product[] arr = new Product[size];
        productList.findFirst();
        for (int i = 0; i < size; i++) {
            arr[i] = productList.retrieve();
            if (!productList.last()) productList.findNext();
        }
        return arr;
    }

    // Fixed: Added parameter
    private void sortByReviewCount(Product[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].getReviewCount() < arr[j + 1].getReviewCount()) {
                    Product temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    //-----------Utility Methods------------//
    
 
    public boolean isEmpty() {
        return products.isEmpty();
    }
 
    public int getProductCount() {
        return products.size();
    }
 
    public AVLTree<Product> getProductsTree() {
        return products;
    }
 
    public LinkedList<Product> getAllProducts() {
        return products.inOrderTraversal();
    }
}