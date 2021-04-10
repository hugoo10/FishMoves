package fr.kahlouch.fishmoves.model;

import fr.kahlouch.fishmoves.view.FishView;


public class Fish extends MovingEntity {
    private final FishView view;


    public Fish(int id, double posX, double posY, World world) {
        super(id, posX, posY, world);
        this.view = new FishView(this);
    }

    @Override
    public double getAngleInRadian() {
        return Math.atan(this.delta.getY() / this.delta.getX()) + (this.delta.getX() < 0 ? Math.PI : 0);
    }

    @Override
    public void move(long time) {
        flyTowardPoint();
        avoidPoint();
        matchPoint();
        limitSpeed(time);
        bounce();
        this.lastMoveTime = time;
        this.position = this.position.add(this.delta);
        this.addMove(this.position);
    }

    public void updateView(long time) {
        this.view.updateView(this, time);
    }

    public FishView getView() {
        return this.view;
    }
}
