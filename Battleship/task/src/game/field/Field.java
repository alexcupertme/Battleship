package game.field;

import game.field.errors.IncorrectAreaPassedException;
import game.field.errors.IncorrectPointPassedException;
import game.field.errors.ShipAlreadyInUseException;
import game.ships.Unit;

public interface Field {
    String getName();

    /**
     * @return length of the game field in cells
     */
    int getHeight();

    /**
     * @return length of the game field in cells
     */
    int getWidth();

    boolean isDestroyed();

    Cell[][] getGrid();

    Cell getCell(Point point) throws IncorrectPointPassedException;

    void placeUnit(Unit unit, Area area) throws IncorrectAreaPassedException, ShipAlreadyInUseException;
}