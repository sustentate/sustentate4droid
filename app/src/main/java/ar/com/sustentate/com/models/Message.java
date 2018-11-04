package ar.com.sustentate.com.models;

/**
 * Created by emzas on 30/10/2018.
 */

public class Message {
    private String sentence;
    private String urlAttachment;

    public Message(String sentence, String urlAttachment) {
        this.sentence = sentence;
        this.urlAttachment = urlAttachment;
    }

    public Message(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public String getUrlAttachment() {
        return urlAttachment;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setUrlAttachment(String urlAttachment) {
        this.urlAttachment = urlAttachment;
    }
}
