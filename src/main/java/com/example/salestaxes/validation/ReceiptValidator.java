package com.example.salestaxes.validation;

import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.exception.*;
import com.example.salestaxes.utils.ValidationUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.salestaxes.utils.Assert.assertNotNull;

/**
 * This component validator will validate all the requests
 * received by the api using Assert and ValidationUtils classes
 */
@Component
public class ReceiptValidator {

    private final String REQUEST_REQUIRED="request.required";
    private final String REQUEST_LIST_PRODUCTS_REQUIRED="request.list.products.required";
    private final String REQUEST_LIST_PRODUCTS_EMPTY="request.list.products.empty";
    private final String REQUEST_LIST_PRODUCT_NAME_EMPTY="request.list.product.name.required";
    private final String REQUEST_LIST_PRODUCT_PRICE_NOT_VALID="request.list.product.price.not.valid";
    private final String REQUEST_PRODUCT_QUANTITY_NOT_VALID="request.list.product.quantity.not.valid";
    private final String REQUEST_LIST_PRODUCT_CATEGORY_REQUIRED="request.list.product.category.required";



    public void validate(ReceiptRequestDto request) throws BusinessException {
        MessageList messageList=new MessageList();
        assertNotNull(request,REQUEST_REQUIRED);
        assertNotNull(request.getProducts(),REQUEST_LIST_PRODUCTS_REQUIRED);
        ValidationUtils.isTrue(!request.getProducts().isEmpty(),REQUEST_LIST_PRODUCTS_EMPTY,"PRODUCTS", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        request.getProducts().forEach((p)->{
            validate(p,messageList);
        });
        if(!messageList.get().isEmpty()) throw new BusinessException(messageList);
    }
    public void validate(ProductDto product, MessageList messageList){
        ValidationUtils.notEmpty(product.getName(),REQUEST_LIST_PRODUCT_NAME_EMPTY,"PRODUCT_NAME", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        ValidationUtils.notNull(product.getProductCategory(),REQUEST_LIST_PRODUCT_CATEGORY_REQUIRED,"PRODUCT_CATEGORY", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        if(Optional.ofNullable(product.getPrice()).isEmpty()) product.setPrice(BigDecimal.ZERO);
        ValidationUtils.isTrue(BigDecimal.ZERO.compareTo(product.getPrice())<0,REQUEST_LIST_PRODUCT_PRICE_NOT_VALID,"PRODUCT_PRICE", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
        ValidationUtils.isTrue(product.getQuantity()>0,REQUEST_PRODUCT_QUANTITY_NOT_VALID,"PRODUCT_QUANTITY", ControlType.FORM_VALIDATION, Level.ERROR,messageList);
    }

}
