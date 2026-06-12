package t3.code.card.controller;

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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import t3.code.card.constants.CardConstants;
import t3.code.card.dto.CardsDto;
import t3.code.card.dto.ResponseDto;
import t3.code.card.service.ICardsService;

import static t3.code.card.constants.CardConstants.MESSAGE_200;
import static t3.code.card.constants.CardConstants.MESSAGE_201;
import static t3.code.card.constants.CardConstants.MESSAGE_404;
import static t3.code.card.constants.CardConstants.MESSAGE_417_DELETE;
import static t3.code.card.constants.CardConstants.MESSAGE_417_UPDATE;
import static t3.code.card.constants.CardConstants.MESSAGE_500;
import static t3.code.card.constants.CardConstants.STATUS_200;
import static t3.code.card.constants.CardConstants.STATUS_201;
import static t3.code.card.constants.CardConstants.STATUS_404;
import static t3.code.card.constants.CardConstants.STATUS_417;
import static t3.code.card.constants.CardConstants.STATUS_500;

@Tag(name = "CRUD REST APIs for Cards in T3 Bank",
        description = "CRUD REST APIs for Cards in T3 Bank to CREATE, READ, UPDATE, DELETE account details"
)
@RestController
@RequestMapping(path = "/api/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class CardsController {

    private final ICardsService iAccountsService;

    @Operation(
            summary = "Get card details by mobile phone",
            description = "Get card details by mobile phone"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
            @ApiResponse(responseCode =STATUS_404, description = MESSAGE_404)
    })
    @GetMapping
    public ResponseEntity<CardsDto> getCardByMobilePhone(

            @Valid
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile phone must be exactly 10 digits")
            @RequestParam String mobilePhone) {
        return ResponseEntity.ok(iAccountsService.getCardByMobilePhone(mobilePhone));
    }


    @Operation(
            summary = "Create card details",
            description = "Create card details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_201, description = MESSAGE_201),
            @ApiResponse(responseCode =STATUS_500, description = MESSAGE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createCard(
            @Valid
            @RequestBody CardsDto cardsDto) {
        iAccountsService.createCard(cardsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
            summary = "Update card details",
            description = "Update card details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
            @ApiResponse(responseCode =STATUS_417, description = MESSAGE_417_UPDATE),
            @ApiResponse(responseCode =STATUS_500, description = MESSAGE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateCard(
            @Valid
            @RequestBody CardsDto cardsDto) {
        boolean isUpdate = iAccountsService.updateCard(cardsDto);
        if (isUpdate) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete card details",
            description = "Delete card details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
            @ApiResponse(responseCode =STATUS_417, description = MESSAGE_417_UPDATE),
            @ApiResponse(responseCode =STATUS_500, description = MESSAGE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCardByMobilePhone(
            @Valid
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile phone must be exactly 10 digits")
            @RequestParam String mobilePhone
    ){
        boolean isDelete = iAccountsService.deleteCardByMobilePhone(mobilePhone);
        if (isDelete) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
        }
    }


}
