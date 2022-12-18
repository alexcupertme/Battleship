package game.ships;

public enum Ship implements Unit {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    CRUISER("Cruiser", 3),
    SUBMARINE("Submarine", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int length;
    private final int width = 1;

    private int health;

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.health = length;
    }

    public String getName() {
        return name;
    }

    public void dealDamage() {
        this.health -= 1;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}