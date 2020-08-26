package clash.back.Initializer;

import clash.back.domain.entity.*;
import clash.back.domain.entity.building.Location;
import clash.back.repository.AgeRepository;
import clash.back.repository.CivilizationRepository;
import clash.back.repository.PlayerRepository;
import clash.back.repository.TreasuryRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Initializer {
    private static final Logger logger = LogManager.getLogger(Initializer.class);
    private static final String STARTING_DATA_INIT = "--Starting data initialization--";

    @Value("${coc.path.initial_data}")
    private String INITIAL_DATA;

    @Value("${coc.age.initial_age}")
    private String INITIAL_AGE;

    @Autowired
    AgeRepository ageRepository;

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TreasuryRepository treasuryRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws FileNotFoundException {
        logger.info(STARTING_DATA_INIT);
        importAges();
        importTeams();
    }

    void importTeams() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA + "/teams.json");
        Gson gson = new Gson();
        Civilization[] civilizations = gson.fromJson(reader, Civilization[].class);
        Arrays.stream(civilizations)
                .filter(civilization -> !civilizationRepository.existsByName(civilization.getName()))
                .collect(Collectors.toList())
                .forEach(civilization -> {
                    Set<Player> players = new HashSet<>();

                    civilization.getPlayers().forEach(player -> players.add(playerRepository.save(player.toBuilder().id(UUID.randomUUID().toString())
                            .treasury(treasuryRepository.save(new Treasury())).cards(new ArrayList<>()).build())));

                    civilization = civilizationRepository.save(civilization.toBuilder()
                            .id(UUID.randomUUID().toString())
                            .age(ageRepository.findByName(INITIAL_AGE).orElse(ageRepository.findAll().iterator().next()))
                            .players(players)
                            .treasury(treasuryRepository.save(new Treasury()))
                            .build());

                    Civilization finalCivilization = civilization;
                    civilization.getPlayers().forEach(player -> playerRepository.save(player.toBuilder().civilization(finalCivilization).build()));
                });

    }

    void importAges() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA + "/ages.json");
        Gson gson = new Gson();
        Age[] ages = gson.fromJson(reader, Age[].class);
        Arrays.stream(ages)
                .filter(age -> !ageRepository.existsByName(age.getName()))
                .collect(Collectors.toList())
                .forEach(age -> {
                    age.setId(UUID.randomUUID().toString());
                    ageRepository.save(age);
                });
    }
}
