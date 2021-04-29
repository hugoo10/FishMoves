package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailMiddleFromLeftState extends TailState {

    @Override
    protected TailState nextState() {
        return new TailRightState();
    }


    @Override
    public TailSide getTailSide() {
        return TailSide.MIDDLE;
    }
}
