package com.dfdyz.epicacg.client.render.targets;

import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.utils.Function.ScreenResizeEventHandler;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import static net.minecraft.client.Minecraft.ON_OSX;

public class TargetManager {
    private static final Stack<RenderTarget> freeTargets = new Stack<>();
    private static final HashMap<ResourceLocation, RenderTarget> busyTarget = Maps.newHashMap();

    public static ScreenResizeEventHandler OnResize = (w,h) -> {};

    private static int lastW, lastH;

    public static RenderTarget getTarget(ResourceLocation handle){
        if(busyTarget.containsKey(handle)) return busyTarget.get(handle);
        else {
            RenderTarget rt = getTargetRaw();
            busyTarget.put(handle, rt);
            return rt;
        }
    }

    public static void ReleaseAll(){
        RenderTarget main = Minecraft.getInstance().getMainRenderTarget();

        boolean shouldResize = lastW != main.width || lastH != main.height;

        busyTarget.forEach((k, v)->{
            freeTargets.push(v);
        });

        if(shouldResize){
            freeTargets.forEach((v) -> {
                v.resize(main.width, main.height, ON_OSX);
            });
        }

        lastW = main.width;
        lastH = main.height;

        OnResize.consume(main.width, main.height);
        busyTarget.clear();
    }

    public static void ReleaseTarget(ResourceLocation handle){
        if(busyTarget.containsKey(handle)){
            freeTargets.add(busyTarget.remove(handle));
        }
    }

    private static RenderTarget getTargetRaw(){
        RenderTarget rt;
        if(freeTargets.isEmpty()){
            rt = PostParticleRenderType.createTempTarget(Minecraft.getInstance().getMainRenderTarget());
        }
        else {
            rt = freeTargets.pop();
        }

        return rt;
    }
}
