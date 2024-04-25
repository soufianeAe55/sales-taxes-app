package com.example.salestaxes.business;

import com.example.salestaxes.dto.ProductCategory;
import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.dto.ReceiptResponseDto;
import com.example.salestaxes.exception.BusinessException;
import com.example.salestaxes.validation.ReceiptValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTest {

    @InjectMocks
    private ReceiptService receiptService;
    @Mock
    private ReceiptValidator receiptValidator;

    @Test
    public void generate_should_handle_exempted_and_imported_products_and_return_correct_answer() throws BusinessException {
        doNothing().when(receiptValidator).validate(any());
        //Given
        ProductDto importedProduct = createProduct(true, ProductCategory.OTHER, "P1", BigDecimal.valueOf(20), 3);
        ProductDto exemptedProduct = createProduct(false, ProductCategory.FOOD, "P2", BigDecimal.valueOf(40), 2);
        ReceiptRequestDto request = createRequest(importedProduct, exemptedProduct);
        //When
        ReceiptResponseDto responseDto=receiptService.generate(request);
        //Then
        Assert.assertEquals(0, responseDto.getTotal().compareTo(BigDecimal.valueOf(149)));
        Assert.assertEquals(0, responseDto.getSalesTaxes().compareTo(BigDecimal.valueOf(9)));
    }
    @Test
    public void generate_should_handle_no_exempted_and_imported_products_and_return_correct_answer() throws BusinessException {
        doNothing().when(receiptValidator).validate(any());
        //Given
        ProductDto importedProduct = createProduct(true, ProductCategory.OTHER, "DVD", BigDecimal.valueOf(20), 3);
        ProductDto noExemptedProduct = createProduct(true, ProductCategory.OTHER, "TV", BigDecimal.valueOf(40), 3);
        ReceiptRequestDto request = createRequest(importedProduct, noExemptedProduct);
        //When
        ReceiptResponseDto responseDto=receiptService.generate(request);
        //Then
        Assert.assertEquals(0, responseDto.getTotal().compareTo(BigDecimal.valueOf(207)));
        Assert.assertEquals(0, responseDto.getSalesTaxes().compareTo(BigDecimal.valueOf(27)));
    }
    @Test
    public void generate_should_handle_exempted_and_no_imported_products_and_return_correct_answer() throws BusinessException {
        doNothing().when(receiptValidator).validate(any());
        //Given
        ProductDto importedProduct = createProduct(false, ProductCategory.FOOD, "chocolate", BigDecimal.valueOf(20.43), 3);
        ProductDto noExemptedProduct = createProduct(false, ProductCategory.BOOKS, "Harry Potter", BigDecimal.valueOf(40.87), 3);
        ReceiptRequestDto request = createRequest(importedProduct, noExemptedProduct);
        //When
        ReceiptResponseDto responseDto=receiptService.generate(request);
        //Then
        Assert.assertEquals(0, responseDto.getTotal().compareTo(BigDecimal.valueOf(184.05)));
        Assert.assertEquals(0, responseDto.getSalesTaxes().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void calculateTaxRate_should_return_rate_of_sales_and_import_taxes(){
        //When
        BigDecimal rate=receiptService.calculateTaxRate(ProductDto.builder().imported(true)
                .productCategory(ProductCategory.OTHER)
                .build());
        //Then
        Assert.assertEquals(0,BigDecimal.valueOf(0.15).compareTo(rate));
    }

    @Test
    public void calculateTaxRate_should_return_rate_of_sales_taxes(){
        //When
        BigDecimal rate=receiptService.calculateTaxRate(ProductDto.builder().imported(false)
                .productCategory(ProductCategory.OTHER)
                .build());
        //Then
        Assert.assertEquals(0,BigDecimal.valueOf(0.1).compareTo(rate));
    }
    @Test
    public void calculateTaxRate_should_return_rate_of_import_taxes(){
        //When
        BigDecimal rate=receiptService.calculateTaxRate(ProductDto.builder().imported(true)
                .productCategory(ProductCategory.FOOD)
                .build());
        //Then
        Assert.assertEquals(0,BigDecimal.valueOf(0.05).compareTo(rate));
    }
    @Test
    public void calculateTaxRate_should_return_no_rate(){
        //When
        BigDecimal rate=receiptService.calculateTaxRate(ProductDto.builder().imported(false)
                .productCategory(ProductCategory.BOOKS)
                .build());
        //Then
        Assert.assertEquals(0,BigDecimal.ZERO.compareTo(rate));
    }

    @Test
    public void isExempted_should_return_true_if_product_is_exempted_else_false(){
        //When
        boolean isExempted=receiptService.isExempted(ProductDto.builder()
                .productCategory(ProductCategory.FOOD)
                .build());
        //When
        boolean isExempted2=receiptService.isExempted(ProductDto.builder()
                .productCategory(ProductCategory.OTHER)
                .build());
        //Then
        Assert.assertTrue(isExempted);
        Assert.assertFalse(isExempted2);
    }

    @Test
    public void getProductTax_should_return_product_tax_based_on_rate(){
        //When
        BigDecimal tax=receiptService.getProductTax(ProductDto.builder()
                        .price(BigDecimal.valueOf(20))
                        .build(),BigDecimal.valueOf(0.05));
        //Then
        Assert.assertEquals(0,BigDecimal.ONE.compareTo(tax));
    }

    @Test
    public void roundUp_should_roundup_a_number_to_nearest_decimal(){
        //When
        BigDecimal rounded=receiptService.roundUp(BigDecimal.valueOf(33.21));
        //Then
        Assert.assertEquals(0,BigDecimal.valueOf(33.25).compareTo(rounded));
    }

    private ProductDto createProduct(boolean imported, ProductCategory category, String name, BigDecimal price, int quantity) {
        return ProductDto.builder()
                .imported(imported)
                .productCategory(category)
                .price(price)
                .name(name)
                .quantity(quantity)
                .build();
    }

    private ReceiptRequestDto createRequest(ProductDto... products) {
        return ReceiptRequestDto.builder()
                .products(Arrays.asList(products))
                .build();
    }

}