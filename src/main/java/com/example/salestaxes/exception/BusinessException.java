package com.example.salestaxes.exception;

import com.example.salestaxes.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;


import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private final List<Message> messageList;

    public BusinessException(String key){
        super(StringUtils.defaultString(key,""));
        this.messageList=new ArrayList<>();
        messageList.add(new Message(key));
    }

    public BusinessException(MessageList messageList) {
        super(JsonUtils.asJsonString(messageList.get()));
        this.messageList=messageList.get();
    }

    public List<Message> getMessageList(){
        return this.messageList;
    }
}
