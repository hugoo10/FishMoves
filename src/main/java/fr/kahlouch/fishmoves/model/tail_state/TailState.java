package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;

public abstract class TailState {

    public abstract TailState nextState();

    public abstract TailSide getTailSide();
}
