package fr.kahlouch.fishmoves.component.physics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.World;

public interface PhysicsComponent {
    void update(GameEntity gameEntity, World world, long elapsedNano);
}
