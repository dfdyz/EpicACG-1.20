package com.dfdyz.epicacg.client.render.pipeline;

import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.vertex.VertexSorting.ORTHOGRAPHIC_Z;
import static net.minecraft.client.Minecraft.ON_OSX;


public abstract class PostParticleRenderType implements ParticleRenderType {
    protected final ResourceLocation renderTypeID;
    protected final ResourceLocation texture;
    public int priority = 0;

    //protected boolean started = false;

    public PostParticleRenderType(ResourceLocation renderTypeID, ResourceLocation texture){
        this.renderTypeID = renderTypeID;
        this.texture = texture;
    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(this::getShader);

        if(texture != null) RenderUtils.GLSetTexture(texture);
        getPipeline().start();
        setupBufferBuilder(bufferBuilder);
    }

    protected ShaderInstance getShader(){
        return GameRenderer.positionColorTexLightmapShader;
    }

    public void callPipeline(){
        getPipeline().call();
    }

    public boolean tryCallPipeline(){
        if(!PostEffectPipelines.isActive()){
            callPipeline();
            return true;
        }
        else return false;
    }

    //simple_post_particle
    @Override
    public void end(Tesselator tesselator) {
        tesselator.getBuilder().setQuadSorting(ORTHOGRAPHIC_Z);
        tesselator.end();
        getPipeline().suspend();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableCull();
    }

    public static RenderTarget createTempTarget(RenderTarget screenTarget) {
        RenderTarget rendertarget = new TextureTarget(screenTarget.width, screenTarget.height, true, ON_OSX);
        rendertarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        rendertarget.clear(ON_OSX);
        //if (screenTarget.isStencilEnabled()) { rendertarget.enableStencil(); }
        return rendertarget;
    }

    public void setupBufferBuilder(BufferBuilder bufferBuilder){
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }

    public abstract PostEffectPipelines.Pipeline getPipeline();

    static ResourceLocation tempTarget = new ResourceLocation("epicacg:depth_cull_temp");
    public static void doDepthCull(RenderTarget source, RenderTarget DepthBuffer){
        RenderTarget tmp = TargetManager.getTarget(tempTarget);
        PostPasses.blit.process(source, tmp);
        PostPasses.depth_cull.process(tmp, DepthBuffer, source);
        TargetManager.ReleaseTarget(tempTarget);
    }

    public static void Blit(RenderTarget source, RenderTarget output){
        //RenderTarget blur = createTempTarget(source);
        //RenderTarget blur2 = createTempTarget(source);

        PostPasses.blit.process(source, output,
                (effect) ->
                {}
        );

        //blur1.destroyBuffers();
        //blur2.destroyBuffers();
    }

    public String toString() {
        return renderTypeID.toString();
    }
}
