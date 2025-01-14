package com.dfdyz.epicacg.client.particle.GenshinImpact;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
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

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.GS_BOW_SHOOT_PARTICLE_TEX;

public class GenShinBowShootParticle extends TextureSheetParticle {
    protected final int lifeTick;


    protected GenShinBowShootParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, int lifeTick) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y+0.5d;
        this.z = z;
        this.rCol = (float) xd;
        this.gCol = (float) yd;
        this.bCol = (float) zd;
        this.gravity = 0;
        this.lifeTick = lifeTick;
        this.lifetime = lifeTick;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 0.9019607f;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicACGRenderType.getBloomRenderTypeByTexture(GS_BOW_SHOOT_PARTICLE_TEX);
    }

    private Vector3f[] avector3f = new Vector3f[]{new Vector3f(-3.0F, -3.0F, 0.0F), new Vector3f(-3.0F, 3.0F, 0.0F), new Vector3f(3.0F, 3.0F, 0.0F), new Vector3f(3.0F, -3.0F, 0.0F)};

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tick) {
        if(!PostEffectPipelines.isActive()) return;

        EpicACGRenderType.getBloomRenderTypeByTexture(GS_BOW_SHOOT_PARTICLE_TEX).callPipeline();

        Vec3 vec3 = camera.getPosition();
        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y() - 0.8);
        float f2 = (float)(this.z - vec3.z());
        Quaternionf quaternion = camera.rotation();

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.rotate(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternion);
            vector3f.mul(0.6f);
            vector3f.add(f, f1, f2);
        }

        float u0 = age * 1f/7;
        float u1 = Math.min(1, (age+1) * 1f/7);
        int light = this.getLightColor(tick);

        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, 1).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u1, 0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, 0).uv2(light).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(u0, 1).uv2(light).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GenShinBowShootParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed,7);
        }
    }
}
