package com.dfdyz.epicacg.client.shaderpasses;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.shaderOrthoMatrix;

public class BlackHole extends PostPassBase {
    public BlackHole(EffectInstance effect) {
        super(effect);
    }

    public BlackHole(String resourceLocation, ResourceManager resmgr) throws IOException {
        super(resourceLocation, resmgr);
    }


    public void process(RenderTarget inTarget, RenderTarget mask, RenderTarget outTarget) {
        prevProcess(inTarget, outTarget);
        inTarget.unbindWrite();

        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);
        this.effect.setSampler("Mask", mask::getColorTextureId);

        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);

        float wh = -1;
        if(inTarget.height > 0) wh = inTarget.width * 1.f / inTarget.height;

        this.effect.safeGetUniform("wh_Len")
                .set(wh, 0.1f);


        //Minecraft minecraft = Minecraft.getInstance();
        //this.effect.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
        this.effect.apply();

        pushVertex(inTarget, outTarget);

        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
    }
}
