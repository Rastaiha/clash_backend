package clash.back.handler;

import clash.back.domain.entity.Map;
import clash.back.domain.entity.building.Location;
import clash.back.util.pathFinding.GameRouter;

public class MapHandler extends DefaultHandler {
    static {
        RELOAD_INTERVAL = 1000L;
    }

    Map map;
    GameRouter router;

    public MapHandler(Map map) {
        this.map = map;
        router = new GameRouter(map);
    }

    @Override
    public void init() {
        super.init();
        System.out.println("MAP HANDLERRR INITIALIZED!!!");
    }

    @Override
    void handle() {
        router.findRoute(new Location(0, 0), new Location(10, 4));
    }
}
