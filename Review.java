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
    private static DoubleLinkedList<Review> reviews = new DoubleLinkedList<>();

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
            System.out.println(" No reviews available!");
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
            System.out.println(" No reviews found for product " + productId);
            return 0.0;
        }

        double average = sum / count;
        System.out.println("Average rating for product " + productId + " = " + average);
        return average;
    }

    // Accessor for list
    public static DoubleLinkedList<Review> getReviewsList() {
        return reviews;
    }
}
