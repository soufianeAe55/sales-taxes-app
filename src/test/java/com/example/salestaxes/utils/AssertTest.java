package com.example.salestaxes.utils;

import com.example.salestaxes.exception.BusinessException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssertTest {


    @Test(expected = BusinessException.class)
    public void assertNotNull_should_throw_exception_when_object_is_null() throws BusinessException {
        try {
            Assert.assertNotNull(null, "product.key");
        } catch (BusinessException e) {
            assertEquals("product.key", e.getMessageList().get(0).getKey());
            throw e;
        }
    }
    @Test
    public void assertNotNull_should_not_throw_exception_when_object_is_not_null() {
        try {
            Assert.assertNotNull("", "product.key");
        } catch (BusinessException e) {
            fail();
        }
    }

}