package t3.code.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto implements Serializable {

    @Schema(
            description = "Account number of the customer", example = "1234567890"
    )
    @NotEmpty(message = "Account number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile phone must be exactly 10 digits")
    Long accountNumber;

    @Schema(
            description = "Account type of the customer", example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    String accountType;

    @Schema(
            description = "Branch address of the customer", example = "123 Main Street"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    String branchAddress;
}