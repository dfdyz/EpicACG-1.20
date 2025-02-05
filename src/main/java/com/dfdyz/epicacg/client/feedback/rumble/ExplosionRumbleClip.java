package com.dfdyz.epicacg.client.feedback.rumble;

import com.dfdyz.epicacg.client.feedback.FeedBackManager;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.RandomUtils;

public class ExplosionRumbleClip implements IRumbleClip {

    public final float lowFrequency, highFrequency, close_time;
    public final int lifetime, wait_time, top_time;

    final Thread thread;
    boolean started = false;

    public ExplosionRumbleClip(float lowFrequency, float highFrequency, int timeInMs, float sampler_rate) {
        this.lowFrequency = lowFrequency;
        this.highFrequency = highFrequency;
        this.lifetime = timeInMs;
        this.wait_time = Mth.floor(1000.f / sampler_rate);
        top_time = Math.max(Math.max(1, wait_time / 2), Mth.ceil(timeInMs * 0.15));
        close_time = timeInMs - top_time;
        thread = new Thread(this::handler);
    }

    int age = 0;
    void handler(){
        //System.out.println("Play ExplosionRumble");
        try {
            started = true;
            age = 0;
            float scale, jitter;
            while (started){
                if(age < top_time){
                    scale = 1f;
                }
                else if(age >= lifetime){
                    started = false;
                    return;
                }
                else {
                    float a = (age - top_time) / close_time;
                    scale = (a-1)*(a-1);
                }

                jitter = RandomUtils.nextFloat(0.9f, 1.1f);

                FeedBackManager.doRumble(scale * lowFrequency, scale * highFrequency, wait_time+1);
                Thread.sleep(wait_time);
                age += wait_time;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void play() {
        if(started) return;
        thread.start();
    }

    @Override
    public void interrupt() {
        started = false;
    }
}
