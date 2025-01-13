package com.dfdyz.epicacg.client.shaderpasses;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.shaderOrthoMatrix;

public class HsvFilter extends PostPassBase {
    public HsvFilter(String resourceLocation, ResourceManager resmgr) throws IOException {
        super(resourceLocation, resmgr);
    }

    public void process(RenderTarget inTarget, RenderTarget outTarget, float center, float w) {
        prevProcess(inTarget, outTarget);
        inTarget.unbindWrite();

        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);

        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);
        this.effect.safeGetUniform("Hue_W").set(center, w);
        this.effect.apply();

        pushVertex(inTarget, outTarget);

        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
    }
}
