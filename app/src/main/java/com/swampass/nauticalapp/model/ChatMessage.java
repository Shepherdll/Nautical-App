package com.swampass.nauticalapp.model;

import java.util.Date;

/**
 * Created by madhur on 17/01/15.
 */
public class ChatMessage {

    private String messageText;
    private UserType userType;
    private Status messageStatus;
    private Date mDate;

    public void setDate(Date mDate) {this.mDate = mDate;}
    public Date getDate() { return mDate;}


    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getMessageText() { return messageText;}

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public UserType getUserType() {
        return userType;
    }

    public void setMessageStatus(String messageStatus) {
        if(messageStatus.equals("sender"))
            this.messageStatus = Status.SENT;
        else
            this.messageStatus = Status.DELIVERED;

    }
    public Status getMessageStatus() {
        return messageStatus;
    }
}
