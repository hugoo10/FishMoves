package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public class TailLeftState extends TailState {
    public static final TailLeftState INSTANCE = new TailLeftState();
    private TailLeftState(){}



    @Override
    public TailState nextState() {
        return TailMiddleFromLeftState.INSTANCE;
    }

    @Override
    public TailSide getTailSide() {
        return TailSide.LEFT;
    }
}
