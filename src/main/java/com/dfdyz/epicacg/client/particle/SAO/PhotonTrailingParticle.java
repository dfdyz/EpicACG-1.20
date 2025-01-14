package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;

public class PhotonTrailingParticle extends NoRenderParticle {
    private final Joint joint;
    private final TrailInfo trailInfo;
    private final StaticAnimation animation;
    private final LivingEntityPatch<?> entitypatch;
    private boolean animationEnd = false;

    public PhotonTrailingParticle(ClientLevel level, LivingEntityPatch<?> entitypatch, Joint joint, StaticAnimation animation, TrailInfo trailInfo) {
        super(level, 0,0,0);

        this.joint = joint;
        this.entitypatch = entitypatch;
        this.animation = animation;
        this.hasPhysics = false;
        this.trailInfo = trailInfo;

        this.lifetime = trailInfo.trailLifetime;

        if(this.entitypatch.getOriginal().isAlive()){
            Vec3 pos = entitypatch.getOriginal().position();
            this.setPos(pos.x, pos.y, pos.z);
        }
    }

    @Override
    public void tick() {
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.animationEnd) {
            if (this.lifetime-- <= 0) {
                this.remove();
            }
        } else {
            if (!this.entitypatch.getOriginal().isAlive()
                    || this.animation != animPlayer.getAnimation().getRealAnimation()
                    || animPlayer.getElapsedTime() > this.trailInfo.endTime) {
                this.animationEnd = true;
                this.lifetime = this.trailInfo.trailLifetime;
            }
        }

        if(this.entitypatch.getOriginal().isAlive() && lifetime > 0 && animPlayer.getElapsedTime() > trailInfo.startTime){
            Vec3 old = entitypatch.getOriginal().getPosition(0);
            Vec3 curr = entitypatch.getOriginal().getPosition(1);

            int count = Math.max((int) (curr.distanceTo(old) / 0.17), 2);
            //System.out.println(count);
            float per = 1.f / count;
            Photon photon;
            Vec3 pos;
            for (int i = 0; i < count; i++) {
                pos = curr.lerp(old, per * i);
                photon = new Photon(level, pos.x, pos.y, pos.z,
                        trailInfo.trailLifetime, trailInfo.rCol, trailInfo.gCol, trailInfo.bCol);

                photon.scale(25f);
                RenderUtils.AddParticle(level, photon);
            }


            this.setPos(curr.x, curr.y, curr.z);
        }


    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet spriteSet) {
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            int eid = (int)Double.doubleToRawLongBits(x);
            //int modid = (int)Double.doubleToRawLongBits(y);
            int animid = (int)Double.doubleToRawLongBits(z);
            int jointId = (int)Double.doubleToRawLongBits(xSpeed);
            int idx = (int)Double.doubleToRawLongBits(ySpeed);
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                StaticAnimation animation = AnimationManager.getInstance().byId( animid);
                Optional<List<TrailInfo>> trailInfo = animation.getProperty(ClientAnimationProperties.TRAIL_EFFECT);
                TrailInfo result = trailInfo.get().get(idx);

                if (result.hand != null) {
                    ItemStack stack = entitypatch.getOriginal().getItemInHand(result.hand);
                    ItemSkin itemSkin = ItemSkins.getItemSkin(stack.getItem());

                    if (itemSkin != null) {
                        result = itemSkin.trailInfo().overwrite(result);
                    }
                }

                if (entitypatch != null && animation != null && trailInfo.isPresent()) {
                    return new PhotonTrailingParticle(level, entitypatch, entitypatch.getArmature().searchJointById(jointId), animation, result);
                }
            }

            return null;
        }
    }


    public static class Photon extends Particle{
        protected Photon(ClientLevel level, double x, double y, double z, int lifetime, float r, float g, float b) {
            super(level,x,y,z);
            this.lifetime = lifetime;
            gravity = 0;
            hasPhysics = false;
            rCol = r;
            gCol = g;
            bCol = b;
            alpha = 1;
            oAlpha = alpha;
        }
        float oAlpha;

        private float getAlpha(float pt){
            return Mth.lerp(pt,oAlpha, alpha);
        }
        @Override
        public boolean shouldCull() {
            return false;
        }
        @Override
        public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
            float alp = getAlpha(pt);
            float t_ = (age % 10 + pt) / 9.f;
            if(t_ <= 0.5f) t_ = 4*t_ - 1;
            else t_ = -4*t_ + 3;
            float sz =  (0.5f + 0.1f * t_) * alp * Mth.sqrt(bbWidth * bbHeight);

            RenderUtils.RenderQuadFaceOnCamera(vertexConsumer, camera,
                    (float) Mth.lerp(pt, this.xo, this.x),
                    (float) Mth.lerp(pt, this.yo, this.y),
                    (float) Mth.lerp(pt, this.zo, this.z),
                    this.rCol, this.gCol, this.bCol, alp,
                    sz, pt
            );

        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            oAlpha = alpha;
            if (this.age++ >= this.lifetime) {
                this.remove();
            }
            alpha = 1.f * (lifetime - age) / lifetime;
        }

        ResourceLocation texture = RenderUtils.GetTexture("particle/photo");
        @Override
        public ParticleRenderType getRenderType() {
            return EpicACGRenderType.getBloomRenderTypeByTexture(texture);
        }
    }


}
