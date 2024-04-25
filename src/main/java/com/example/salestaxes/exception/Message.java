package com.example.salestaxes.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * This class represent the structure of the response that
 * will be displayed to the client in case there is exceptions
 */
@Data
@Builder
@AllArgsConstructor
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String key;
    private String filedName;
    private ControlType controlType;
    private Level level;

    public Message(){
        this.controlType=ControlType.FORM_VALIDATION;
        this.level=Level.ERROR;
    }
    public Message(String key){
        this.key=key;
        this.controlType=ControlType.FORM_VALIDATION;
        this.level=Level.ERROR;
    }

}
