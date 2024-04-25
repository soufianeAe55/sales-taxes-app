package com.example.salestaxes.utils;

import com.example.salestaxes.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Wrapper that handle the use of ObjectMapper
 */
public class JsonUtils {

    private static  ObjectMapper mapper=new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
           throw new TechnicalException(e);
        }
    }

    public static void setMapper(ObjectMapper mapper) {
        JsonUtils.mapper = mapper;
    }
}
