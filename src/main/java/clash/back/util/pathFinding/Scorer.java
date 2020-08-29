package clash.back.util.pathFinding;

public interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to);
}
