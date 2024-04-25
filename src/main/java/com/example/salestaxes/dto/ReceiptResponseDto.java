package com.example.salestaxes.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReceiptResponseDto {
    private List<ProductDto> products;
    private LocalDate date;
    private BigDecimal total;
    private BigDecimal salesTaxes;
}
