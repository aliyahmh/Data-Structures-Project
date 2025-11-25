import java.util.Scanner;

import java.util.Comparator;


public class ReviewAVL {
    private AVLTree<Review> reviews = CSVReader.readReviewsAVL("datasets/reviews.csv");
    private Scanner input = new Scanner(System.in);

    // 1. Add a new review
    public void addReview() {
        System.out.print("Enter Review ID: ");
        String reviewId = input.nextLine();

        // Check if review ID exists
        if (searchById(reviewId) != null) {
            System.out.println("This Review ID already exists!");
            return;
        }

        System.out.print("Enter Product ID: ");
        String productId = input.nextLine();

        System.out.print("Enter Customer ID: ");
        String customerId = input.nextLine();

        System.out.print("Enter Rating (1-5): ");
        int rating = input.nextInt();
        input.nextLine(); // clear buffer

        System.out.print("Enter Comment: ");
        String comment = input.nextLine();

        Review newReview = new Review(reviewId, productId, customerId, rating, comment);
        reviews.insert(newReview);
        System.out.println("Review added successfully!");
    }

    // 2. Search review by ID
    public void searchReview() {
        System.out.print("Enter Review ID to search: ");
        String reviewId = input.nextLine();

        Review review = searchById(reviewId);
        if (review != null) {
            System.out.println("Review Found:");
            System.out.println(review);
        } else {
            System.out.println("Review not found!");
        }
    }

