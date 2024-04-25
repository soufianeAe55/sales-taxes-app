package com.example.salestaxes.api;


import com.example.salestaxes.business.ReceiptService;
import com.example.salestaxes.dto.ProductCategory;
import com.example.salestaxes.dto.ProductDto;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.dto.ReceiptResponseDto;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private ReceiptController receiptController;
    @Mock
    private ReceiptService receiptService;

    @Before
    public void Setup(){
        mockMvc= MockMvcBuilders.standaloneSetup(receiptController).build();
    }

    @Test
    public void test_generate() throws Exception {
        ProductDto product=ProductDto.builder()
                .name("music DVD")
                .imported(true)
                .productCategory(ProductCategory.OTHER)
                .quantity(2)
                .price(BigDecimal.valueOf(300))
                .build();
        ReceiptResponseDto response= ReceiptResponseDto.builder()
                .products(List.of(product))
                .salesTaxes(BigDecimal.valueOf(40))
                .total(BigDecimal.valueOf(340))
                .date(LocalDate.of(2024,4,24))
                .build();
        doReturn(response).when(receiptService).generate(any(ReceiptRequestDto.class));
        ResultActions result= mockMvc.perform(post("/receipts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
        MvcResult mvcResult=result.andExpect(status().is(HttpStatus.SC_CREATED)).andDo(print()).andReturn();

        Assert.assertEquals("{\"products\":[{\"name\":\"music DVD\",\"price\":300,\"quantity\":2,\"imported\":true,\"productCategory\":\"OTHER\"}],\"date\":[2024,4,24],\"total\":340,\"salesTaxes\":40}",
                mvcResult.getResponse().getContentAsString());

    }


}