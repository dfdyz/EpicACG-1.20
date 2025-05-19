package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.utils.MathUtils;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;


public class StarFlashParticle extends Particle {
    private final Joint joint;
    private final TrailInfo trailInfo;
    private final AssetAccessor<StaticAnimation> animation;
    private final LivingEntityPatch<?> entitypatch;
    private boolean animationEnd = false;

    private static RenderUtils.Quad quad1 = new RenderUtils.Quad(1.2f,1.2f)
                    .rotate(Vec3f.X_AXIS, -90)
                    .move(-0.58f, 0, 0)
                    .rotate(Vec3f.Y_AXIS, 30)
                    .move(0,0.2f,0.2f);
    private static RenderUtils.Quad quad2 = new RenderUtils.Quad(1.2f,1.2f)
            .rotate(Vec3f.X_AXIS, -90)
            .move(-0.58f, 0, 0)
            .rotate(Vec3f.Y_AXIS, 150)
            .move(0,0.2f,0.2f);

    public StarFlashParticle(ClientLevel level, LivingEntityPatch<?> entitypatch, Joint joint, AssetAccessor<StaticAnimation> animation,
                             TrailInfo trailInfo) {
        super(level, 0,0,0);

        this.joint = joint;
        this.entitypatch = entitypatch;
        this.animation = animation;
        this.hasPhysics = false;
        this.trailInfo = trailInfo;

        this.lifetime = trailInfo.trailLifetime();

        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.alpha = 1;
        oAlpha = alpha;

        if(this.entitypatch.getOriginal().isAlive()){
            Vec3 pos = entitypatch.getOriginal().position();
            this.setPos(pos.x, pos.y, pos.z);
        }
    }

    float oAlpha;

    int frame = 0;
    int timer = 0;

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);
        ++this.age;
        oAlpha = alpha;
        if (this.animationEnd) {
            alpha -= 1.0f / trailInfo.trailLifetime();
            if (this.lifetime-- == 0) {
                this.remove();
            }
        } else {
            if (!this.entitypatch.getOriginal().isAlive()
                    || this.animation != animPlayer.getAnimation().get().getRealAnimation()
                    || animPlayer.getElapsedTime() > this.trailInfo.endTime()) {
                this.animationEnd = true;
                this.lifetime = this.trailInfo.trailLifetime();
            }
        }

        if(++timer > 2){
            timer = 0;
            if(++frame >= trailInfo.interpolateCount()){
                frame = 0;
            }
        }

        if(this.entitypatch.getOriginal().isAlive()){
            Vec3 pos = entitypatch.getOriginal().position();
            this.setPos(pos.x, pos.y, pos.z);
        }

        //System.out.println("????");
    }

    //ResourceLocation texture = RenderUtils.GetTextures("particle/star_flash");

    private float getAlpha(float pt){
        return Mth.lerp(pt,oAlpha, alpha);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
        if(!PostEffectPipelines.isActive() || !this.entitypatch.getOriginal().isAlive()) return;
        EpicACGRenderType.getBloomRenderTypeByTexture(trailInfo.texturePath()).callPipeline();
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);
        float elapsedTime = Mth.lerp(pt, animPlayer.getPrevElapsedTime(), animPlayer.getElapsedTime());
        //System.out.println("sssss");

        if(!animationEnd && elapsedTime < trailInfo.startTime()){
            return;
        }

        //RenderUtils.GLSetTexture(trailInfo.texturePath);

        Pose pose = this.entitypatch.getAnimator().getPose(pt);
        Vec3 pos = this.entitypatch.getOriginal().getPosition(pt);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(pt)));
        OpenMatrix4f tf = this.entitypatch.getArmature().getBindedTransformFor(pose, this.joint).mulFront(modelTf);

        Vec3f particlePos = OpenMatrix4f.transform3v(tf, new Vec3f(0, 0, -0.3f), new Vec3f());

        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};

        Vec3 camPos =  camera.getPosition();
        float x = particlePos.x - (float)camPos.x();
        float y = particlePos.y - (float)camPos.y();
        float z = particlePos.z - (float)camPos.z();

        Quaternionf camRot = camera.rotation();
        float alp = getAlpha(pt);
        float t_ = (age % 10 + pt) / 9.f;

        if(t_ <= 0.5f) t_ = 4*t_ - 1;
        else t_ = -4*t_ + 3;

        float sz =  (7.5f + 1.0f * t_) * alp;

        t_ = (age % 5 + pt) / 4f;
        if(t_ <= 0.5f) t_ = 4*t_ - 1;
        else t_ = -4*t_ + 3;

        Quaternionf rol = new Quaternionf(new AxisAngle4f(MathUtils.toDegrees(t_ * 2.5f), new Vector3f(0, 0, 1)));
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(rol);
            vector3f.mul(sz);
            vector3f.add(0,0,-0.2f);
            vector3f.rotate(camRot);
            vector3f.add(x, y, z);
        }

        float perFrame = 1.0f / trailInfo.interpolateCount();
        float u0 = 0;
        float u1 = 1;
        float v0 = perFrame * frame;
        float v1 = perFrame + perFrame * frame;

        int j = 15728880;
        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, 1).uv(u0, v0).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, 1).uv(u0, v1).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, 1).uv(u1, v1).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, 1).uv(u1, v0).uv2(j).endVertex();
        
        //quad1.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), getLightColor(pt));
        //quad2.PushVertex(vertexConsumer, Vec3f.fromDoubleVector(camera.getPosition()),tf, rCol, gCol, bCol, getAlpha(pt), getLightColor(pt));
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.getBloomRenderTypeByTexture(trailInfo.texturePath());
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet spriteSet) {
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            int eid = (int)Double.doubleToRawLongBits(x);
            int animid = (int)Double.doubleToRawLongBits(z);
            int jointId = (int)Double.doubleToRawLongBits(xSpeed);
            int idx = (int)Double.doubleToRawLongBits(ySpeed);
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                var animation = AnimationManager.byId(animid);
                var trailInfos = animation.get().getProperty(ClientAnimationProperties.TRAIL_EFFECT);

                if (trailInfos.isEmpty()) {
                    return null;
                }

                var result = trailInfos.get().get(idx);

                if (result.hand() != null) {
                    ItemStack stack = entitypatch.getOriginal().getItemInHand(result.hand());
                    RenderItemBase renderItemBase = ClientEngine.getInstance().renderEngine.getItemRenderer(stack);

                    if (renderItemBase != null && renderItemBase.trailInfo() != null) {
                        result = renderItemBase.trailInfo().overwrite(result);
                    }
                }

                if (entitypatch != null && result.playable()) {
                    return new StarFlashParticle(level, entitypatch, entitypatch.getArmature()
                            .searchJointById(jointId), animation, result);
                }
            }

            return null;
        }
    }
}
