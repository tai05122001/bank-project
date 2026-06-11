package t3.code.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto{

    @Schema(
            description = "Name of the customer", example = "John Doe"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email must be a valid email address")
    @Schema(
            description = "Email of the customer", example = "john.doe@example.com"
    )
    private String email;

    @Schema(
            description = "MobilePhone of the customer", example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile phone must be exactly 10 digits")
    private String mobilePhone;

    @Schema(
            description = "AccountsDto of the customer"
    )
    private AccountsDto accountsDto;
}