package com.dfdyz.epicacg.client.render;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.custom.*;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.dfdyz.epicacg.utils.RenderUtils.GetTexture;
import static net.minecraft.client.renderer.GameRenderer.*;

@OnlyIn(Dist.CLIENT)
public class EpicACGRenderType {
    public static final ResourceLocation ChildSkillnoSelected = GetTexture("gui/noselected");
    public static final ResourceLocation ChildSkillSelected = GetTexture("gui/selected");

    public static final ResourceLocation GS_BOW_SHOOT_PARTICLE_TEX = GetTexture("particle/genshin_bow");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX = GetTexture("particle/genshin_bow_landing");
    public static final ResourceLocation GS_BOW_LANDONG_PARTICLE_TEX3 = GetTexture("particle/genshin_bow_landing3");

    public static final ResourceLocation BLACK_KNIGHT_PARTICLE_TEX = GetTexture("particle/blackknight_hit");
    public static final ResourceLocation BLOOD_THIRSTY_PARTICLE_TEX = GetTexture("particle/bloodthirsty_hit");

    public static final BloomParticleRenderType SAO_DEATH_PARTICLE = new BloomParticleRenderType(
            new ResourceLocation(EpicACG.MODID, "sao_death"),
            GetTexture("particle/sao_death")
    );

    private static int bloomIdx = 0;
    public static final HashMap<ResourceLocation, BloomParticleRenderType> BloomRenderTypes = Maps.newHashMap();
    public static BloomParticleRenderType getBloomRenderTypeByTexture(ResourceLocation texture){
        if(BloomRenderTypes.containsKey(texture)){
            return BloomRenderTypes.get(texture);
        }
        else {
            BloomParticleRenderType bloomType = new BloomParticleRenderType(new ResourceLocation(EpicACG.MODID, "bloom_particle_" + bloomIdx++), texture);
            BloomRenderTypes.put(texture, bloomType);
            return bloomType;
        }
    }

    private static int quadIdx = 0;
    public static final HashMap<ResourceLocation, EpicACGQuadParticleRenderType> QuadRenderTypes = Maps.newHashMap();
    public static EpicACGQuadParticleRenderType getRenderTypeByTexture(ResourceLocation texture){
        if(QuadRenderTypes.containsKey(texture)){
            return QuadRenderTypes.get(texture);
        }
        else {
            EpicACGQuadParticleRenderType rdt = new EpicACGQuadParticleRenderType("epicacg:quad_particle_" + quadIdx++, texture);
            QuadRenderTypes.put(texture,rdt);
            return rdt;
        }
    }

    private static int triangleIdx = 0;
    public static final HashMap<ResourceLocation, EpicACGTriangleParticleRenderType> TriangleRenderTypes = Maps.newHashMap();
    public static EpicACGTriangleParticleRenderType getTriangleRenderTypeByTexture(ResourceLocation texture){
        if(TriangleRenderTypes.containsKey(texture)){
            return TriangleRenderTypes.get(texture);
        }
        else {
            EpicACGTriangleParticleRenderType rdt = new EpicACGTriangleParticleRenderType("epicacg:triangle_particle_" + triangleIdx++, texture);
            TriangleRenderTypes.put(texture,rdt);
            return rdt;
        }
    }


    public static SpaceBrokenRenderType SpaceBroken1 = new SpaceBrokenRenderType(new ResourceLocation(EpicACG.MODID, "space_broken" ), 0);
    public static SpaceBrokenRenderType SpaceBroken2 = new SpaceBrokenRenderType(new ResourceLocation(EpicACG.MODID, "space_broken" ), 1);

    public static SpaceBrokenRenderType SpaceBrokenEnd = new SpaceBrokenRenderType(new ResourceLocation(EpicACG.MODID, "space_broken_end" ), RenderUtils.GetTexture("particle/glass"), 0, 4);
    public static BlockHoleRenderType GravLens = new BlockHoleRenderType(new ResourceLocation(EpicACG.MODID, "black_hole"),
            GetTexture("particle/black_hole"));

    public static SubMaskRenderType SubMask = new SubMaskRenderType(new ResourceLocation(EpicACG.MODID, "sub_mask"),
            GetTexture("none")
    );

    public static SubSpaceRenderType SubSpace_BlackHole = new SubSpaceRenderType(new ResourceLocation(EpicACG.MODID, "sub_space"),
            GetTexture("none")
    );

    /*
            //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow", "GENSHIN_BOW");
    public static final ParticleRenderType GENSHIN_BOW_LANDING_PARTICLE = new PostQuadParticleRT(
                    new ResourceLocation(EpicACG.MODID, "genshin_bow")
//                    GetTextures("particle/genshin_bow_landing")
            );
                    //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow_landing", "GENSHIN_BOW");

    public static final ParticleRenderType GENSHIN_BOW_LANDING_PARTICLE3 = new PostQuadParticleRT(
            new ResourceLocation(EpicACG.MODID, "genshin_bow")
//            GetTextures("particle/genshin_bow_landing3")
    );
            //new EpicACGQuadParticleRenderType("textures/particle/genshin_bow_landing3", "GENSHIN_BOW");
*/


    public static class EpicACGTriangleParticleRenderType implements ParticleRenderType {
        private final ResourceLocation Texture;
        private final String Name;

        public EpicACGTriangleParticleRenderType(String name, ResourceLocation tex) {
            this.Texture = tex;
            Name = name;
        }

        public void begin(@NotNull BufferBuilder p_107448_, @NotNull TextureManager p_107449_) {
            RenderSystem.enableBlend();
            //RenderSystem.disableCull();
            RenderSystem.enableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            //Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);

            //System.out.println("AAAAAA");

            if(Texture != null) RenderUtils.GLSetTexture(Texture);
            p_107448_.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        public String toString() {
            return Name;
        }
    };


    public static class EpicACGQuadParticleRenderType implements ParticleRenderType {
        private final ResourceLocation Texture;
        private final String Name;

        public EpicACGQuadParticleRenderType(String name, ResourceLocation tex) {
            this.Texture = tex;
            Name = name;
        }

        public void begin(BufferBuilder p_107448_, TextureManager p_107449_) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();

            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getParticleShader);

            if(Texture != null) RenderUtils.GLSetTexture(Texture);

            p_107448_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        public String toString() {
            return Name;
        }
    };

    public static ShaderInstance getPositionColorLightmapShader(){
        return positionColorLightmapShader;
    }
    public static ShaderInstance getPositionColorTexShader(){
        return positionColorTexLightmapShader;
    }


    public static final ResourceLocation NoneTexture = RenderUtils.GetTexture("none");
    public static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            //RenderSystem.disableTexture();
            RenderUtils.GLSetTexture(NoneTexture);
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            //RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        public String toString() {
            return "EPICACG:TRANSLUCENT";
        }
    };

}
