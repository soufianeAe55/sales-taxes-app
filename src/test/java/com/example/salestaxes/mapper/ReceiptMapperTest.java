package com.example.salestaxes.mapper;

import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptResponseDto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


public class ReceiptMapperTest {

    @Test
    public void test_map(){
        ReceiptResponseDto response=ReceiptMapper.map(List.of(ProductDto.builder().build()), BigDecimal.ONE,BigDecimal.TEN);
        Assert.assertNotNull(response.getProducts());
        Assert.assertEquals(0,BigDecimal.ONE.compareTo(response.getTotal()));
        Assert.assertEquals(0,BigDecimal.TEN.compareTo(response.getSalesTaxes()));
    }

}