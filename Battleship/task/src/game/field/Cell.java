package game.field;

import game.field.errors.PointAlreadyShotException;
import game.ships.Unit;

import java.util.Optional;

public class Cell {
    private Optional<Unit> unit;
    private CellState state;

    public Cell() {
        this.unit = Optional.empty();
        this.state = CellState.FOG_OF_WAR;
    }

    public Optional<Unit> getOwner() {
        return this.unit;
    }

    public void setOwner(Unit unit) {
        if (this.unit.isEmpty()) {
            this.unit = Optional.ofNullable(unit);
            this.state = CellState.OCCUPIED;
        }
    }

    public CellState getState() {
        return this.state;
    }

    public void destroy() throws PointAlreadyShotException {
        if (this.unit.isEmpty()) {
            this.state = CellState.MISS;
            return;
        }

        if (this.state == CellState.OCCUPIED) {
            this.unit.get().dealDamage();
        }
        this.state = CellState.HIT;
    }
}