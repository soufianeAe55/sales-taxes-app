package com.example.salestaxes.business;

import com.example.salestaxes.dto.ProductCategory;
import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.dto.ReceiptResponseDto;
import com.example.salestaxes.exception.BusinessException;
import com.example.salestaxes.mapper.ReceiptMapper;
import com.example.salestaxes.validation.ReceiptValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ReceiptService {

    private final Logger logger= LoggerFactory.getLogger(ReceiptService.class);


    private final ReceiptValidator receiptValidator;

    private final BigDecimal SALES_TAXES_RATE= new BigDecimal("0.1");
    private final BigDecimal IMPORT_DUTY_RATE= new BigDecimal("0.05");
    private final BigDecimal INCREMENT=new BigDecimal("0.05");

    public ReceiptService(ReceiptValidator receiptValidator){
        this.receiptValidator=receiptValidator;
    }

    public ReceiptResponseDto generate(ReceiptRequestDto request) throws BusinessException {
        logger.info("Generating the receipt of the products...");

        //Validating all the fields of the request using a layer of validation
        receiptValidator.validate(request);

        //Precalculate the rate of taxes for each product and store them in a map
        Map<ProductDto,BigDecimal> taxRates=request.getProducts().stream()
                .collect(Collectors.toMap(p->p,this::calculateTaxRate));

        //Calculating the sum of taxes using the map of rates and round up the sum
        BigDecimal sumTaxes = roundUp(request.getProducts().stream()
                .map(p->getProductTax(p,taxRates.get(p)).multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add));

        //Update the prices of the products using the map of rates
        request.getProducts().forEach(p -> {
            BigDecimal tax = getProductTax(p, taxRates.get(p));
            p.setPrice(roundUp(p.getPrice().add(tax)));
        });

        //Calculating the total of the updated prices and round up the total
        BigDecimal totalPrices = roundUp(request.getProducts().stream()
                .map(p->p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add));

        //Use the mapper to get the response
        return ReceiptMapper.map(request.getProducts(),totalPrices,sumTaxes);
    }

    /**
     *
     * @param product
     * @return BigDecimal
     * this function will calculate the rate of tax based on the product category
     * and the imported field
     */
    public BigDecimal calculateTaxRate(ProductDto product){
        BigDecimal taxRate = BigDecimal.ZERO;
        if (!isExempted(product)) taxRate=taxRate.add(SALES_TAXES_RATE);
        if (product.isImported()) taxRate=taxRate.add(IMPORT_DUTY_RATE);
        return taxRate;
    }

    public boolean isExempted(ProductDto product){
        return !ProductCategory.OTHER.equals(product.getProductCategory());
    }

    public BigDecimal getProductTax(ProductDto product, BigDecimal rate){
        return product.getPrice().multiply(rate);
    }

    public BigDecimal roundUp(BigDecimal decimal){
        return decimal.divide(INCREMENT,0, RoundingMode.UP).multiply(INCREMENT)
                .setScale(2,RoundingMode.HALF_UP);
    }

}