    // 3. Display all reviews
    public void displayAllReviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews available.");
            return;
        }

        System.out.println("\n=== ALL REVIEWS ===");
        LinkedList<Review> allReviews = reviews.inOrderTraversal();
        
        allReviews.findFirst();
        while (true) {
            System.out.println(allReviews.retrieve());
            if (allReviews.last()) break;
            allReviews.findNext();
        }
    }

    // 4. Show reviews for a product
    public void showProductReviews() {
        System.out.print("Enter Product ID: ");
        String productId = input.nextLine();

        LinkedList<Review> productReviews = getReviewsByProduct(productId);
        
        if (productReviews.isEmpty()) {
            System.out.println("No reviews found for this product.");
            return;
        }

        System.out.println("\n=== REVIEWS FOR PRODUCT " + productId + " ===");
        productReviews.findFirst();
        while (true) {
            System.out.println(productReviews.retrieve());
            if (productReviews.last()) break;
            productReviews.findNext();
        }
    }

    // 5. Show reviews by a customer
    public void showCustomerReviews() {
        System.out.print("Enter Customer ID: ");
        String customerId = input.nextLine();

        LinkedList<Review> customerReviews = getReviewsByCustomer(customerId);
        
        if (customerReviews.isEmpty()) {
            System.out.println("No reviews found for this customer.");
            return;
        }

        System.out.println("\n=== REVIEWS BY CUSTOMER " + customerId + " ===");
        customerReviews.findFirst();
        while (true) {
            System.out.println(customerReviews.retrieve());
            if (customerReviews.last()) break;
            customerReviews.findNext();
        }
    }

    // 6. Show average rating for a product
    public void showAverageRating() {
        System.out.print("Enter Product ID: ");
        String productId = input.nextLine();

        double average = getAverageRating(productId);
        System.out.println("Average rating for product " + productId + ": " + String.format("%.2f", average) + "/5");
    }

    // 7. Show top 3 highest rated products
    public void showTop3Products() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews available!");
            return;
        }

        // Get all unique products
        LinkedList<String> allProducts = getAllProducts();
        
        if (allProducts.isEmpty()) {
            System.out.println("No products with reviews found.");
            return;
        }

        // Calculate average for each product
        LinkedList<Double> averages = new LinkedList<>();
        allProducts.findFirst();
        while (true) {
            String productId = allProducts.retrieve();
            averages.insert(getAverageRating(productId));
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        // Simple sorting to find top 3
        String[] products = listToArray(allProducts);
        Double[] ratings = listToArrayDouble(averages);

        // Bubble sort (simple but works for small data)
        for (int i = 0; i < ratings.length - 1; i++) {
            for (int j = 0; j < ratings.length - i - 1; j++) {
                if (ratings[j] < ratings[j + 1]) {
                    // Swap ratings
                    double tempRating = ratings[j];
                    ratings[j] = ratings[j + 1];
                    ratings[j + 1] = tempRating;
                    
                    // Swap products
                    String tempProduct = products[j];
                    products[j] = products[j + 1];
                    products[j + 1] = tempProduct;
                }
            }
        }

        System.out.println("\n=== TOP 3 HIGHEST RATED PRODUCTS ===");
        int count = Math.min(3, products.length);
        for (int i = 0; i < count; i++) {
            if (ratings[i] > 0) {
                System.out.printf("%d. Product %s - Average: %.2f/5\n", i + 1, products[i], ratings[i]);
            }
        }
    }

    // Helper method: Search by ID
    private Review searchById(String reviewId) {
        Review temp = new Review();
        temp.setReviewId(reviewId);
        return reviews.search(temp);
    }

    // Helper method: Get reviews for a product
    private LinkedList<Review> getReviewsByProduct(String productId) {
        LinkedList<Review> result = new LinkedList<>();
        LinkedList<Review> allReviews = reviews.inOrderTraversal();
        
        if (allReviews.isEmpty()) return result;
        
        allReviews.findFirst();
        while (true) {
            Review review = allReviews.retrieve();
            if (review.getProductId().equals(productId)) {
                result.insert(review);
            }
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        return result;
    }

    // Helper method: Get reviews by a customer
    private LinkedList<Review> getReviewsByCustomer(String customerId) {
        LinkedList<Review> result = new LinkedList<>();
        LinkedList<Review> allReviews = reviews.inOrderTraversal();
        
        if (allReviews.isEmpty()) return result;
        
        allReviews.findFirst();
        while (true) {
            Review review = allReviews.retrieve();
            if (review.getCustomerId().equals(customerId)) {
                result.insert(review);
            }
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        return result;
    }

    // Helper method: Get average rating for a product
    private double getAverageRating(String productId) {
        LinkedList<Review> productReviews = getReviewsByProduct(productId);
        
        if (productReviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        int count = 0;

        productReviews.findFirst();
        while (true) {
            Review review = productReviews.retrieve();
            sum += review.getRating();
            count++;
            if (productReviews.last()) break;
            productReviews.findNext();
        }

        return sum / count;
    }

    // Helper method: Get all unique products
    private LinkedList<String> getAllProducts() {
        LinkedList<String> products = new LinkedList<>();
        LinkedList<Review> allReviews = reviews.inOrderTraversal();
        
        if (allReviews.isEmpty()) return products;
        
        allReviews.findFirst();
        while (true) {
            Review review = allReviews.retrieve();
            String productId = review.getProductId();
            
            // Check if product already in list
            boolean found = false;
            if (!products.isEmpty()) {
                products.findFirst();
                while (true) {
                    if (products.retrieve().equals(productId)) {
                        found = true;
                        break;
                    }
                    if (products.last()) break;
                    products.findNext();
                }
            }
            
            if (!found) {
                products.insert(productId);
            }
            
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        return products;
    }

    // Helper method: Convert LinkedList to String array
    private String[] listToArray(LinkedList<String> list) {
        if (list.isEmpty()) return new String[0];
        
        int size = 0;
        list.findFirst();
        while (true) {
            size++;
            if (list.last()) break;
            list.findNext();
        }

        String[] arr = new String[size];
        list.findFirst();
        for (int i = 0; i < size; i++) {
            arr[i] = list.retrieve();
            if (!list.last()) list.findNext();
        }
        return arr;
    }

    // Helper method: Convert LinkedList to Double array
    private Double[] listToArrayDouble(LinkedList<Double> list) {
        if (list.isEmpty()) return new Double[0];
        
        int size = 0;
        list.findFirst();
        while (true) {
            size++;
            if (list.last()) break;
            list.findNext();
        }

        Double[] arr = new Double[size];
        list.findFirst();
        for (int i = 0; i < size; i++) {
            arr[i] = list.retrieve();
            if (!list.last()) list.findNext();
        }
        return arr;
    }

    // Simple utility methods
    public boolean isEmpty() {
        return reviews.isEmpty();
    }
    
    public int getReviewCount() {
        return reviews.size();
    }
}