package com.dfdyz.epicacg.client.feedback.clip;

import com.dfdyz.epicacg.EpicACG;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;

public class SimpleRumbleClip extends AbstractRumbleClip{
    private static final Gson gson = new Gson();
    final int[] H, L;

    SimpleRumbleClip(int[] h, int[] l) {
        H = h;
        L = l;
    }

    private record hl(int[] high, int[] low){ }

    public static SimpleRumbleClip fromJson(JsonObject jsonObject){
        try{
            var hl = gson.fromJson(jsonObject, TypeToken.get(hl.class));
            return new SimpleRumbleClip(hl.high, hl.low);
        }catch (Exception e){
            EpicACG.LOGGER.error(e.toString());
            return null;
        }

    }

    @Override
    public float samplerH(int tick) {
        if(tick < H.length)
            return H[tick];
        return 0;
    }

    @Override
    public float samplerL(int tick) {
        if(tick < L.length)
            return L[tick];
        return 0;
    }

    @Override
    public int lifetime() {
        return Math.max(L.length, H.length);
    }
}
