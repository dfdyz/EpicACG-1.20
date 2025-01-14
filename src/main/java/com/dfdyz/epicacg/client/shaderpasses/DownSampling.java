package com.dfdyz.epicacg.client.shaderpasses;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.function.Consumer;

public class DownSampling extends PostPassBase {
    public DownSampling(EffectInstance effect) {
        super(effect);
    }

    public DownSampling(String resourceLocation, ResourceManager resmgr) throws IOException {
        super(resourceLocation, resmgr);
    }

    @Override
    public void process(RenderTarget inTarget, RenderTarget outTarget, Consumer<EffectInstance> uniformConsumer) {
        prevProcess(inTarget, outTarget);
        inTarget.unbindWrite();

        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);

        this.effect.safeGetUniform("ProjMat").set(orthographic(outTarget));
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);
        this.effect.safeGetUniform("InSize").set((float) inTarget.width, (float) inTarget.height);

        if(uniformConsumer != null){
            uniformConsumer.accept(effect);
        }
        this.effect.apply();

        pushVertex(inTarget, outTarget);

        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
    }
}
