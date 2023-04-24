package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailRightState extends TailState {
    public static final TailRightState INSTANCE = new TailRightState();

    private TailRightState() {
    }

    @Override
    public TailState nextState() {
        return TailMiddleFromRightState.INSTANCE;
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.RIGHT;
    }
}
