package ar.com.sustentate.ia.models;

/**
 * Created by pablo.cavallo on 2/24/18.
 */

public class ClassificationRequest {
    private String encodedImage;
    private String userId;

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
