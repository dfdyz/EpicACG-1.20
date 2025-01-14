package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.CustomModelParticle;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.shader.AnimationShaderInstance;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class DynamicEntityAfterImgParticle extends CustomModelParticle<AnimatedMesh> {
    private OpenMatrix4f[] poseMatrices;
    private Matrix4f modelMatrix;

    public DynamicEntityAfterImgParticle(ClientLevel level,
                                         double x, double y, double z,
                                         double xd, double yd, double zd,
                                         int lft,
                                         MeshProvider<AnimatedMesh> particleMesh,
                                         OpenMatrix4f[] matrices,
                                         Matrix4f modelMatrix) {
        super(level, x, y, z, xd, yd, zd, particleMesh);
        this.poseMatrices = matrices;
        this.modelMatrix = modelMatrix;
        this.lifetime = lft;

        this.yaw = (float) (Vec3f.getAngleBetween(new Vec3f((float) xd, 0, (float) -zd), Vec3f.X_AXIS) / Math.PI * 180f);
        if(zd > 0) yaw = -yaw;
        yaw += 90;
        yawO = yaw;

        this.hasPhysics = false;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;


        this.pitch = -(float) (Vec3f.getAngleBetween(new Vec3f((float) xd, 0, (float) -zd),
               new Vec3f((float) xd, (float) yd, (float) -zd)) / Math.PI * 180f);

        pitch += 10;

        pitchO = pitch;

        alpha = 0.9f;
    }

    public void SetScale(float s){
        scale = s;
        scaleO = s;
    }

    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.TRANSLUCENT;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.pitchO = this.pitch;
            this.yawO = this.yaw;
            this.oRoll = this.roll;
            this.scaleO = this.scale;

            x += xd;
            y += yd;
            z += zd;

            this.setPos(x,y,z);
        }

    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        PoseStack poseStack = new PoseStack();
        this.setupPoseStack(poseStack, camera, partialTicks);
        poseStack.mulPoseMatrix(this.modelMatrix);

        AnimationShaderInstance animShader = EpicFightRenderTypes.getAnimationShader(GameRenderer.getPositionColorLightmapShader());
        this.particleMeshProvider.get().drawWithShader(poseStack, animShader,
                RenderUtils.EmissiveLightPos, this.rCol, this.gCol, this.bCol, alpha,
                OverlayTexture.NO_OVERLAY, null, this.poseMatrices);
    }


    public static DynamicEntityAfterImgParticle create(
            LivingEntityPatch<?> entitypatch,
            StaticAnimation animation,
            double x, double y, double z,
            double xd, double yd, double zd,
            int lifeTime,
            float animTime
    ) {
        PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());

        Armature armature = entitypatch.getArmature();
        var mesh = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal()).getMeshProvider(entitypatch);

        Pose modelPose = animation.getPoseByTime(entitypatch, animTime, 0);
        modelPose.getOrDefaultTransform("Root").translation().set(0,0,0);

        PoseStack poseStack = new PoseStack();
        OpenMatrix4f[] matrices = renderer.getPoseMatrices(entitypatch, armature, 1.0F, true);
        MulPoseWithoutRot(entitypatch, poseStack);
        DynamicEntityAfterImgParticle particle = new DynamicEntityAfterImgParticle(
                (ClientLevel) entitypatch.getOriginal().level(), x,y,z, xd,yd,zd, lifeTime, mesh, matrices, poseStack.last().pose()
        );
        return particle;
    }

    static final Quaternionf rot_quat_y = new Quaternionf().rotateY(180);
    static final Quaternionf rot_quat_z = new Quaternionf().rotateZ(180);
    public static void MulPoseWithoutRot(LivingEntityPatch<?> entitypatch, PoseStack poseStack){
        float scale = entitypatch.getOriginal().isBaby() ? 0.5F : 1.0F;
        OpenMatrix4f modelMatrix = MathUtils.getModelMatrixIntegral(0.0F, 0.0F, 0.0F, 0.0F,
                0.0F, 0.0F, 0.0F, 0.0F, 0f,0f, 0.5f, scale, scale, scale);
        OpenMatrix4f transpose = modelMatrix.transpose(null);

        poseStack.mulPose(rot_quat_y);

        MathUtils.translateStack(poseStack, modelMatrix);
        MathUtils.rotateStack(poseStack, transpose);
        MathUtils.scaleStack(poseStack, transpose);

        LivingEntity livingEntity = entitypatch.getOriginal();

        if (LivingEntityRenderer.isEntityUpsideDown(livingEntity)) {
                poseStack.translate(0.0, livingEntity.getBbHeight() + 0.1F, 0.0);
                poseStack.mulPose(rot_quat_z);
        }
    }




    /*
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        // x: entityId      y,z: animationId(namespaceId & animId)    xSpeed: elapsedTime       ySpeed: colorInt      zSpeed: lifeTime
        public Provider(SpriteSet spriteSet) {

        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Entity entity = level.getEntity((int)Double.doubleToLongBits(x));
            LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            int modid = (int)Double.doubleToRawLongBits(y);
            int animid = (int)Double.doubleToRawLongBits(z);
            StaticAnimation animation = AnimationManager.getInstance().byId(animid);

            Color color = new Color((int)Double.doubleToRawLongBits(ySpeed), true);
            int lt = (int)Double.doubleToRawLongBits(zSpeed);

            if (entitypatch != null && ClientEngine.getInstance().renderEngine.hasRendererFor(entitypatch.getOriginal())) {
                PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = entitypatch.getArmature();

                Pose modelPose = animation.getPoseByTime(entitypatch, (float) xSpeed, 0);

                PoseStack poseStack = new PoseStack();
                OpenMatrix4f[] matrices = armature.getPoseAsTransformMatrix(modelPose);
                        //renderer.getPoseMatrices(entitypatch, armature, 1.0F);
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);

                for (int i = 0; i < matrices.length; i++) {
                    matrices[i] = OpenMatrix4f.mul(matrices[i], armature.searchJointById(i).getToOrigin(), null);
                }

                AnimatedMesh mesh = renderer.getMesh(entitypatch);

                Vec3 pos = entity.getPosition(0.5f);
                DynamicEntityAfterImgParticle particle =
                        new DynamicEntityAfterImgParticle(level, pos.x() , pos.y(), pos.z(), 0, 0, 0, lt,
                                mesh, matrices, poseStack.last().pose());
                particle.setColor(color.getRed() / 255.f, color.getGreen() / 255.f, color.getBlue() / 255.f);
                particle.setAlpha(color.getAlpha() / 255.f);

                //System.out.println("particle");

                return particle;
            }

            return null;
        }
    }*/

}
