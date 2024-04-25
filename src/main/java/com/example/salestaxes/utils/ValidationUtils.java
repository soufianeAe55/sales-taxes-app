package com.example.salestaxes.utils;

import com.example.salestaxes.exception.ControlType;
import com.example.salestaxes.exception.Level;
import com.example.salestaxes.exception.Message;
import com.example.salestaxes.exception.MessageList;
import org.apache.commons.lang.StringUtils;


/**
 * Validation utilities for the requests
 */
public interface ValidationUtils {

    static void notEmpty(String value, String key, String filedName, ControlType controlType,Level level , MessageList list){
        value=StringUtils.defaultString(value,"");
        if(value.isBlank()) list.add(new Message(key, filedName, controlType, level));
    }
    static void notNull(Object value,String key, String filedName, ControlType controlType,Level level , MessageList list){
        if(value==null) list.add(new Message(key, filedName, controlType, level));
    }
    static void isTrue(Boolean value, String key, String filedName, ControlType controlType,Level level , MessageList list){
        if(Boolean.FALSE.equals(value)) list.add(new Message(key, filedName, controlType, level));
    }
}
