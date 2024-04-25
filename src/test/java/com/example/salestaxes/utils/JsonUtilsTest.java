package com.example.salestaxes.utils;

import com.example.salestaxes.dto.MessageError;
import com.example.salestaxes.exception.ControlType;
import com.example.salestaxes.exception.Level;
import com.example.salestaxes.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {

    @Test
    public void asJsonString() {
        JsonUtils.setMapper(new ObjectMapper());
        String result = JsonUtils.asJsonString(new MessageError("Message", "field", ControlType.FORM_VALIDATION, Level.ERROR));
        assertEquals("{\"filedName\":\"field\",\"controlType\":\"FORM_VALIDATION\",\"level\":\"ERROR\",\"message\":\"Message\"}", result);
    }
    @Test(expected = TechnicalException.class)
    public void asJsonString_should_throw_technical_exception() throws JsonProcessingException {
        ObjectMapper mapper= Mockito.mock(ObjectMapper.class);
        JsonUtils.setMapper(mapper);
        MessageError msg=new MessageError("Message", "field", ControlType.FORM_VALIDATION, Level.ERROR);
        when(mapper.writeValueAsString(msg)).thenThrow(new RuntimeException());
        JsonUtils.asJsonString(msg);
      }

}