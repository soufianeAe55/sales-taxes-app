package com.example.salestaxes.mapper;

import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * A mapper for mapping between DTOs
 */
public interface ReceiptMapper {

    static ReceiptResponseDto map(List<ProductDto> list, BigDecimal total, BigDecimal salesTax){
        return ReceiptResponseDto.builder()
                .products(list)
                .salesTaxes(salesTax)
                .total(total)
                .date(LocalDate.now())
                .build();
    }
}
