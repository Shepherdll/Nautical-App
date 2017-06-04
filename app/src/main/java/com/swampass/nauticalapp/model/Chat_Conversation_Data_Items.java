package com.swampass.nauticalapp.model;

/**
 * Created by Peter on 6/3/2017.
 */

public class Chat_Conversation_Data_Items {
    private String message;
    private String sender;

    public Chat_Conversation_Data_Items()
    {
    }

    public Chat_Conversation_Data_Items(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
