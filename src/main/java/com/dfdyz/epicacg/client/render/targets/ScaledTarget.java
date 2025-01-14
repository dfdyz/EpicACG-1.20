package com.dfdyz.epicacg.client.render.targets;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;

public class ScaledTarget extends RenderTarget {
    float scaleW;
    float scaleH;

    public ScaledTarget(float scaleW, float scaleH, int W, int H, boolean useDepth, boolean pClearError) {
        super(useDepth);
        this.scaleW = scaleW;
        this.scaleH = scaleH;
        RenderSystem.assertOnRenderThreadOrInit();
        this.resize(W, H, pClearError);
    }

    @Override
    public void resize(int pWidth, int pHeight, boolean pClearError) {
        super.resize((int)(pWidth * scaleW), (int)(pHeight * scaleH), pClearError);
    }
}