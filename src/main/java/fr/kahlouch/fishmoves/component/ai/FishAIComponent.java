package fr.kahlouch.fishmoves.component.ai;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.World;
import fr.kahlouch.fishmoves.util.Variables;
import javafx.geometry.Point2D;

import java.util.List;

public class FishAIComponent implements AIComponent {
    private static final double FLY_TOWARD_FACTOR = 0.005;
    private static final double AVOID_FACTOR = 0.05;
    private static final double MATCH_FACTOR = 0.05;

    @Override
    public void update(GameEntity gameEntity, World world) {
        gameEntity.delta = changeDeltaDependingOnOtherFish(gameEntity, world.getEntities());
    }


    private Point2D changeDeltaDependingOnOtherFish(GameEntity gameEntity, List<GameEntity> otherEntities) {
        var moveAwayDelta = new Point2D(0, 0);
        var moveAwayNb = 0;
        var getCloserDelta = new Point2D(0, 0);
        var getCloserNb = 0;
        var stayInRangeDelta = new Point2D(0, 0);
        var stayInRangeNb = 0;

        for (GameEntity other : otherEntities) {
            if (areAtViewDistance(gameEntity, other)) {
                if (needToGetCloser(gameEntity, other)) {
                    getCloserDelta = getCloserDelta.add(other.position);
                    ++getCloserNb;
                } else if (needToMoveAway(gameEntity, other)) {
                    moveAwayDelta = moveAwayDelta.add(gameEntity.position.subtract(other.position));
                    ++moveAwayNb;
                } else {
                    stayInRangeDelta = stayInRangeDelta.add(other.delta);
                    ++stayInRangeNb;
                }
            }
        }

        var newDelta = gameEntity.delta;
        if (moveAwayNb > 0) {
            newDelta = moveAwayDelta.multiply(AVOID_FACTOR);
        }

        if (getCloserNb > 0) {
            newDelta = newDelta.add(getCloserDelta.subtract(gameEntity.position).multiply(FLY_TOWARD_FACTOR));
        }

        if (stayInRangeNb > 0) {
            var averageDelta = stayInRangeDelta.multiply(1D / stayInRangeNb);
            newDelta = newDelta.add(averageDelta.subtract(newDelta).multiply(MATCH_FACTOR));
        }
        return newDelta;

    }

    private boolean needToGetCloser(GameEntity entity, GameEntity other) {
        return other.position.distance(entity.position) > Variables.TOO_FAR_DISTANCE;
    }

    private boolean needToMoveAway(GameEntity entity, GameEntity other) {
        return other.position.distance(entity.position) < Variables.TOO_CLOSE_DISTANCE;
    }

    private boolean areAtViewDistance(GameEntity entity, GameEntity other) {
        return entity.id != other.id && entity.position.distance(other.position) < Variables.VIEW_DISTANCE;
    }
}
