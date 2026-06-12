package t3.code.card.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import t3.code.card.dto.CardsDto;
import t3.code.card.entity.Cards;
import t3.code.card.exception.CardsAlreadyExistsException;
import t3.code.card.exception.ResourceNotFoundException;
import t3.code.card.mapper.CardsMapper;
import t3.code.card.repository.CardsRepository;
import t3.code.card.service.ICardsService;

import java.util.Random;

import static t3.code.card.constants.CardConstants.CREDIT_CARD;
import static t3.code.card.constants.CardConstants.NEW_CARD_LIMIT;

@Service
@AllArgsConstructor
@Slf4j
public class CardsServiceImpl implements ICardsService {
    private final CardsRepository cardsRepository;

    /**
     * @param mobilePhone phone number
     * @return card details
     */
    @Override
    public CardsDto getCardByMobilePhone(String mobilePhone) {
        Cards cards = cardsRepository.findByMobileNumber(mobilePhone).orElseThrow(
                () -> new ResourceNotFoundException("Card", " mobileNumber:", mobilePhone)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    /**
     * @param cardsDto card details
     */
    @Override
    public void createCard(CardsDto cardsDto) {
        cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).ifPresent(
                c -> {
                    throw new CardsAlreadyExistsException("Card already exists with mobile number: " + c.getMobileNumber());
                }
        );
        cardsRepository.save(createNewCard(cardsDto.getMobileNumber()));

    }

    private Cards createNewCard(String mobilePhone){
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobilePhone);
        newCard.setCardType(CREDIT_CARD);
        newCard.setTotalLimit(NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     * @param cardsDto card details
     * @return true if the card is updated
     */
    @Override
    @Transactional
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", " mobileNumber:", cardsDto.getCardNumber())
        );
        cardsRepository.save(CardsMapper.mapToCards(cardsDto, cards));
        return true;
    }

    /**
     * @param mobilePhone phone number
     * @return true if the card is deleted
     */
    @Override
    public boolean deleteCardByMobilePhone(String mobilePhone) {
        Cards card = cardsRepository.findByMobileNumber(mobilePhone).orElseThrow(
                () -> new ResourceNotFoundException("Card", " mobileNumber:", mobilePhone)
        );
        cardsRepository.deleteById(card.getCardId());
        return false;
    }
}

