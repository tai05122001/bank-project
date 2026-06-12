package t3.code.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Cards extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "mobile_number")
    @NotNull
    private String mobileNumber;

    @Column(name = "card_number")
    @NotNull
    private String cardNumber;

    @Column(name = "card_type")
    @NotNull
    private String cardType;

    @Column(name = "total_limit")
    @NotNull
    private int totalLimit;

    @Column(name = "amount_used")
    @NotNull
    private int amountUsed;

    @Column(name = "available_amount")
    private int availableAmount;

}