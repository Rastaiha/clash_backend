package clash.back.util.pathFinding;

public class ManhattanScorer implements Scorer<Station> {
    @Override
    public double computeCost(Station from, Station to) {
        return Math.abs(to.getLocation().getY() - from.getLocation().getY()) + Math.abs(to.getLocation().getX() - from.getLocation().getX());
    }
}
