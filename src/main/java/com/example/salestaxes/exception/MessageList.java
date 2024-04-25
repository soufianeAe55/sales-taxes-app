package com.example.salestaxes.exception;

import java.util.ArrayList;
import java.util.List;


/**
 * Wrapper for Message Object array list (override add method to handle
 * NullPointerException)
 */
public class MessageList {

    private final List<Message> messageList;

    public MessageList() {
        this.messageList = new ArrayList<>();
    }

    public boolean add(Message message){
        boolean add=false;
        if(message!=null){
            add=this.messageList.add(message);
        }
        return add;
    }

    public List<Message> get(){
        return this.messageList;
    }


}
