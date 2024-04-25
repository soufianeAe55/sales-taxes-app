package com.example.salestaxes.exception;

import com.example.salestaxes.dto.CommonResponseError;
import com.example.salestaxes.dto.MessageError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * A global exception handler to handle exceptions separately from
 * the layers of the app, this component will return a CommonResponseError
 * for all the intercepted exceptions
 *
 */
@ControllerAdvice
public class ExceptionGlobalHandler {

    private  final Logger logger=LoggerFactory.getLogger(ExceptionGlobalHandler.class);

    private final MessageSource messageSource;

    private final String TECHNICAL_EXCEPTION_KEY= "technical.exception";

    public static final String BUSINESS_EXCEPTION_HAS_OCCURED = "Business exception {} has occured";


    public ExceptionGlobalHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponseError handle(BusinessException e){
        logger.info(BUSINESS_EXCEPTION_HAS_OCCURED,e.getMessage());
        CommonResponseError responseError=new CommonResponseError();
        e.getMessageList().forEach(m->{
            String message=messageSource.getMessage(m.getKey(),null,null);
            responseError.addMessage(MessageError.builder()
                    .Message(message)
                    .filedName(m.getFiledName())
                    .controlType(m.getControlType())
                    .level(m.getLevel())
                    .build());
        });
        return responseError;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    public CommonResponseError handle(TechnicalException e){
        logger.error(e.getMessage(), e);
        CommonResponseError response=new CommonResponseError();
        String message=messageSource.getMessage(TECHNICAL_EXCEPTION_KEY,null,null);
        response.addMessage(MessageError.builder()
                .Message(message)
                .controlType(ControlType.FORM_VALIDATION)
                .level(Level.ERROR)
                .build());

        return response;
    }
}
