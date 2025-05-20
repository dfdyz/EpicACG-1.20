package com.dfdyz.epicacg.client.feedback.channel;

public class HitRumbleChannel extends AbstractRumbleChannel{
    public HitRumbleChannel(int lifetime) {
        super(lifetime);
    }

    @Override
    public float samplerH() {
        return .6f;
    }

    @Override
    public float samplerL() {
        return 1f;
    }
}
