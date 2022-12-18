package game;

import game.field.Area;
import game.field.CellState;
import game.field.Field;
import game.field.Point;
import game.ships.Ship;
import game.ships.Unit;

public class GameService {
    private CustomUI ui;
    public GameService(CustomUI ui) {
        this.ui = ui;

    }
    public void initializeGame(Field field, Unit...units) {
        ui.displayInfo(field.getName() + ", place your ships on the game field\n");
        ui.renderUI(field);
        for (var i = 0; i < units.length;) {
            Area area = ui.getAreaForUnit(units[i]);
            try {
                field.placeUnit(units[i], area);
                ui.renderUI(field);
                i++;
            } catch (Exception e) {
                ui.displayInfo(e.getMessage());
            }
        }
        ui.showPassMoveDialog();
    }

    public void takeShot(Field myField, Field enemyField) {
        while (true) {
            try {
                ui.renderFogUI(enemyField);
                ui.displayInfo("---------------------");
                ui.renderUI(myField);
                ui.displayInfo(myField.getName() + ", it's your turn:");
                Point point = ui.getShotPosition();
                var cell = enemyField.getCell(point);
                cell.destroy();
                if (cell.getState() == CellState.MISS) ui.displayInfo("You missed!");
                else if (cell.getState() == CellState.HIT) {
                    if (cell.getOwner().get().isAlive()) ui.displayInfo("You hit a ship!");
                    else {
                        ui.displayInfo("You sank a ship!");
                        if (enemyField.isDestroyed()) return;
                    }
                }
                ui.showPassMoveDialog();
                break;

            } catch (Exception e) {
                ui.displayInfo(e.getMessage());
            }
        }
    }

    public void end() {
        ui.displayInfo("You sank the last ship. You won. Congratulations!");
    }
}
