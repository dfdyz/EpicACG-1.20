package com.dfdyz.epicacg.client.particle;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.CustomModelParticle;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.awt.*;

import static yesman.epicfight.client.particle.EntityAfterImageParticle.WHITE;

public class EntityAfterImgParticleEx extends CustomModelParticle<SkinnedMesh> {
    private OpenMatrix4f[] poseMatrices;
    private Matrix4f modelMatrix;
    private float alphaO;

    public EntityAfterImgParticleEx(ClientLevel level, double x, double y, double z, double xd, double yd, double zd,
                                    AssetAccessor<SkinnedMesh> particleMesh,
                                    OpenMatrix4f[] matrices,
                                    Matrix4f modelMatrix) {
        super(level, x, y, z, xd, yd, zd, particleMesh);
        this.poseMatrices = matrices;
        this.modelMatrix = modelMatrix;
        this.lifetime = 20;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.alphaO = 0.3F;
        this.alpha = 0.3F;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return EpicACGRenderType.BLOOM_POSITION_COLOR_LIGHTMAP;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.alphaO = this.alpha;
        this.alpha = (float)(this.lifetime - this.age) / (float)this.lifetime * 0.8F;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        /*
        PoseStack poseStack = new PoseStack();
        this.setupPoseStack(poseStack, camera, partialTicks);
        poseStack.mulPoseMatrix(this.modelMatrix);

        AnimationShaderInstance animShader = EpicFightRenderTypes.getAnimationShader(GameRenderer.getPositionColorLightmapShader());
        this.particleMeshProvider.get().drawWithShader(poseStack, animShader,
                RenderUtils.EmissiveLightPos, this.rCol, this.gCol, this.bCol, alpha,
                OverlayTexture.NO_OVERLAY, null, this.poseMatrices);*/

        PoseStack poseStack = new PoseStack();
        boolean useAnimationShader = ClientConfig.activateAnimationShader && this.poseMatrices.length <= 50;
        if (useAnimationShader) {
            poseStack.mulPoseMatrix(RenderSystem.getModelViewMatrix());
        }

        this.setupPoseStack(poseStack, camera, partialTicks);
        poseStack.mulPoseMatrix(this.modelMatrix);
        float alpha = this.alphaO + (this.alpha - this.alphaO) * partialTicks;
        RenderSystem.setShaderTexture(0, WHITE);

        this.particleMeshProvider.get().drawPosed(poseStack, vertexConsumer,
                Mesh.DrawingFunction.POSITION_COLOR_LIGHTMAP,
                this.getLightColor(partialTicks), this.rCol, this.gCol, this.bCol,
                alpha, OverlayTexture.NO_OVERLAY, null, this.poseMatrices);



    }

    @Override
    protected void setAlpha(float alpha) {
        super.setAlpha(alpha);
        alphaO = alpha;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        // x: entityId      y,z: animationId(namespaceId & animId)    xSpeed: elapsedTime       ySpeed: colorInt      zSpeed: lifeTime
        public Provider(SpriteSet spriteSet) {

        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            /*
            Entity entity = level.getEntity((int)Double.doubleToLongBits(x));
            LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            Color color = new Color((int)Double.doubleToRawLongBits(ySpeed), true);
            int lt = (int)Double.doubleToRawLongBits(zSpeed);

            if (entitypatch != null && ClientEngine.getInstance().renderEngine.hasRendererFor(entitypatch.getOriginal())) {
                PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = entitypatch.getArmature();
                PoseStack poseStack = new PoseStack();
                OpenMatrix4f[] matrices = renderer.getPoseMatrices(entitypatch, armature, 1.0F, true);
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);
                var mesh = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal()).getMeshProvider(entitypatch);
                Vec3 pos = entity.getPosition(0.5f);
                EntityAfterImgParticleEx particle =
                        new EntityAfterImgParticleEx(level, pos.x() , pos.y(), pos.z(), 0, 0, 0, lt,
                                mesh, matrices, poseStack.last().pose());
                particle.setColor(color.getRed() / 255.f, color.getGreen() / 255.f, color.getBlue() / 255.f);
                particle.setAlpha(color.getAlpha() / 255.f);
                return particle;
            }*/

            Entity entity = level.getEntity((int)Double.doubleToLongBits(x));
            LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            Color color = new Color((int)Double.doubleToRawLongBits(ySpeed), true);
            int lt = (int)Double.doubleToRawLongBits(zSpeed);

            if (entitypatch != null && ClientEngine.getInstance().renderEngine.hasRendererFor(entitypatch.getOriginal())) {
                PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = entitypatch.getArmature();
                PoseStack poseStack = new PoseStack();
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);

                var pose = entitypatch.getAnimator().getPose(1.0F);
                renderer.setJointTransforms(entitypatch, armature, pose, 1.0F);
                OpenMatrix4f[] matrices = armature.getPoseAsTransformMatrix(pose, true);

                AssetAccessor<SkinnedMesh> meshProvider = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal()).getMeshProvider(entitypatch);
                EntityAfterImgParticleEx particle = new EntityAfterImgParticleEx(level, x, y, z, xSpeed, ySpeed, zSpeed, meshProvider, matrices, poseStack.last().pose());

                particle.setColor(color.getRed() / 255.f, color.getGreen() / 255.f, color.getBlue() / 255.f);
                particle.setAlpha(color.getAlpha() / 255.f);
                particle.setLifetime(lt);

                System.out.println("Success");
                return particle;
            }

            System.out.println("Failed");


            return null;
        }
    }

}
