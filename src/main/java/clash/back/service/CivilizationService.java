package clash.back.service;

import clash.back.domain.entity.Age;
import clash.back.domain.entity.Card;
import clash.back.domain.entity.Civilization;
import clash.back.exception.AgeNotFoundException;
import clash.back.exception.NotEnoughResourcesException;
import clash.back.repository.AgeRepository;
import clash.back.repository.CardRepository;
import clash.back.repository.CivilizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CivilizationService {

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    AgeRepository ageRepository;

    @Autowired
    CardRepository cardRepository;

    public List<Card> getCards(Civilization civilization) {
        return civilization.getArmory().getCards();
    }

    public void upgrade(Civilization civilization) throws Exception {
        Age age = ageRepository.findByOrderNo(civilization.getAge().getOrderNo() + 1).orElseThrow(AgeNotFoundException::new);
        if (age.getChivalryCost() > civilization.getChivalry() ||
                age.getKnowledgeCost() > civilization.getKnowledge() ||
                age.getUpgradeXP() > civilization.getXP())
            throw new NotEnoughResourcesException();
        civilization.decreaseChivalry(age.getChivalryCost());
        civilization.decreaseKnowledge(age.getKnowledgeCost());
        civilization.setAge(age);
        civilizationRepository.save(civilization);
        cardRepository.resetCardLevels(civilization);
    }
}
