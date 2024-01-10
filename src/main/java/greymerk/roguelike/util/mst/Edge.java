package greymerk.roguelike.util.mst;

public class Edge implements Comparable<Edge> {

    private final Point start;
    private final Point end;
    private final double length;

    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.length = start.distance(end);
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(length, other.length);
    }

    public Point[] getPoints() {
        Point[] toReturn = new Point[2];
        toReturn[0] = start;
        toReturn[1] = end;
        return toReturn;
    }
}
