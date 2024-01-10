package greymerk.roguelike.util.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class Graph {

    protected List<Point> points;
    protected List<Edge> edges;

    public Graph(Random rand, int size, int edgeLength) {
        this(rand, size, edgeLength, new Coord(0, 0, 0));
    }

    public Graph(Random rand, int size, int edgeLength, Coord origin) {

        points = new ArrayList<>();

        int offset = size / 2 * edgeLength;

        for (int i = 0; i < size; ++i) {

            Coord temp = new Coord(origin);
            temp.add(Cardinal.NORTH, offset);
            temp.add(Cardinal.WEST, offset);
            temp.add(Cardinal.SOUTH, edgeLength * i);

            for (int j = 0; j < size; ++j) {
                points.add(new Point(new Coord(temp), rand));
                temp.add(Cardinal.EAST, edgeLength);
            }
        }

        edges = new ArrayList<>();
        for (Point p : points) {
            for (Point o : points) {
                if (p.equals(o)) continue;
                edges.add(new Edge(p, o));
            }
        }
    }

    public List<Coord> getPointPositions() {

        List<Coord> positions = new ArrayList<>();

        for (Point p : points) {
            Coord toAdd = p.getPosition();
            positions.add(toAdd);
        }

        return positions;
    }
}
