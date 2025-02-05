package com.dfdyz.epicacg.client.feedback.rumble;

import com.dfdyz.epicacg.client.feedback.FeedBackManager;
import net.minecraft.util.Mth;

public class SimpleRumbleClip implements IRumbleClip {

    public final float lowFrequency, highFrequency, close_time;
    public final int lifetime, wait_time, top_time;

    Thread thread;

    public SimpleRumbleClip(float lowFrequency, float highFrequency, int timeInMs, float sampler_rate) {
        this.lowFrequency = lowFrequency;
        this.highFrequency = highFrequency;
        this.lifetime = timeInMs;
        this.wait_time = Mth.floor(1000.f / sampler_rate);
        top_time = Math.max(Math.max(1, wait_time / 2), Mth.ceil(timeInMs * 0.15));
        close_time = timeInMs - top_time;

        System.out.println("Top "+ top_time);
        System.out.println("Close " + close_time);
    }

    //int age = 0;
    void handler(){
        System.out.println("Play Rumble");
        try {
            int age = 0;
            float scale;
            while (!Thread.currentThread().isInterrupted()){
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
        if(thread != null){
            thread.interrupt();
        }
        thread = new Thread(this::handler);
        thread.start();
    }

    @Override
    public void interrupt() {
        if(thread != null){
            thread.interrupt();
        }
    }
}
