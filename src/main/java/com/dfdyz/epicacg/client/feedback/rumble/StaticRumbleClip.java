package com.dfdyz.epicacg.client.feedback.rumble;

import com.dfdyz.epicacg.client.feedback.FeedBackManager;

public class StaticRumbleClip implements IRumbleClip {

    public final float lowFrequency, highFrequency;
    public final int timeInMs;

    public StaticRumbleClip(float lowFrequency, float highFrequency, int timeInMs) {
        this.lowFrequency = lowFrequency;
        this.highFrequency = highFrequency;
        this.timeInMs = timeInMs;
    }

    @Override
    public void play() {
        FeedBackManager.doRumble(lowFrequency, highFrequency, timeInMs);
    }

    @Override
    public void interrupt() {

    }
}
