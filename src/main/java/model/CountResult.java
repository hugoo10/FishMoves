package model;

import javafx.geometry.Point2D;
import lombok.Getter;


@Getter
public class CountResult {
    private final Point2D point2D;
    private final int number;

    public CountResult(Point2D point2D, int number) {
        this.point2D = new Point2D(point2D.getX(), point2D.getY());
        this.number = number;
    }

    public CountResult() {
        this(new Point2D(0, 0), 0);
    }

    public CountResult add(CountResult countResult) {
        return new CountResult(this.point2D.add(countResult.point2D), this.number + countResult.number);
    }


    public static CountResult fromMovingEntityPosition(MovingEntity movingEntity) {
        return new CountResult(new Point2D(movingEntity.position.getX(), movingEntity.position.getY()), 1);
    }

    public static CountResult fromMovingEntityDelta(MovingEntity movingEntity) {
        return new CountResult(movingEntity.delta, 1);
    }
}
