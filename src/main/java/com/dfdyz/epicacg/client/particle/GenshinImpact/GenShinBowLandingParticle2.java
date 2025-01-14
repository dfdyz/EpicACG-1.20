package com.dfdyz.epicacg.client.particle.GenshinImpact;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;

import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.utils.MathUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.GS_BOW_LANDONG_PARTICLE_TEX;

public class GenShinBowLandingParticle2 extends TextureSheetParticle {
    //protected final int lifeTick;
    protected final Quaternionf rot;
    protected GenShinBowLandingParticle2(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, int lifeTick) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y+0.05f;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.gravity = 0;
        this.lifetime = lifeTick;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 0.9019607f;
        Quaternionf rot_ = MathUtils.fromEuler(0,(float) yd,0);
        rot_.mul(MathUtils.fromEuler((float) xd,0,0));
        this.rot = rot_;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.getBloomRenderTypeByTexture(GS_BOW_LANDONG_PARTICLE_TEX);
    }

    private Vector3f[] avector3f = new Vector3f[]{new Vector3f(-3.0F, -3.0F, 0.0F), new Vector3f(-3.0F, 3.0F, 0.0F), new Vector3f(3.0F, 3.0F, 0.0F), new Vector3f(3.0F, -3.0F, 0.0F)};

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tick) {
        if(!PostEffectPipelines.isActive()) return;
        EpicACGRenderType.getBloomRenderTypeByTexture(GS_BOW_LANDONG_PARTICLE_TEX).callPipeline();
        Vec3 vec3 = camera.getPosition();
        //Quaternion quaternion = camera.rotation();

        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y());
        float f2 = (float)(this.z - vec3.z());

        //Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        //vector3f1.transform(quaternion);

        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-0.5f, 0, 0),
                new Vector3f(-0.5f, 2, 0),
                new Vector3f(0.5f, 2, 0),
                new Vector3f(0.5f, 0, 0),
                new Vector3f(0, 0, -0.5f),
                new Vector3f(0, 2, -0.5f),
                new Vector3f(0, 2, 0.5f),
                new Vector3f(0, 0, 0.5f)
        };

        for(int i = 0; i < 8; ++i) {
            Vector3f vector3f = avector3f[i];
            //vector3f.add(f, f1, f2);
            vector3f.rotate(rot);

            vector3f.mul(1.3f);
            vector3f.add(f, f1, f2);
            //vector3f.mul(0f);
            //vector3f.add(f, f1, f2);
        }

        int t = Math.min(7,(int) Math.floor((age + tick) /lifetime * 8));

        float u0 = t * 0.125f;
        float u1 = (t+1) * 0.125f;

        float v0 = 0.75f;
        float v1 = 1;

        int light = this.getLightColor(tick);

        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v1).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v1).uv2(light).endVertex();

        vertexConsumer.vertex(avector3f[4].x(), avector3f[4].y(), avector3f[4].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v1).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[5].x(), avector3f[5].y(), avector3f[5].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[6].x(), avector3f[6].y(), avector3f[6].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[7].x(), avector3f[7].y(), avector3f[7].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, v1).uv2(light).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GenShinBowLandingParticle2(worldIn, x, y, z, xSpeed, ySpeed, zSpeed,2);
        }
    }
}
