package com.dfdyz.epicacg.client.feedback;

import com.dfdyz.epicacg.client.feedback.rumble.IRumbleClip;
import com.dfdyz.epicacg.compat.controllable.ControllableCompat;

public class FeedBackManager {

    public enum RumbleType{
        ATTACKING, HURT, GROUND_SHAKE
    }

    static IRumbleClip current;

    public static void rumble(RumbleType type, IRumbleClip clip){
        // todo
        // check type to make rumble interrupt
        if(current != null){
            current.interrupt();
        }
        current = clip;
        current.play();
    }

    public static void stop(RumbleType type){
        if(current != null){
            current.interrupt();
        }
        current = null;
    }

    public static void doRumble(float lowFrequency, float highFrequency, int timeInMs){
        ControllableCompat.Impl.rumble(lowFrequency, highFrequency, timeInMs);
    }
}
