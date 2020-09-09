package clash.back.util.pathFinding;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Path {
    final Queue<Station> path;

    public Path(Queue<Station> path) {
        this.path = path;
    }

    public Path(List<Station> path){
        this.path = new LinkedList<>(path);
    }

    public boolean hasNext(){
        return path.size() > 1;
    }

    public int getPathLength() {
        return path.size() - 1;
    }
    public Station getNextStation(){
        return path.poll();
    }
}
