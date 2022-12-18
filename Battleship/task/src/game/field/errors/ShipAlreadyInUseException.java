package game.field.errors;

public class ShipAlreadyInUseException extends Exception {
    public ShipAlreadyInUseException(String message) {
        super(message);
    }
}
