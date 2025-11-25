import java.util.Comparator;

public class Review {
    private String reviewId;
    private String productId;
    private String customerId;
    private int rating;
    private String comment;

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

    // Simple getters
    public String getReviewId() { return reviewId; }
    public String getProductId() { return productId; }
    public String getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    // Simple setters
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Review ID: " + reviewId + 
               ", Product: " + productId + 
               ", Customer: " + customerId + 
               ", Rating: " + rating + "/5" + 
               ", Comment: " + comment;
    }
}