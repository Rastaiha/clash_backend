package clash.back.util.pathFinding;

import clash.back.domain.entity.Map;
import clash.back.domain.entity.building.Location;
import clash.back.domain.entity.building.MapEntity;

import java.util.*;

public class GameRouter {
    Map map;
    Station[][] stations;
    RouteFinder<Station> routeFinder;

    public GameRouter(Map map) {
        this.map = map;
        stations = new Station[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                int finalI = i;
                int finalJ = j;
                stations[i][j] = new Station(i, j,
                        map.getMapEntities().stream()
                                .filter(mapEntity -> mapEntity.getX() == finalI && mapEntity.getY() == finalJ).findAny());
            }

        Set<Station> nodes = new HashSet<>();
        java.util.Map<String, Set<String>> connections = new HashMap<>();
        Arrays.stream(stations).forEach(row -> nodes.addAll(Arrays.asList(row)));

        nodes.stream().filter(station -> isAvailable(station.getMapEntity())).forEach(station -> {
            HashSet<String> stationConnections = new HashSet<>();
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (isValidIndex(station.getLocation().getX() + i, station.getLocation().getY() + j) && (i != 0 || j != 0))
                        if (isAvailable(stations[station.getLocation().getX() + i][station.getLocation().getY() + j].getMapEntity()))
                            stationConnections.add(stations[station.getLocation().getX() + i][station.getLocation().getY() + j].getId());
            connections.put(station.getId(), stationConnections);
        });

        routeFinder = new RouteFinder<>(new Graph<>(nodes, connections), new ManhattanScorer(), new ManhattanScorer());
    }

    public Path findRoute(Location from, Location to) {
        List<Station> route = routeFinder.findRoute(stations[from.getX()][from.getY()], stations[to.getX()][to.getY()]);
        return new Path(route);
    }

    private boolean isValidIndex(int i, int j) {
        return i >= 0 && j >= 0 && i < map.getWidth() && j < map.getHeight();
    }

    private boolean isAvailable(Optional<MapEntity> mapEntity) {
        if (!mapEntity.isPresent())
            return true;
        switch (mapEntity.get().getClass().getSimpleName().trim().toUpperCase()) {
            case "WALL":
                return false;
            case "TREE":
                return false;
            case "TOWNHALL":
                return true;
            default:
                return true;
        }
    }
}
