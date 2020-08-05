package model;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CountResult {
    private final Point2D point2D;
    private final int number;

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
        return new CountResult(new Point2D(movingEntity.dX, movingEntity.dY), 1);
    }
}
