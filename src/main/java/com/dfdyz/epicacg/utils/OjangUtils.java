package com.dfdyz.epicacg.utils;


import net.minecraft.resources.ResourceLocation;

// Mojang你的马呢？
@SuppressWarnings("removal")
public class OjangUtils {

    public static ResourceLocation newRL(String n_p){
        return new ResourceLocation(n_p);
    }

    public static ResourceLocation newRL(String n, String p){
        return new ResourceLocation(n, p);
    }
}
