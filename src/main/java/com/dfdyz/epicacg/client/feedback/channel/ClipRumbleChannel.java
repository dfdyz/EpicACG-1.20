package com.dfdyz.epicacg.client.feedback.channel;

import com.dfdyz.epicacg.client.feedback.clip.AbstractRumbleClip;

public class ClipRumbleChannel extends AbstractRumbleChannel {
    private final AbstractRumbleClip clip;

    public ClipRumbleChannel (AbstractRumbleClip clip) {
        super(clip.lifetime()+1);
        this.clip = clip;
    }

    @Override
    public float samplerH(){
        return clip.samplerH(age);
    }

    @Override
    public float samplerL() {
        return clip.samplerL(age);
    }
}
