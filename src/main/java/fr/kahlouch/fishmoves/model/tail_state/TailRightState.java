package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailRightState extends TailState {

    @Override
    protected TailState nextState() {
        return new TailMiddleFromRightState();
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.RIGHT;
    }
}
