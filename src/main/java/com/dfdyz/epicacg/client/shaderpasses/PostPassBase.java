package com.dfdyz.epicacg.client.shaderpasses;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.function.Consumer;

public class PostPassBase {
    protected EffectInstance effect;
    public PostPassBase(EffectInstance effect){
        this.effect = effect;
    }

    public PostPassBase(String resourceLocation, ResourceManager resmgr) throws IOException {
        this(new EffectInstance(resmgr, resourceLocation));
    }

    protected static Matrix4f orthographic(RenderTarget out){
        return new Matrix4f().setOrtho(0.0F, (float)out.width, 0.0F, (float)out.height, 0.1F, 1000.0F);
    }

    protected void setParameter(EffectInstance effect, RenderTarget inTarget, RenderTarget outTarget){

    }

    public void process(RenderTarget inTarget, RenderTarget outTarget){
        process(inTarget, outTarget, null);
    }

    public void process(RenderTarget inTarget, RenderTarget outTarget, Consumer<EffectInstance> uniformConsumer)
    {
        prevProcess(inTarget, outTarget);
        inTarget.unbindWrite();

        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);

        this.effect.safeGetUniform("ProjMat").set(orthographic(outTarget));
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);

        if(uniformConsumer != null){
            uniformConsumer.accept(effect);
        }

        //Minecraft minecraft = Minecraft.getInstance();
        //this.effect.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
        this.effect.apply();

        pushVertex(inTarget, outTarget);

        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
    }

    public void prevProcess(RenderTarget inTarget, RenderTarget outTarget){

    }
    public void pushVertex(RenderTarget inTarget, RenderTarget outTarget){
        outTarget.clear(Minecraft.ON_OSX);
        outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(outTarget.width, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(outTarget.width, outTarget.height, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, outTarget.height, 500.0D).endVertex();
        BufferUploader.draw(bufferbuilder.end());
        RenderSystem.depthFunc(515);
    }
}
