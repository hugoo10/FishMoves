package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailLeftState extends TailState {

    @Override
    protected TailState nextState() {
        return new TailMiddleFromLeftState();
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.LEFT;
    }
}
