package com.dfdyz.epicacg.client.feedback.channel;

import com.dfdyz.epicacg.client.feedback.clip.AbstractRumbleClip;
import net.minecraft.util.Mth;

public class ShockRumbleChannel extends AbstractRumbleChannel {

    public final float lowFrequency, highFrequency, close_time;
    public final int top_time;

    private float scale = 0;

    public ShockRumbleChannel(float lowFrequency, float highFrequency, int lifetime) {
        super(lifetime);
        this.lowFrequency = lowFrequency;
        this.highFrequency = highFrequency;
        top_time = Math.max(1, Mth.ceil(lifetime * 0.15));
        close_time = lifetime - top_time;
    }

    @Override
    public void tick() {
        super.tick();
        if(age < top_time){
            scale = 1f;
        }
        else if(age >= lifetime){
            return;
        }
        else {
            float a = (age - top_time) / close_time;
            scale = (a-1)*(a-1);
        }
    }

    @Override
    public float samplerH() {
        return scale * lowFrequency;
    }

    @Override
    public float samplerL() {
        return scale * lowFrequency;
    }
}
