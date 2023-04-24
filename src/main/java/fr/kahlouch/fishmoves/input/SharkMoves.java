package fr.kahlouch.fishmoves.input;

import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.gameresources.input_handling.InputHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public class SharkMoves extends InputHandler {
    @Override
    protected ICommand convertInputToCommand(InputEvent inputEvent) {
        if (inputEvent instanceof MouseEvent me) {
            return new MoveShark(me.getSceneX(), me.getSceneY());
        }
        throw new RuntimeException();
    }
}
