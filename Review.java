import java.util.Scanner;

public class Review {
    private String reviewId;
    private String productId;
    private String customerId;
    private int rating;
    private String comment;

    // Constructors
    public Review() {
        this.reviewId = "";
        this.productId = "";
        this.customerId = "";
        this.rating = 0;
        this.comment = "";
    }

    public Review(String reviewId, String productId, String customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "\nReview ID: " + reviewId +
               ", Product ID: " + productId +
               ", Customer ID: " + customerId +
               ", Rating: " + rating +
               ", Comment: " + comment;
    }

    // Data Management Section
    private static Scanner input = new Scanner(System.in);
    private static LinkedList<Review> reviews = new LinkedList<>();

    // Add Review
    public static void addReview(String customerId, String productId) {
        System.out.print("Enter Review ID: ");
        String rId = input.nextLine();

        while (checkReviewID(rId)) {
            System.out.print("ID already exists! Enter another: ");
            rId = input.nextLine();
        }

        System.out.print("Enter rating (1-5): ");
        int rating = input.nextInt();
        while (rating < 1 || rating > 5) {
            System.out.print("Re-enter rating (1-5): ");
            rating = input.nextInt();
        }
        input.nextLine(); // consume newline

        System.out.print("Enter comment: ");
        String comment = input.nextLine();

        Review newReview = new Review(rId, productId, customerId, rating, comment);
        reviews.insert(newReview);

        System.out.println(" Review added successfully!");
    }

    // Update Review
    public static void updateReview() {
        if (reviews.empty()) {
            System.out.println(" No reviews available!");
            return;
        }

        System.out.print("Enter Review ID to update: ");
        String rId = input.nextLine();

        Review found = null;
        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            if (r.getReviewId().equals(rId)) {
                found = r;
                break;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }

        if (found == null) {
            System.out.println(" Review not found!");
            return;
        }

        System.out.println("1. Update rating");
        System.out.println("2. Update comment");
        System.out.print("Enter choice: ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new rating (1–5): ");
                int rate = input.nextInt();
                input.nextLine();
                while (rate < 1 || rate > 5) {
                    System.out.print("Re-enter rating (1–5): ");
                    rate = input.nextInt();
                    input.nextLine();
                }
                found.setRating(rate);
                break;

            case 2:
                System.out.print("Enter new comment: ");
                String comment = input.nextLine();
                found.setComment(comment);
                break;

            default:
                System.out.println(" Invalid choice!");
        }

        System.out.println(" Review updated!");
    }

    // Check Review ID
    public static boolean checkReviewID(String id) {
        if (reviews.empty()) return false;

        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().getReviewId().equals(id))
                return true;
            if (reviews.last()) break;
            reviews.findNext();
        }
        return false;
    }

   
    // Get Average Rating for a Product
    public static double getAverageRatingForProduct(String productId) {
        if (reviews.empty()) {
            return 0.0;
        }

        double sum = 0;
        int count = 0;

        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            if (r.getProductId().equals(productId)) {
                sum += r.getRating();
                count++;
            }

            if (reviews.last()) break;
            reviews.findNext();
        }

        if (count == 0) {
            return 0.0;
        }

        double average = sum / count;
        return average;
    }

    // Accessor for list
    public static LinkedList<Review> getReviewsList() {
        return reviews;
    }

    public static void setReviewsList(LinkedList<Review> listFromCSV) {
        reviews = listFromCSV;
    }




    public static void showCommonHighRatedProducts() {
    if (reviews.empty()) {
        System.out.println(" No reviews available!");
        return;
    }

    System.out.print("Enter first customer ID: ");
    String customer1 = input.nextLine();

    System.out.print("Enter second customer ID: ");
    String customer2 = input.nextLine();

    LinkedList<String> products1 = new LinkedList<>();
    LinkedList<String> products2 = new LinkedList<>();

    reviews.findFirst();
    while (true) {
        Review r = reviews.retrieve();
        if (r.getRating() >= 4) {
            if (r.getCustomerId().equals(customer1)) {
                products1.insert(r.getProductId());
            } else if (r.getCustomerId().equals(customer2)) {
                products2.insert(r.getProductId());
            }
        }

        if (reviews.last()) break;
        reviews.findNext();
    }

    LinkedList<String> common = new LinkedList<>();
    Node<String> p1 = products1.getHead();

    while (p1 != null) {
        Node<String> p2 = products2.getHead();
        while (p2 != null) {
            if (p1.data.equals(p2.data)) {
                common.insert(p1.data);
                break;
            }
            p2 = p2.next;
        }
        p1 = p1.next;
    }

    if (common.empty()) {
        System.out.println("No common products rated 4 or more between these customers.");
    } else {
        System.out.println(" Common products rated 4 or more:");
        Node<String> current = common.getHead();
        while (current != null) {
            System.out.println("Product ID: " + current.data);
            current = current.next;
        }
    }
}




public static void showReviewsByCustomer() {
    if (reviews.empty()) {
        System.out.println("No reviews available!");
        return;
    }

    System.out.print("Enter Customer ID: ");
    String customerId = input.nextLine();

    boolean found = false;

    reviews.findFirst();
    while (true) {
        Review r = reviews.retrieve();
        if (r.getCustomerId().equals(customerId)) {
            System.out.println(r);
            found = true;
        }

        if (reviews.last()) break;
        reviews.findNext();
    }

    if (!found) {
        System.out.println("No reviews found for customer ID: " + customerId);
    }
}




 public static void showTop3Products(LinkedList<Product> products) {
    if (products.empty() || reviews.empty()) {
        System.out.println(" No products or reviews available!");
        return;
    }

    LinkedList<Product> sortedList = new LinkedList<>();
    LinkedList<Double> avgRatings = new LinkedList<>();

    Node<Product> pNode = products.getHead();
    while (pNode != null) {
        Product p = pNode.data;
        double avg = getAverageRatingForProduct(p.getProductId());
            if (avg > 0) {
        sortedList.insert(p);
        avgRatings.insert(avg);
        }
        pNode = pNode.next;
    }

    Node<Product> iP = sortedList.getHead();
    Node<Double> iR = avgRatings.getHead();
    while (iP != null) {
        Node<Product> jP = iP.next;
        Node<Double> jR = iR.next;
        while (jP != null) {
            if (iR.data < jR.data) {
                Double tempR = iR.data;
                iR.data = jR.data;
                jR.data = tempR;

                Product tempP = iP.data;
                iP.data = jP.data;
                jP.data = tempP;
            }
            jP = jP.next;
            jR = jR.next;
        }
        iP = iP.next;
        iR = iR.next;
    }

    System.out.println("Top 3 Products by Average Rating:");
    Node<Product> node = sortedList.getHead();
    Node<Double> rateNode = avgRatings.getHead();
    int count = 0;

    while (node != null && count < 3) {
        System.out.println((count + 1) + ". " + node.data.getName() +
                " | Avg Rating: " + String.format("%.2f", rateNode.data));
        node = node.next;
        rateNode = rateNode.next;
        count++;
    }

    if (count == 0) {
        System.out.println("No products with reviews found.");
    }
}

}
