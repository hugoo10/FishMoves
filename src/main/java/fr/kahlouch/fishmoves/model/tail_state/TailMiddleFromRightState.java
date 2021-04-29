package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailMiddleFromRightState extends TailState {

    @Override
    protected TailState nextState() {
        return new TailLeftState();
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.MIDDLE;
    }
}
