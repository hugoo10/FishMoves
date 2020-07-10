package model;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

public class Bird {
    private final static double VIEW_DISTANCE = 200;
    private final static double TOO_CLOSE_DISTANCE = 20;
    private final static double TOO_FAR_DISTANCE = 50;
    private final static int SPEED = 100;
    private final static int ANGLE_STEP = 5;

    private int id;
    private Point2D.Double position;
    private int angleInDegree;
    private World world;

    //Meta
    private long lastMoveTime;

    public Bird(int id, double posX, double posY, World world) {
        this.id = id;
        this.world = world;
        this.position = new Point2D.Double(posX, posY);
        this.angleInDegree = new Random().nextInt(360);
        this.lastMoveTime = System.currentTimeMillis();
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public int getAngleInDegree() {
        return angleInDegree;
    }

    public double getAngleInRadian() {
        return Math.toRadians(this.angleInDegree);
    }

    public void move(long time) {
        long delta = time - this.lastMoveTime;
        double distance = (delta / 1000D) * SPEED;
        double distanceX = distance * Math.cos(getAngleInRadian());
        double distanceY = distance * Math.sin(getAngleInRadian());
        Optional<Bird> optionalBird = getClosestBird();
        //GAUCHE OU DROITE
        if (position.x + distanceX < 0 || position.x + distanceX >= 1920) {
            angleInDegree = (540 - angleInDegree) % 360;
        }
        //HAUT OU BAS
        else if (position.y + distanceY < 0 || position.y + distanceY >= 1080) {
            angleInDegree = 360 - angleInDegree;
        } else if (optionalBird.isPresent()) {
            final int angleToTake;
            Bird closestBird = optionalBird.get();
            double distanceBird = closestBird.position.distance(this.position);

            /*if (distanceBird <= TOO_CLOSE_DISTANCE) {
                angleToTake = angleToEscape(closestBird);
            } else if (distanceBird >= TOO_FAR_DISTANCE) {
                angleToTake = angleToGoTo(closestBird);
            } else {
                angleToTake = angleToGoToCenterOfGravity();
            }*/
            angleToTake = angleToGoToCenterOfGravity();
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

    public Optional<Bird> getClosestBird() {
        return this.world.getBirds().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE)
                .min(Comparator.comparing(bird -> bird.position.distance(this.position)));
    }

    public Point2D.Double getCenterOfGravity() {
        double xSum = 0;
        double ySum = 0;
        for (Bird bird : world.getBirds()) {
            xSum += bird.position.x;
            ySum += bird.position.y;
        }
        return new Point2D.Double(xSum / world.getBirds().size(), ySum / world.getBirds().size());
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
        return isAUnderB(bird.position, this.position);
    }

    public boolean isUpperBird(Bird bird) {
        return isAUpperB(bird.position, this.position);
    }

    public boolean isLeftToBird(Bird bird) {
        return isALeftToB(bird.position, this.position);
    }

    public boolean isRightToBird(Bird bird) {
        return isARightToB(bird.position, this.position);
    }

    public boolean isAUnderB(Point2D.Double a, Point2D.Double b) {
        return a.y < b.y;
    }

    public boolean isAUpperB(Point2D.Double a, Point2D.Double b) {
        return a.y > b.y;
    }

    public boolean isALeftToB(Point2D.Double a, Point2D.Double b) {
        return a.x > b.x;
    }

    public boolean isARightToB(Point2D.Double a, Point2D.Double b) {
        return a.x < b.x;
    }
}
