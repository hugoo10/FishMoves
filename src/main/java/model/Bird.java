package model;

import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Bird extends MovingEntity {
    private final static int ANGLE_STEP = 10;
    private int angleInDegree;


    public Bird(int id, double posX, double posY, World world) {
        super(id, posX, posY, world);
        this.angleInDegree = new Random().nextInt(360);
    }

    @Override
    public int getAngleInDegree() {
        return angleInDegree;
    }

    @Override
    public double getAngleInRadian() {
        return Math.toRadians(this.angleInDegree);
    }

    @Override
    public void move(long time) {
        long delta = time - this.lastMoveTime;
        double distance = (delta / 1000D) * SPEED;
        double distanceX = distance * Math.cos(getAngleInRadian());
        double distanceY = distance * Math.sin(getAngleInRadian());
        Optional<MovingEntity> optionalBird = getClosest();
        //GAUCHE OU DROITE
        if (position.x + distanceX < 0 || position.x + distanceX >= 1920) {
            angleInDegree = (540 - angleInDegree) % 360;
        }
        //HAUT OU BAS
        else if (position.y + distanceY < 0 || position.y + distanceY >= 1080) {
            angleInDegree = 360 - angleInDegree;
        } else if (optionalBird.isPresent()) {
            final int angleToTake;
            MovingEntity closestBird = optionalBird.get();
            double distanceBird = closestBird.position.distance(this.position);

            if (distanceBird <= TOO_CLOSE_DISTANCE) {
                angleToTake = angleToEscape((Bird) closestBird);
            } else if (distanceBird >= TOO_FAR_DISTANCE) {
                angleToTake = angleToGoToCenterOfGravity();
            } else {
                angleToTake = closestBird.getAngleInDegree();
            }
            //this.angleInDegree = (getPasToGoTo(angleToTake) + this.angleInDegree) % 360;
            this.angleInDegree = angleToTake;
        }
        doMove(time);
    }

    private void doMove(long time) {
        long delta = time - this.lastMoveTime;
        double distance = (delta / 1000D) * SPEED;
        double distanceX = distance * Math.cos(getAngleInRadian());
        double distanceY = distance * Math.sin(getAngleInRadian());
        this.position.setLocation(position.x + distanceX, position.y + distanceY);
        this.lastMoveTime = time;
    }

    public Point2D.Double getCenterOfGravity() {
        double xSum = 0;
        double ySum = 0;
        for (MovingEntity bird : world.getMovingEntities().stream().filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE).collect(Collectors.toList())) {
            xSum += bird.position.x;
            ySum += bird.position.y;
        }
        return new Point2D.Double(xSum / world.getMovingEntities().size(), ySum / world.getMovingEntities().size());
    }


    public int angleToGoTo(Bird bird) {
        double hyp = bird.position.distance(this.position);
        double adj = new Point2D.Double(bird.position.x, this.position.y).distance(this.position);
        double angle = Math.acos(adj / hyp);
        double angleInDegree = Math.toDegrees(angle);
        if (bird.isUpperBird(this) && bird.isLeftToBird(this)) {
            angleInDegree = 90 + angleInDegree;
        } else if (bird.isUnderBird(this) && bird.isLeftToBird(this)) {
            angleInDegree = 180 + angleInDegree;
        } else if (bird.isUnderBird(this) && bird.isRightToBird(this)) {
            angleInDegree = 270 + angleInDegree;
        }
        return (int) angleInDegree;
    }

    public int angleToGoToCenterOfGravity() {
        Point2D.Double centerOfGravity = getCenterOfGravity();
        double hyp = centerOfGravity.distance(this.position);
        double adj = new Point2D.Double(centerOfGravity.x, this.position.y).distance(this.position);
        double angle = Math.acos(adj / hyp);
        double angleInDegree = Math.toDegrees(angle);
        if (isAUpperB(centerOfGravity, this.position) && isALeftToB(centerOfGravity, this.position)) {
            angleInDegree = 90 + angleInDegree;
        } else if (isAUnderB(centerOfGravity, this.position) && isALeftToB(centerOfGravity, this.position)) {
            angleInDegree = 180 + angleInDegree;
        } else if (isAUnderB(centerOfGravity, this.position) && isARightToB(centerOfGravity, this.position)) {
            angleInDegree = 270 + angleInDegree;
        }
        return (int) angleInDegree;
    }

    public int angleToEscape(Bird bird) {
        return (angleToGoTo(bird) + 180) % 360;
    }

    public int getPasToGoTo(int angle) {
        int leftToRight = angle - (this.angleInDegree - 360);
        int rightToLeft = angle - this.angleInDegree;
        int multiplicateur = 1;
        if (Math.abs(leftToRight) > Math.abs(rightToLeft)) {
            multiplicateur = -1;
        }
        return multiplicateur * ANGLE_STEP;
    }

    public void alignWith(Bird bird) {
        angleInDegree = bird.angleInDegree;
    }

    public boolean isUnderBird(Bird bird) {
        return isAUnderB(this.position, bird.position);
    }

    public boolean isUpperBird(Bird bird) {
        return isAUpperB(this.position, bird.position);
    }

    public boolean isLeftToBird(Bird bird) {
        return isALeftToB(this.position, bird.position);
    }

    public boolean isRightToBird(Bird bird) {
        return isARightToB(this.position, bird.position);
    }

    public boolean isAUnderB(Point2D.Double a, Point2D.Double b) {
        return a.y < b.y;
    }

    public boolean isAUpperB(Point2D.Double a, Point2D.Double b) {
        return a.y > b.y;
    }

    public boolean isALeftToB(Point2D.Double a, Point2D.Double b) {
        return a.x < b.x;
    }

    public boolean isARightToB(Point2D.Double a, Point2D.Double b) {
        return a.x > b.x;
    }
}
