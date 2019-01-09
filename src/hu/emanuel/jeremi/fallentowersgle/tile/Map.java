package hu.emanuel.jeremi.fallentowersgle.tile;

public class Map {

    /**
     * This is the smallest unit of a map. It contains: - tile id for floor,
     * ceiling, wall - location data (inside, outside)
     *
     * @author Jeremi
     *
     */
    public class Cell {

        public boolean inside;
        public int ceiling, floor, wall;
        public int height;
    }

    public static final int mapWidth = 26;
    public static final int mapHeight = 24;

}
