package t3.code.loans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import t3.code.loans.dto.ErrorResponseDto;
import t3.code.loans.dto.LoansDto;
import t3.code.loans.dto.ResponseDto;
import t3.code.loans.service.ILoansService;

import static t3.code.loans.constants.LoansConstants.MESSAGE_200;
import static t3.code.loans.constants.LoansConstants.MESSAGE_201;
import static t3.code.loans.constants.LoansConstants.MESSAGE_404;
import static t3.code.loans.constants.LoansConstants.MESSAGE_417_DELETE;
import static t3.code.loans.constants.LoansConstants.MESSAGE_417_UPDATE;
import static t3.code.loans.constants.LoansConstants.MESSAGE_500;
import static t3.code.loans.constants.LoansConstants.STATUS_200;
import static t3.code.loans.constants.LoansConstants.STATUS_201;
import static t3.code.loans.constants.LoansConstants.STATUS_404;
import static t3.code.loans.constants.LoansConstants.STATUS_417;
import static t3.code.loans.constants.LoansConstants.STATUS_500;

@Tag(name = "CRUD REST APIs for Loans in T3 Bank",
        description = "CRUD REST APIs for Loans in T3 Bank to CREATE, READ, UPDATE, DELETE account details"
)
@RestController
@RequestMapping(path = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoansController {

    private final ILoansService iLoansService;

    @Operation(
            summary = "Get loan details by mobile number",
            description = "Get loan details by mobile number"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = STATUS_200,
                    description = MESSAGE_200),
            @ApiResponse(
                    responseCode = STATUS_404,
                    description = MESSAGE_404),
            @ApiResponse(
                    responseCode = STATUS_500,
                    description = MESSAGE_500)
    })
    @GetMapping
    public ResponseEntity<LoansDto> getLoanByMobileNumber(
            @Valid
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
            @RequestParam("mobilePhone") String mobilePhone
    ) {
        return ResponseEntity.ok(iLoansService.getLoanByMobileNumber(mobilePhone));
    }

    @Operation(
            summary = "Create new loan",
            description = "Create new loan"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = STATUS_201,
                    description = MESSAGE_201
            ),
            @ApiResponse(
                    responseCode = STATUS_500,
                    description = MESSAGE_500,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class
                            )
                    ))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createLoan(
            @Valid @RequestBody LoansDto loansDto
    ) {
        iLoansService.createLoan(loansDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
            summary = "Update loan details",
            description = "Update loan details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = STATUS_200,
                    description = MESSAGE_200
            ),
            @ApiResponse(
                    responseCode = STATUS_417,
                    description = MESSAGE_417_UPDATE

            ),
            @ApiResponse(
                    responseCode = STATUS_500,
                    description = MESSAGE_500,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateLoan(
            @Valid @RequestBody LoansDto loansDto
    ) {
        boolean isUpdate = iLoansService.updateLoan(loansDto);
        if (isUpdate) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
    }


    @Operation(
            summary = "Delete loan details",
            description = "Delete loan details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = STATUS_200,
                    description = MESSAGE_200
            ),
            @ApiResponse(
                    responseCode = STATUS_417,
                    description = MESSAGE_417_DELETE
            ),
            @ApiResponse(
                    responseCode = STATUS_500,
                    description = MESSAGE_500,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteLoanDetail(
            @Valid
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
            @RequestParam("mobilePhone") String mobilePhone
    ) {
        boolean isDelete = iLoansService.deleteLoanDetail(mobilePhone);
        if (isDelete) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
    }
}
