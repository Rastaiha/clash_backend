package clash.back.initializer;

import clash.back.domain.entity.Map;
import clash.back.domain.entity.*;
import clash.back.domain.entity.building.Institute;
import clash.back.domain.entity.building.MapEntity;
import clash.back.domain.entity.building.Motel;
import clash.back.domain.entity.building.TownHall;
import clash.back.repository.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Initializer {
    private static final Logger logger = LogManager.getLogger(Initializer.class);
    private static final String STARTING_DATA_INIT = "--Starting data initialization--";
    private static final String FINISHED = "--data initialization finished--";
    @Autowired
    ChallengeTemplateRepository challengeTemplateRepository;

    @Value("${coc.age.initial_age}")
    private String INITIAL_AGE;

    @Value("${coc.civilization.initial_chivalry}")
    private int INITIAL_CHIVALRY;

    @Autowired
    AgeRepository ageRepository;

    @Autowired
    CivilizationRepository civilizationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TreasuryRepository treasuryRepository;

    @Autowired
    MapRepository mapRepository;

    @Autowired
    MapEntityRepository mapEntityRepository;

    @Autowired
    WorldRepository worldRepository;

    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    ArmoryRepository armoryRepository;
    @Value("${coc.path.initial_data}")
    private String INITIAL_DATE_PATH;

    private static final String DEFAULT_PASSWORD = "12345";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void appReady() throws FileNotFoundException {
        logger.info(STARTING_DATA_INIT);
        importWorld();
        importAges();
        importMap();
        importTeams();
        importChallengeTemplates();
        logger.info(FINISHED);
    }

    void importAges() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATE_PATH + "/ages.json");
        Gson gson = new Gson();
        Age[] ages = gson.fromJson(reader, Age[].class);
        Arrays.stream(ages)
                .filter(age -> !ageRepository.existsByName(age.getName()))
                .collect(Collectors.toList())
                .forEach(age -> {
                    Set<CardType> cardTypes = new HashSet<>();
                    age.getCardTypes().forEach(cardType -> cardTypes.add(cardTypeRepository.save(cardType.toBuilder().id(UUID.randomUUID().toString()).build())));
                    Age finalAge = ageRepository.save(age.toBuilder().id(UUID.randomUUID().toString()).cardTypes(cardTypes).build());
                    finalAge.getCardTypes().forEach(cardType -> cardTypeRepository.save(cardType.toBuilder().age(finalAge).build()));
                });
    }

    void importWorld() {
        if (worldRepository.count() != 0)
            return;
        worldRepository.save(World.builder().id(UUID.randomUUID().toString()).build());
    }

    private void importMap() throws FileNotFoundException {
        if (mapRepository.findAll().iterator().hasNext())
            return;
        World world = worldRepository.findAll().iterator().next();
        Reader reader = new FileReader(INITIAL_DATE_PATH + "/map.json");
        Gson gson = new Gson();
        Map map = gson.fromJson(reader, Map.class);
        List<MapEntity> mapEntities = map.getMapEntities();
        map = map.toBuilder().id(UUID.randomUUID().toString()).mapEntities(new ArrayList<>())
                .world(world).build();
        Map finalMap1 = map;
        world.setMap(mapRepository.save(map));
        worldRepository.save(world);
        map.setMapEntities(mapEntities.stream().map(mapEntity -> {
            switch (mapEntity.getName()) {
                case "MOTEL":
                    return new Motel(mapEntity.getX(), mapEntity.getY()).buildMap(finalMap1);
                case "INSTITUTE":
                    return new Institute(mapEntity.getX(), mapEntity.getY()).buildMap(finalMap1);
                case "TOWNHALL":
                    return new TownHall(mapEntity.getX(), mapEntity.getY()).buildMap(finalMap1);
                default:
                    return mapEntity;
            }
        }).collect(Collectors.toList()));

        map.getMapEntities().forEach(mapEntity -> mapEntityRepository.save(mapEntity));
        map.setWorld(world);
        worldRepository.save(map.getWorld());
    }

    void importTeams() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATE_PATH + "/teams.json");
        Gson gson = new Gson();
        Random random = new Random();
        Civilization[] civilizations = gson.fromJson(reader, Civilization[].class);
        Arrays.stream(civilizations)
                .filter(civilization -> !civilizationRepository.existsByName(civilization.getName()))
                .collect(Collectors.toList())
                .forEach(civilization -> {
                    Set<Player> players = new HashSet<>();

                    int x = random.nextInt(mapRepository.findAll().iterator().next().getWidth());
                    int y = random.nextInt(mapRepository.findAll().iterator().next().getHeight());

                    civilization.getPlayers().forEach(player -> players.add(playerRepository.save(player.toBuilder().id(UUID.randomUUID().toString())
                            .treasury(treasuryRepository.save(new Treasury())).x(x).y(y)
                            .challenges(new HashSet<>())
                            .password(passwordEncoder.encode(DEFAULT_PASSWORD)).cards(new HashSet<>()).build())));

                    civilization = civilizationRepository.save(civilization.toBuilder()
                            .id(UUID.randomUUID().toString())
                            .age(ageRepository.findByName(INITIAL_AGE).orElse(ageRepository.findAll().iterator().next()))
                            .players(players)
                            .treasury(treasuryRepository.save(Treasury.builder().id(UUID.randomUUID().toString()).chivalry(INITIAL_CHIVALRY).build()))
                            .world(worldRepository.findAll().iterator().next())
                            .armory(armoryRepository.save(Armory.builder().cards(new ArrayList<>()).id(UUID.randomUUID().toString()).build()))
                            .townHall(mapEntityRepository.save((TownHall) new TownHall(x, y).buildMap(mapRepository.findAll().iterator().next())))
                            .build());

                    Civilization finalCivilization = civilization;
                    civilization.getPlayers().forEach(player -> {
                        player.setCivilization(finalCivilization);
                        playerRepository.save(player);
                    });
                    civilization.getTownHall().setCivilization(finalCivilization);
                    civilization.getWorld().getCivilizations().add(civilization);
                    civilization.getArmory().setCivilization(finalCivilization);
                    mapEntityRepository.save(civilization.getTownHall());
                    worldRepository.save(civilization.getWorld());
                    armoryRepository.save(civilization.getArmory());
                });

    }

    private void importChallengeTemplates() {
        challengeTemplateRepository.deleteAll();

        Arrays.stream(ChallengeType.values()).forEach(challengeType -> {
            try {
                Stream<Path> walk = Files.walk(Paths.get(INITIAL_DATE_PATH + "/challenges/" + challengeType.toString().toLowerCase()));
                walk.filter(Files::isRegularFile)
                        .forEach(path -> challengeTemplateRepository.save(ChallengeTemplate.builder()
                                .challengeType(challengeType)
                                .fileName(path.getFileName().toString())
                                .id(UUID.randomUUID().toString())
                                .build()));
            } catch (IOException ignored) {
            }
        });
    }
}
