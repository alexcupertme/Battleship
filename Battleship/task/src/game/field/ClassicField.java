package game.field;

import game.field.errors.IncorrectAreaPassedException;
import game.field.errors.IncorrectPointPassedException;
import game.field.errors.ShipAlreadyInUseException;
import game.ships.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ClassicField implements Field {
    private final int height;

    private final int width;

    private final Cell[][] grid;

    private final ArrayList<Unit> units = new ArrayList<>();

    private final String name;

    public ClassicField(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        this.name = name;

        this.fillField();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public Cell[][] getGrid() {
        return this.grid;
    }

    @Override
    public Cell getCell(Point point) throws IncorrectPointPassedException {
        if (isPointValid(point))
            return this.grid[point.posY()][point.posX()];
        throw new IncorrectPointPassedException("You entered the wrong coordinates! Try again:");
    }

    @Override
    public void placeUnit(Unit unit, Area area) throws IncorrectAreaPassedException, ShipAlreadyInUseException {
        if (!isAreaValid(area)) throw new IncorrectAreaPassedException("Error! Area out of range or busy!");
        if (!isAreaMatchesUnit(unit, area)) throw new IncorrectAreaPassedException(String.format("Error! Wrong length of the %s!", unit.getName()));
        if (units.contains(unit)) throw new ShipAlreadyInUseException("Error! Ship already in use!");

        units.add(unit);
        claimCells(unit, area);
    }

    public boolean isDestroyed() {
        return Stream.of(grid).flatMap(Arrays::stream).noneMatch(x -> x.getState() == CellState.OCCUPIED);
    }

    private boolean isAreaMatchesUnit(Unit unit, Area area) {
        int unitLength = unit.getLength();
        int unitWidth = unit.getWidth();

        int areaLength = area.getPoint2().posX() - area.getPoint1().posX() + 1;
        int areaWidth = area.getPoint2().posY() - area.getPoint1().posY() + 1;

        return (unitLength == areaLength && unitWidth == areaWidth) || (unitLength == areaWidth && unitWidth == areaLength);
    }

    private void claimCells(Unit unit, Area area) {
        for (int i = area.getPoint1().posX(); i <= area.getPoint2().posX(); i++) {
            for (int j = area.getPoint1().posY(); j <= area.getPoint2().posY(); j++) {
                try {
                    this.getCell(new Point(i, j)).setOwner(unit);
                } catch (Exception ignored) { }
            }
        }
    }

    private boolean isAreaValid(Area area) {
        Point point1 = area.getPoint1();
        Point point2 = area.getPoint2();
        if (!(this.isPointValid(point1) && this.isPointValid(point2))) return false;

        var posX1 = point1.posX();
        var posY1 = point1.posY();

        var posX2 = point2.posX();
        var posY2 = point2.posY();

        return isAreaFree(posX1, posY1, posX2, posY2) &&
                isAreaFree(posX1, posY1 - 1, posX2, posY1 - 1) &&
                isAreaFree(posX2 + 1, posY1, posX2 + 1, posY2) &&
                isAreaFree(posX1, posY2 + 1, posX2, posY2 + 1) &&
                isAreaFree(posX1 - 1, posY1, posX1 - 1, posY2);

    }

    private boolean isAreaFree(int posX1, int posY1, int posX2, int posY2) {
        try {
            for (int i = posX1; i <= posX2; i++) {
                for (int j = posY1; j <= posY2; j++) {
                    if (!(this.getCell(new Point(i, j)).getState() == CellState.FOG_OF_WAR)) return false;
                }
            }
            return true;
        } catch (IncorrectPointPassedException e) {
            return true;
        }
    }

    private boolean isPointValid(Point point) {
        var resultX = point.posX() >= 0 && point.posX() < this.width;
        var resultY = point.posY() >= 0 && point.posY() < this.height;

        return resultX && resultY;
    }

    private void fillField() {
        for (var i = 0; i < this.height; i++) {
            for (var j = 0; j < this.width; j++) {
                grid[i][j] = new Cell();
            }
        }
    }
}