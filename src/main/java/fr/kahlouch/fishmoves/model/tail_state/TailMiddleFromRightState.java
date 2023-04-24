package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailMiddleFromRightState extends TailState {
    public static final TailMiddleFromRightState INSTANCE = new TailMiddleFromRightState();
    private TailMiddleFromRightState(){}

    @Override
    public TailState nextState() {
        return TailLeftState.INSTANCE;
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.MIDDLE;
    }
}
