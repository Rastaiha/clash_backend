package clash.back.service;

import clash.back.domain.entity.Card;
import clash.back.domain.entity.Civilization;
import clash.back.repository.CivilizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CivilizationService {

    @Autowired
    CivilizationRepository civilizationRepository;

    public List<Card> getCards(Civilization civilization) {
        return civilization.getArmory().getCards();
    }

    public void upgrade(Civilization civilization) {

    }
}
