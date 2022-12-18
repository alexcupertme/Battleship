import game.CustomUI;
import game.GameService;
import game.field.ClassicField;
import game.field.Field;
import game.ships.Ship;

public class Main {

    public static void main(String[] args) {
        Field player1Field = new ClassicField(10, 10, "Player 1");
        Field player2Field = new ClassicField(10, 10, "Player 2");

        CustomUI customUI = new CustomUI();

        GameService game1 = new GameService(customUI);
        GameService game2 = new GameService(customUI);


        game1.initializeGame(player1Field,
                Ship.AIRCRAFT_CARRIER,
                Ship.BATTLESHIP,
                Ship.SUBMARINE,
                Ship.CRUISER,
                Ship.DESTROYER);

        game2.initializeGame(player2Field,
                Ship.AIRCRAFT_CARRIER,
                Ship.BATTLESHIP,
                Ship.SUBMARINE,
                Ship.CRUISER,
                Ship.DESTROYER);

        while (true) {
            game1.takeShot(player1Field, player2Field);
            if (player2Field.isDestroyed()) {
                game1.end();
                return;
            }
            game2.takeShot(player2Field, player1Field);
            if (player1Field.isDestroyed()) {
                game2.end();
                return;
            }
        }
    }
}