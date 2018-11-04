package ar.com.sustentate.com.models;

/**
 * Created by emzas on 06/10/2018.
 */

public class AssistanceRequest extends Message {
    private String conversationId;

    public AssistanceRequest(String sentence, String urlAttachment, String conversationId) {
        super(sentence, urlAttachment);
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    /* Identificador único de la conversacion. Se pueden intercambiar muchos dialogos
        para una misma conversación.
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
