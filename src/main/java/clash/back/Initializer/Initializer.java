package clash.back.Initializer;

import clash.back.domain.entity.*;
import clash.back.domain.entity.Map;
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

    @Autowired
    MapRepository mapRepository;

    @Autowired
    MapEntityRepository mapEntityRepository;

    @Autowired
    WorldRepository worldRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws FileNotFoundException {
        logger.info(STARTING_DATA_INIT);
        importWorld();
        importAges();
        importMap();
        importTeams();
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

    void importWorld() {
        if (worldRepository.count() != 0)
            return;
        worldRepository.save(World.builder().id(UUID.randomUUID().toString()).build());
    }

    private void importMap() throws FileNotFoundException {
        if (mapRepository.findAll().iterator().hasNext())
            return;
        World world = worldRepository.findAll().iterator().next();
        Reader reader = new FileReader(INITIAL_DATA + "/map.json");
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
        Reader reader = new FileReader(INITIAL_DATA + "/teams.json");
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
                            .treasury(treasuryRepository.save(new Treasury())).cards(new ArrayList<>()).build())));

                    civilization = civilizationRepository.save(civilization.toBuilder()
                            .id(UUID.randomUUID().toString())
                            .age(ageRepository.findByName(INITIAL_AGE).orElse(ageRepository.findAll().iterator().next()))
                            .players(players)
                            .treasury(treasuryRepository.save(new Treasury()))
                            .world(worldRepository.findAll().iterator().next())
                            .townHall(mapEntityRepository.save((TownHall) new TownHall(x, y).buildMap(mapRepository.findAll().iterator().next())))
                            .build());

                    Civilization finalCivilization = civilization;
                    civilization.getPlayers().forEach(player -> playerRepository.save(player.toBuilder().civilization(finalCivilization).build()));
                    civilization.getTownHall().setCivilization(finalCivilization);
                    civilization.getWorld().getCivilizations().add(civilization);
                    mapEntityRepository.save(civilization.getTownHall());
                    worldRepository.save(civilization.getWorld());
                });

    }
}
