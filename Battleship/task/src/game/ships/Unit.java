package game.ships;

public interface Unit {
    String getName();

    int getLength();

    int getWidth();

    boolean isAlive();

    void dealDamage();
}
