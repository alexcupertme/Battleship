package game.field;

public class Area {
    private Point point1;
    private Point point2;

    public Area(Point point1, Point point2) {
        if (point1.posX() > point2.posX() || point1.posY() > point2.posY()) {
            this.point2 = point1;
            this.point1 = point2;
        }
        else {
            this.point1 = point1;
            this.point2 = point2;
        }
    }

    public Point getPoint1() {
        return this.point1;
    }

    public Point getPoint2() {
        return this.point2;
    }
}
