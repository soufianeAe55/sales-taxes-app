package com.example.salestaxes.utils;

import com.example.salestaxes.exception.ControlType;
import com.example.salestaxes.exception.Level;
import com.example.salestaxes.exception.MessageList;
import org.junit.Test;

import static org.junit.Assert.*;


public class ValidationUtilsTest {

    @Test
    public void notEmpty_should_add_error_message_when_value_is_empty() {
        MessageList messageList = new MessageList();
        ValidationUtils.notEmpty("","empty.msg", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        assertEquals(1,messageList.get().size());
        assertEquals("empty.msg",messageList.get().get(0).getKey());
    }

    @Test
    public void notNull_should_add_error_message_when_value_is_null() {
        MessageList messageList = new MessageList();
        ValidationUtils.notNull(null,"null.msg", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        assertEquals(1,messageList.get().size());
        assertEquals("null.msg",messageList.get().get(0).getKey());
    }

    @Test
    public void isTrue_should_add_error_message_when_value_is_false() {

        MessageList messageList = new MessageList();
        ValidationUtils.isTrue(false,"test.msg", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        assertEquals(1,messageList.get().size());
        assertEquals("test.msg",messageList.get().get(0).getKey());
    }


    @Test
    public void isTrue_should_do_nothing_when_value_is_true() {

        MessageList messageList = new MessageList();
        ValidationUtils.isTrue(null,"", "field",ControlType.FORM_VALIDATION, Level.ERROR, messageList);
        assertEquals(0,messageList.get().size());
    }

}