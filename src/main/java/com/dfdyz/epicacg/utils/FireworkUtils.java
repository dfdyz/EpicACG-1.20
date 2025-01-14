package com.dfdyz.epicacg.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class FireworkUtils {
    public static CompoundTag getFireworks(CompoundTag ...tags){
        ListTag listTag = new ListTag();

        for (CompoundTag t:tags) {
            listTag.addTag(0, t);
        }

        CompoundTag tag = new CompoundTag();
        tag.put("Explosions", listTag);
        return tag;
    }

    public static CompoundTag getFirework(boolean trail, boolean flicker, byte type, int[] colors, int[] fadeColors){
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Trail", trail);
        tag.putBoolean("Flicker", flicker);
        tag.putByte("Type", type);
        tag.putIntArray("Colors", colors);
        tag.putIntArray("FadeColors", fadeColors);
        return tag;
    }

    public static final CompoundTag Explode = getFireworks(
            //getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361}),
            getFirework(true, true, (byte) 1,new int[]{16738665}, new int[]{62463})
    );

    public static final CompoundTag Red = getFireworks(
            //getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361}),
            getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361})
    );

    public static final CompoundTag Green = getFireworks(
            //getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361}),
            getFirework(true, true, (byte) 0,new int[]{65290}, new int[]{16741632})
    );

    public static final CompoundTag Yellow = getFireworks(
            //getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361}),
            getFirework(true, true, (byte) 0,new int[]{62463}, new int[]{13865361})
    );

    public static final CompoundTag Blue = getFireworks(
            //getFirework(true, true, (byte) 0,new int[]{16738665}, new int[]{13865361}),
            getFirework(true, true, (byte) 0,new int[]{16252672}, new int[]{16741632})
    );

    public static final CompoundTag[] GS_Yoimiya_SA = {
        Green,Red,Yellow,Blue,Green,Red,Yellow,Blue,Green,Red,Yellow,Blue
    };

}
