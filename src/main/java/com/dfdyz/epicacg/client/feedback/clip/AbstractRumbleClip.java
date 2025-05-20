package com.dfdyz.epicacg.client.feedback.clip;

public abstract class AbstractRumbleClip {

    public abstract float samplerH(int tick);
    public abstract float samplerL(int tick);
    public abstract int lifetime();
}
