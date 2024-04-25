package com.example.salestaxes.utils;

import com.example.salestaxes.exception.BusinessException;

/**
 * Assert interface for throwing business exceptions based on some conditions
 */
public interface Assert {

    static void assertNotNull(Object value, String messageKey) throws BusinessException {
        if (value == null) {
            throw new BusinessException(messageKey);
        }
    }
}
