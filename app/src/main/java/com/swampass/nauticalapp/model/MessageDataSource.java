package com.swampass.nauticalapp.model;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Peter on 6/2/2017.
 */

public class MessageDataSource
{
    private static DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";

    public static void saveMessage(ChatMessage message, String convoId){
        Date date = message.getDate();
        String key = mDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getMessageText());
        msg.put(COLUMN_SENDER,"Ajay");
        mRef.child(convoId).child(key).setValue(msg);
    }

    public static MessagesListener addMessagesListener(String convoId, final MessagesCallbacks callbacks){
        MessagesListener listener = new MessagesListener(callbacks);
        mRef.child(convoId).addChildEventListener(listener);
        return listener;

    }

    public static void stop(MessagesListener listener){
        mRef.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener {
        private MessagesCallbacks callbacks;

        MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg = (HashMap)dataSnapshot.getValue();
            ChatMessage message = new ChatMessage();
            message.setMessageStatus(msg.get(COLUMN_SENDER));
            message.setMessageText(msg.get(COLUMN_TEXT));
            try {
                message.setDate(mDateFormat.parse(dataSnapshot.getKey()));
            }catch (Exception e){
                Log.d(TAG, "Couldn't parse date"+e);
            }
            if(callbacks != null){
                callbacks.onMessageAdded(message);
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {


        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }


    public interface MessagesCallbacks{
        public void onMessageAdded(ChatMessage message);
    }
}
