package t3.code.base.controller;

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
import org.springframework.http.HttpStatusCode;
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
import t3.code.base.dto.CustomerDto;
import t3.code.base.dto.ErrorResponseDto;
import t3.code.base.dto.ResponseDto;
import t3.code.base.service.IAccountsService;

import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_200;
import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_201;
import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_404;
import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_417_DELETE;
import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_417_UPDATE;
import static t3.code.base.constants.AccountsConstants.MESSAGE_STATUS_500;
import static t3.code.base.constants.AccountsConstants.STATUS_200;
import static t3.code.base.constants.AccountsConstants.STATUS_201;
import static t3.code.base.constants.AccountsConstants.STATUS_417;
import static t3.code.base.constants.AccountsConstants.STATUS_500;

@Tag(name = "CRUD REST APIs for Accounts in T3 Bank",
        description = "CRUD REST APIs for Accounts in T3 Bank to CREATE, READ, UPDATE, DELETE account details"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

    @Operation(
            summary = "Create a new account",
            description = "Create a new account in T3 Bank"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = MESSAGE_STATUS_201
            ),
            @ApiResponse(
                    responseCode = "500"
                    , description = MESSAGE_STATUS_500
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping("/create-account")
    public ResponseEntity<ResponseDto> createAccounts(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccounts(customerDto);
        return ResponseEntity
                .ok(new ResponseDto(
                        STATUS_201,
                        MESSAGE_STATUS_201)
                );
    }

    @Operation(
            summary = "Get account details by mobile phone",
            description = "Get account details by mobile phone in T3 Bank"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = MESSAGE_STATUS_200
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = MESSAGE_STATUS_404,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = MESSAGE_STATUS_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/get-accounts")
    public ResponseEntity<CustomerDto> fetchAccounts(@RequestParam
                                                     @Pattern(
                                                             regexp = "(^$|[0-9]{10})",
                                                             message = "Mobile phone must be exactly 10 digits"
                                                     )
                                                     String mobilePhone) {
        return ResponseEntity.ok(iAccountsService.fetchAccounts(mobilePhone));
    }

    @Operation(
            summary = "Update account details by mobile phone",
            description = "Update account details by mobile phone in T3 Bank"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = MESSAGE_STATUS_200
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = MESSAGE_STATUS_417_UPDATE,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500"
                    , description = MESSAGE_STATUS_500
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccounts(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccounts(customerDto);
        if (isUpdated) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_STATUS_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(
                        STATUS_417,
                        MESSAGE_STATUS_417_UPDATE)
                );
    }


    @Operation(
            summary = "Delete account by mobile phone",
            description = "Delete account by mobile phone in T3 Bank"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = MESSAGE_STATUS_200
            ),
            @ApiResponse(
                    responseCode = "417"
                    , description = MESSAGE_STATUS_417_DELETE
            ),
            @ApiResponse(
                    responseCode = "500"
                    , description = MESSAGE_STATUS_500
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccounts(@RequestParam
                                                      @Pattern(
                                                              regexp = "(^$|[0-9]{10})",
                                                              message = "Mobile phone must be exactly 10 digits"
                                                      )
                                                      String mobilePhone) {
        boolean isDeleted = iAccountsService.deleteAccounts(mobilePhone);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_STATUS_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_STATUS_417_DELETE));
    }

}
