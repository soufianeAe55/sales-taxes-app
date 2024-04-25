package com.example.salestaxes.exception;

import com.example.salestaxes.dto.CommonResponseError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExceptionGlobalHandlerTest {

    @InjectMocks
    private ExceptionGlobalHandler exceptionGlobalHandler;
    @Mock
    private MessageSource messageSource;

    @Test
    public void when_handling_business_exception_should_return_right_messages() throws Exception {

        MessageList messages = new MessageList();
        messages.add(new Message("error1"));

        when(messageSource.getMessage("error1",null, null)).thenReturn("Message1");

        CommonResponseError result = exceptionGlobalHandler.handle(new BusinessException(messages));

        assertNotNull(result);
        assertEquals(1, result.getMessageErrorList().size());
        assertEquals("Message1", result.getMessageErrorList().get(0).getMessage());

    }

    @Test
    public void when_handling_technical_exception_should_return_right_messages() {

        when(messageSource.getMessage(anyString(),any(), any())).thenReturn("technical exception occurred");

        CommonResponseError result = exceptionGlobalHandler.handle(new TechnicalException(""));

        assertNotNull(result);
        assertEquals(1, result.getMessageErrorList().size());
        assertEquals("technical exception occurred", result.getMessageErrorList().get(0).getMessage());

    }
}