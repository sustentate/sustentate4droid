package ar.com.sustentate.ia.models;

/**
 * Created by emzas on 30/10/2018.
 */

public class Message {
    private String sentence;
    private String urlAttachment;
    private String sessionId;
    public Message(String sentence, String urlAttachment, String sessionId) {
        this.sentence = sentence;
        this.sessionId = sessionId;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
