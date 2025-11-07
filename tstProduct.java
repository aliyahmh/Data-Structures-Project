import java.util.Scanner;

public class tstProduct {
   


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // إنشاء قائمة المنتجات
        DoubleLinkedList<Product> productList = new DoubleLinkedList<>();
        Product.setProductList(productList); // نربطها بالكلاس

        int choice;
        do {
            System.out.println("\n========= PRODUCT MENU =========");
            System.out.println("1. Add new product");
            System.out.println("2. Remove product");
            System.out.println("3. Update product (name, price, stock)");
            System.out.println("4. Search By ID (linear)");
            System.out.println("5. Search products by name (linear)");
            System.out.println("6. Track all Out-stock products");
            System.out.println("7. Display all products");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    Product.addProduct();
                    break;

                case 2:
                    Product.removeProduct();
                    break;

                case 3:
                    Product.updateProduct();
                    break;

                case 4: {
                    System.out.print("Enter Product ID: ");
                    String id = input.nextLine();
                    Product p = Product.searchById(id);
                    if (p != null)
                        System.out.println("✅ Found: " + p);
                    else
                        System.out.println("❌ Product not found.");
                }
                break;

                case 5: {
                    System.out.print("Enter Product Name: ");
                    String name = input.nextLine();
                    Product p = Product.searchByName(name);
                    if (p != null)
                        System.out.println("✅ Found: " + p);
                    else
                        System.out.println("❌ Product not found.");
                }
                break;

                case 6:
                    Product.showOutOfStock();
                    break;

                case 7:
                    Product.displayAllProducts();
                    break;

                case 8:
                    System.out.println("Exiting... 👋");
                    break;

                default:
                    System.out.println("⚠️ Invalid choice! Try again.");
            }

        } while (choice != 8);

        input.close();
    }
}




  

