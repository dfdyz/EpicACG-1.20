package com.dfdyz.epicacg.client.shaderpasses;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.shaderOrthoMatrix;

public class UEComposite extends PostPassBase {
    public UEComposite(String resourceLocation, ResourceManager resmgr) throws IOException {
        super(resourceLocation, resmgr);
    }

    public void process(RenderTarget inTarget, RenderTarget outTarget,
                        RenderTarget blur1,
                        RenderTarget blur2,
                        RenderTarget blur3,
                        RenderTarget blur4) {
        prevProcess(inTarget, outTarget);
        inTarget.unbindWrite();

        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);

        //Matrix4f shaderOrthoMatrix = Matrix4f.orthographic(0.0F, outTarget.width, outTarget.height, 0.0F, 0.1F, 1000.0F);
        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);
        //this.effect.safeGetUniform("InSize").set((float) inTarget.width, (float) inTarget.height);
        effect.setSampler("BlurTexture1", blur1::getColorTextureId);
        effect.setSampler("BlurTexture2", blur2::getColorTextureId);
        effect.setSampler("BlurTexture3", blur3::getColorTextureId);
        effect.setSampler("BlurTexture4", blur4::getColorTextureId);

        //effect.safeGetUniform("BloomRadius").set(BloomRadius);
        //effect.safeGetUniform("BloomIntensive").set(BloomIntensive);
        this.effect.apply();

        pushVertex(inTarget, outTarget);

        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
    }
}
