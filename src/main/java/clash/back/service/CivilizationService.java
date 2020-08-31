package clash.back.service;

import clash.back.domain.entity.Card;
import clash.back.domain.entity.Civilization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CivilizationService {

    public List<Card> getCards(Civilization civilization) {
        return civilization.getArmory().getCards();
    }
}
