package fr.kahlouch.fishmoves.component.ai;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.World;

public interface AIComponent {
    void update(GameEntity gameEntity, World world);
}
