package clash.back.initializer;

import clash.back.domain.dto.MapEntityInitializerDto;
import clash.back.domain.dto.MapInitializerDto;
import clash.back.domain.entity.Map;
import clash.back.domain.entity.*;
import clash.back.domain.entity.building.*;
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
    private String INITIAL_DATA_PATH;

    private static final String DEFAULT_PASSWORD = "12345";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void appReady() throws FileNotFoundException {
        logger.info(STARTING_DATA_INIT);
        importWorld();
        importAges();
        importCardTypes();
        importMap();
        importTeams();
        importChallengeTemplates();
        importMentors();
        logger.info(FINISHED);
    }


    void importAges() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA_PATH + "/ages.json");
        Gson gson = new Gson();
        Age[] ages = gson.fromJson(reader, Age[].class);
        Arrays.stream(ages)
                .filter(age -> !ageRepository.existsByName(age.getName()))
                .collect(Collectors.toList())
                .forEach(age -> ageRepository.save(age.toBuilder().id(UUID.randomUUID().toString()).build()));
    }

    void importCardTypes() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA_PATH + "/cardTypes.json");
        Gson gson = new Gson();
        CardType[] cardTypes = gson.fromJson(reader, CardType[].class);
        Arrays.stream(cardTypes)
                .filter(cardType -> !cardTypeRepository.existsByOrderNo(cardType.getOrderNo()))
                .collect(Collectors.toList())
                .forEach(age -> cardTypeRepository.save(age.toBuilder().id(UUID.randomUUID().toString()).build()));

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
        Reader reader = new FileReader(INITIAL_DATA_PATH + "/map.json");
        Gson gson = new Gson();
        MapInitializerDto mapInitializerDto = gson.fromJson(reader, MapInitializerDto.class);
        Map map = Map.builder().id(UUID.randomUUID().toString()).mapEntities(new ArrayList<>())
                .width(mapInitializerDto.getWidth()).height(mapInitializerDto.getHeight()).world(world).build();

        List<MapEntity> mapEntities = generateEntities(mapInitializerDto.getEntities(), map);
        world.setMap(mapRepository.save(map));
        worldRepository.save(world);
        map.setMapEntities(mapEntities.stream().map(mapEntity -> {
            switch (mapEntity.getName().trim().toUpperCase()) {
                case "MOTEL":
                    return new Motel(mapEntity.getLocation(), mapEntity.getRootId()).buildMap(map);
                case "INSTITUTE":
                    return new Institute(mapEntity.getLocation(), mapEntity.getRootId()).buildMap(map);
                case "WALL":
                    return new Wall(mapEntity.getLocation(), mapEntity.getRootId()).buildMap(map);
                case "TREE":
                    return new Tree(mapEntity.getLocation(), mapEntity.getRootId()).buildMap(map);
                default:
                    return MapEntity.builder().id(UUID.randomUUID().toString()).map(map).build();
            }
        }).collect(Collectors.toList()));

        map.getMapEntities().forEach(mapEntity -> mapEntityRepository.save(mapEntity));
        map.setWorld(world);
        mapRepository.save(map);
    }

    void importTeams() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA_PATH + "/teams.json");
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
                            .isMentor(false)
                            .x(x).y(y)
                            .status(PlayerStatus.IN_TOWNHALL)
                            .challenges(new HashSet<>())
                            .password(passwordEncoder.encode(DEFAULT_PASSWORD)).cards(new HashSet<>()).build())));

                    civilization = civilizationRepository.save(civilization.toBuilder()
                            .id(UUID.randomUUID().toString())
                            .age(ageRepository.findByName(INITIAL_AGE).orElse(ageRepository.findAll().iterator().next()))
                            .players(players)
                            .chivalry(INITIAL_CHIVALRY)
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
        Arrays.stream(ChallengeType.values()).forEach(challengeType -> {
            try {
                Stream<Path> walk = Files.walk(Paths.get(INITIAL_DATA_PATH + "/challenges/" + challengeType.toString().toLowerCase()));
                walk.filter(Files::isRegularFile).filter(path -> !challengeTemplateRepository.findByFileName(path.getFileName().toString()).isPresent())
                        .forEach(path -> challengeTemplateRepository.save(ChallengeTemplate.builder()
                                .challengeType(challengeType)
                                .fileName(path.getFileName().toString())
                                .id(UUID.randomUUID().toString())
                                .build()));
            } catch (IOException ignored) {
            }
        });
    }

    private void importMentors() throws FileNotFoundException {
        Reader reader = new FileReader(INITIAL_DATA_PATH + "/mentors.json");
        Gson gson = new Gson();
        String[] mentors = gson.fromJson(reader, String[].class);
        Arrays.stream(mentors).filter(mentor -> !playerRepository.findPlayerByUsername(mentor).isPresent())
                .forEach(mentor -> playerRepository.save(Player.builder().id(UUID.randomUUID().toString())
                        .username(mentor).x(-1).y(-1).password(passwordEncoder.encode(mentor.split("@")[0])).isMentor(true).build()));
    }

    private Location assignLocation(List<MapEntity> mapEntities, Map map, int width, int height) {
        int x, y;
        Random random = new Random();
        while (true) {
            int randX = random.nextInt(map.getWidth() - width), randY = random.nextInt(map.getHeight() - height);
            if (mapEntities.stream().noneMatch(me -> me.getX() == randX && me.getY() == randY)) {
                x = randX;
                y = randY;
                break;
            }
        }
        return new Location(x, y);
    }

    private List<MapEntity> generateEntities(List<MapEntityInitializerDto> dtos, Map map) {
        List<MapEntity> mapEntities = new ArrayList<>();
        dtos.forEach(dto -> {
            for (int i = 0; i < dto.getCount(); i++) {
                Location location = assignLocation(mapEntities, map, dto.getWidth(), dto.getHeight());
                String rootId = UUID.randomUUID().toString();
                mapEntities.add(MapEntity.builder().id(rootId).rootId(rootId)
                        .x(location.getX()).y(location.getY()).name(dto.getName()).build());
                for (int j = 0; j < dto.getWidth(); j++)
                    for (int k = 0; k < dto.getHeight(); k++) {
                        if (j == k && j == 0)
                            continue;
                        mapEntities.add(MapEntity.builder().id(UUID.randomUUID().toString()).rootId(rootId)
                                .x(location.getX() + j).y(location.getY() + k).name(dto.getName()).build());
                    }
            }
        });
        return mapEntities;
    }
}
