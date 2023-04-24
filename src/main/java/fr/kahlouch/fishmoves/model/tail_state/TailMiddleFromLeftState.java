package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailMiddleFromLeftState extends TailState {
    public static final TailMiddleFromLeftState INSTANCE = new TailMiddleFromLeftState();
    private TailMiddleFromLeftState(){}

    @Override
    public TailState nextState() {
        return TailRightState.INSTANCE;
    }


    @Override
    public TailSide getTailSide() {
        return TailSide.MIDDLE;
    }
}
