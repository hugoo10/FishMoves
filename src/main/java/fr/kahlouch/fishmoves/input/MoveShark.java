package fr.kahlouch.fishmoves.input;

import fr.kahlouch.fishmoves.model.Shark;
import fr.kahlouch.gameresources.input_handling.ICommand;

public record MoveShark(double x, double y) implements ICommand {

    @Override
    public void execute() {
        Shark.INSTANCE.setPosition(x, y);
    }

    @Override
    public void undo() {

    }
}
