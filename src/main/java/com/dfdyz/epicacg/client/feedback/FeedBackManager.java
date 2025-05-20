package com.dfdyz.epicacg.client.feedback;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.feedback.channel.AbstractRumbleChannel;
import com.dfdyz.epicacg.client.feedback.clip.AbstractRumbleClip;
import com.dfdyz.epicacg.compat.controllable.ControllableCompat;
import com.google.common.collect.EvictingQueue;

import java.util.concurrent.atomic.AtomicReference;

public class FeedBackManager {
    protected static final EvictingQueue<AbstractRumbleChannel> queue = EvictingQueue.create(32);

    public static void rumble(AbstractRumbleChannel channel){
        queue.add(channel);
    }

    public static void tick(){
        try{
            AtomicReference<Float> h = new AtomicReference<>((float) 0);
            AtomicReference<Float> l = new AtomicReference<>((float) 0);

            queue.removeIf((rumbleChannel -> {
                h.set(Math.max(h.get(), rumbleChannel.samplerH()));
                l.set(Math.max(l.get(), rumbleChannel.samplerL()));

                rumbleChannel.tick();
                return rumbleChannel.isRemoved();
            }));

            doRumble(l.get(), h.get(), 200);
        }catch (Exception e){
            EpicACG.LOGGER.warn(e.toString());
            doRumble(0, 0, 100);
        }
    }

    public static void doRumble(float lowFrequency, float highFrequency, int timeInMs){
        ControllableCompat.Impl.rumble(lowFrequency, highFrequency, timeInMs);
    }
}
