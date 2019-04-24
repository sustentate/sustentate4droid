package ar.com.sustentate.ia.models;

/**
 * Created by emzas on 06/10/2018.
 */

public class AssistanceResponse extends Message {

    private int status; // 0 OK

    public AssistanceResponse(String sentence, String urlAttachment, int status, String sessionid) {
        super(sentence, urlAttachment, sessionid);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
