package com.example.salestaxes.validation;

import com.example.salestaxes.dto.ProductCategory;
import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.exception.BusinessException;
import com.example.salestaxes.exception.Message;
import com.example.salestaxes.exception.MessageList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ReceiptValidatorTest {

    @InjectMocks
    private ReceiptValidator validator;

    private final String REQUEST_REQUIRED="request.required";
    private final String REQUEST_LIST_PRODUCTS_REQUIRED="request.list.products.required";
    private final String REQUEST_LIST_PRODUCTS_EMPTY="request.list.products.empty";
    private final String REQUEST_LIST_PRODUCT_NAME_EMPTY="request.list.product.name.required";
    private final String REQUEST_LIST_PRODUCT_PRICE_NOT_VALID="request.list.product.price.not.valid";
    private final String REQUEST_PRODUCT_QUANTITY_NOT_VALID="request.list.product.quantity.not.valid";
    private final String REQUEST_LIST_PRODUCT_CATEGORY_REQUIRED="request.list.product.category.required";

    @Test(expected = BusinessException.class)
    public void validate_request_should_not_be_null() throws BusinessException {
        ReceiptRequestDto request=null;
        try{
            validator.validate(request);
        } catch (BusinessException e) {
            Assert.assertEquals(1,e.getMessageList().size());
            Assert.assertTrue(exists(e.getMessageList(),REQUEST_REQUIRED));
            throw e;
        }
    }
    @Test(expected = BusinessException.class)
    public void validate_list_products_should_not_be_null() throws BusinessException {
        ReceiptRequestDto request=ReceiptRequestDto.builder()
                .products(null)
                .build();
        try{
            validator.validate(request);
        } catch (BusinessException e) {
            Assert.assertEquals(1,e.getMessageList().size());
            Assert.assertTrue(exists(e.getMessageList(),REQUEST_LIST_PRODUCTS_REQUIRED));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void validate_list_products_should_not_be_empty() throws BusinessException {
        ReceiptRequestDto request=ReceiptRequestDto.builder()
                .products(new ArrayList<>())
                .build();
        try{
            validator.validate(request);
        } catch (BusinessException e) {
            Assert.assertEquals(1,e.getMessageList().size());
            Assert.assertTrue(exists(e.getMessageList(),REQUEST_LIST_PRODUCTS_EMPTY));
            throw e;
        }
    }

    @Test
    public void validate_products_name_should_not_be_null()  {
        ProductDto request=ProductDto.builder()
                .name(null)
                .quantity(9)
                .productCategory(ProductCategory.OTHER)
                .price(new BigDecimal("9"))
                .build();
        MessageList messageList=new MessageList();
        validator.validate(request,messageList);
        Assert.assertEquals(1,messageList.get().size());
        Assert.assertTrue(exists(messageList.get(),REQUEST_LIST_PRODUCT_NAME_EMPTY));
    }
    @Test
    public void validate_products_name_should_not_be_blank() {
        ProductDto request=ProductDto.builder()
                .name("")
                .quantity(9)
                .productCategory(ProductCategory.OTHER)
                .price(new BigDecimal("9"))
                .build();
        MessageList messageList=new MessageList();
        validator.validate(request,messageList);
        Assert.assertEquals(1,messageList.get().size());
        Assert.assertTrue(exists(messageList.get(),REQUEST_LIST_PRODUCT_NAME_EMPTY));
    }
    @Test
    public void validate_products_quantity_should_be_valid() {
        ProductDto request=ProductDto.builder()
                .name("name")
                .quantity(0)
                .productCategory(ProductCategory.OTHER)
                .price(new BigDecimal("9"))
                .build();
        MessageList messageList=new MessageList();
        validator.validate(request,messageList);
        Assert.assertEquals(1,messageList.get().size());
        Assert.assertTrue(exists(messageList.get(),REQUEST_PRODUCT_QUANTITY_NOT_VALID));
    }
    @Test
    public void validate_products_price_should_be_valid()  {
        ProductDto request=ProductDto.builder()
                .name("name")
                .quantity(2)
                .productCategory(ProductCategory.OTHER)
                .price(new BigDecimal("0"))
                .build();
        MessageList messageList=new MessageList();
        validator.validate(request,messageList);
        Assert.assertEquals(1,messageList.get().size());
        Assert.assertTrue(exists(messageList.get(),REQUEST_LIST_PRODUCT_PRICE_NOT_VALID));
    }
    @Test
    public void validate_products_category_should_not_be_null() {
        ProductDto request=ProductDto.builder()
                .name("name")
                .quantity(9)
                .productCategory(null)
                .price(new BigDecimal("9"))
                .build();
        MessageList messageList=new MessageList();
        validator.validate(request,messageList);
        Assert.assertEquals(1,messageList.get().size());
        Assert.assertTrue(exists(messageList.get(),REQUEST_LIST_PRODUCT_CATEGORY_REQUIRED));
    }
    private boolean exists(List<Message> msgs, String key) {
        return msgs.stream().anyMatch(item -> item.getKey().equals(key));
    }

}