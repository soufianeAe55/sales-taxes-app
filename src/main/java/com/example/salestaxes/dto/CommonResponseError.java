package com.example.salestaxes.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommonResponseError {

    private List<MessageError> messageErrorList;

    public CommonResponseError(){
        messageErrorList=new ArrayList<>();
    }

    public void addMessage(MessageError messageError){
        messageErrorList.add(messageError);
    }
}
