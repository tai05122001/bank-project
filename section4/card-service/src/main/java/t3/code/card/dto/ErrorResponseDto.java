package t3.code.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold Error Response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path of the error"
    )
    private String apiPath;

    @Schema(
            description = "Status code of the error"
    )
    private HttpStatus statusCode;

    @Schema(
            description = "Error message of the error"
    )
    private String errorMessage;

    @Schema(
            description = "Error time of the error"
    )
    private LocalDateTime errorTime;
}

