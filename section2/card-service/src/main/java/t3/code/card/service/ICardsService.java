package t3.code.card.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import t3.code.card.dto.CardsDto;
import t3.code.card.dto.ResponseDto;

public interface ICardsService {

    CardsDto getCardByMobilePhone(String mobilePhone);

    void createCard(CardsDto cardsDto);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCardByMobilePhone( String mobilePhone);
}
