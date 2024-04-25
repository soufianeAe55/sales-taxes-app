package com.example.salestaxes.exception;

public class TechnicalException extends RuntimeException{

    public TechnicalException(Throwable e){
        super(e);
    }
    public TechnicalException(String m){
        super(m);
    }
}
