package com.example.salestaxes.dto;

import com.example.salestaxes.exception.ControlType;
import com.example.salestaxes.exception.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageError {

    private String Message;
    private String filedName;
    private ControlType controlType;
    private Level level;

}
