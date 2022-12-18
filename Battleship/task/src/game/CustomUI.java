package game;

import game.field.*;
import game.ships.Unit;

import java.util.Scanner;

public class CustomUI implements UI {
    private final int capitalLettersSpan = 65;

    public void renderUI(Field field) {
        var grid = field.getGrid();

        this.renderHorizontalCoordinates(grid.length);

        for (var row = 0; row < grid.length; row++) {
            this.renderRow(grid[row], row);
        }
    }

    public void renderFogUI(Field field) {
        var grid = field.getGrid();

        this.renderHorizontalCoordinates(grid.length);

        for (var row = 0; row < grid.length; row++) {
            this.renderFogRow(grid[row], row);
        }
    }

    public Area getAreaForUnit(Unit unit) {
        System.out.printf("\n\nEnter the coordinates of the %s (%d cells):\n\n", unit.getName(), unit.getLength());

        try {
            return getAreaFromConsole();
        } catch (Exception e) {
            System.out.println("Error: you passed invalid data. Example: J5 J6");
            return null;
        }
    }

    public void displayInfo(String message) {
        System.out.println("\n" + message);
    }

    public void showStartGameMessage() {
        System.out.println("\n\nThe game starts!\n");
    }

    public Point getShotPosition() {
        Scanner sc = new Scanner(System.in);

        var input = sc.next();
        return convertInputIntoPoint(input);
    }

    public void showPassMoveDialog() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress Enter and pass the move to another player");
        sc.nextLine();
        System.out.flush();
    }

    private Area getAreaFromConsole() {
        Scanner sc = new Scanner(System.in);

        var points = sc.nextLine().split(" ");


        return new Area(convertInputIntoPoint(points[0]), convertInputIntoPoint(points[1]));
    }

    private Point convertInputIntoPoint(String input) {
        var posY = input.charAt(0) - capitalLettersSpan;
        var posX = Integer.parseInt(String.valueOf(input.substring(1)));
        return new Point(posX - 1, posY);
    }

    private void renderHorizontalCoordinates(int gridLength) {
        System.out.println();
        // Margin to the right
        System.out.print("  ");
        for (var i = 0; i < gridLength; i++) {
            System.out.print(i + 1 + " ");
        }
    }

    private void renderRow(Cell[] row, int index) {
        System.out.println();
        System.out.print(this.getVerticalCoordinate(index) + " ");
        for (var cell : row) {
            this.renderCell(cell);
        }
    }

    private void renderFogRow(Cell[] row, int index) {
        System.out.println();
        System.out.print(this.getVerticalCoordinate(index) + " ");
        for (var cell : row) {
            this.renderFogCell(cell);
        }
    }

    private void renderCell(Cell cell) {
        System.out.print(cell.getState() + " ");
    }

    private void renderFogCell(Cell cell) {
        if (cell.getState() == CellState.OCCUPIED)
            System.out.print(CellState.FOG_OF_WAR + " ");
        else System.out.print(cell.getState() + " ");
    }

    private char getVerticalCoordinate(int index) {
        return (char) (index + capitalLettersSpan);
    }
}