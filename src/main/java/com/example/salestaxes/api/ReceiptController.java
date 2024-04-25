package com.example.salestaxes.api;


import com.example.salestaxes.business.ReceiptService;
import com.example.salestaxes.dto.CommonResponseError;
import com.example.salestaxes.dto.ReceiptRequestDto;
import com.example.salestaxes.dto.ReceiptResponseDto;
import com.example.salestaxes.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @ApiOperation(value = "Generate the receipt of the products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ReceiptResponseDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = CommonResponseError.class),
            @ApiResponse(code = 500, message = "technical error", response = CommonResponseError.class),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceiptResponseDto> generate(@RequestBody ReceiptRequestDto request) throws BusinessException {
        return new ResponseEntity<>(receiptService.generate(request), HttpStatus.CREATED);
    }
}
