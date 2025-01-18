package com.dfdyz.epicacg.client.particle;

import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.*;

public class MyTextureSheetParticle extends SingleQuadParticle {
    protected final int maxframe;
    protected final ResourceLocation texture;

    protected MyTextureSheetParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, ResourceLocation texture) {
        super(level, x, y, z, xd, yd, zd);
        maxframe = (int)yd;
        this.texture = texture;
        this.setLifetime((int)zd);
    }

    @Override
    protected float getU0() {
        return 0;
    }

    @Override
    protected float getU1() {
        return 0;
    }

    @Override
    protected float getV0() {
        return 0;
    }

    @Override
    protected float getV1() {
        return 0;
    }

    @Override
    public void tick() {
        if (this.age++ > this.lifetime) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return getRenderTypeByTexture(texture);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float pt) {
        //RenderUtils.GLSetTexture(texture);

        Vec3 vec3 = camera.getPosition();
        float rt = (age+pt)/lifetime;
        float f = (float)(Mth.lerp(pt, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(pt, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(pt, this.zo, this.z) - vec3.z());
        Quaternionf quaternion = camera.rotation();
        
        Vector3f[] vertexes = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = 0.8f;

        //System.out.println(rt);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = vertexes[i];
            vector3f.rotate(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        int t = Math.min(maxframe-1, (int) Math.floor(rt * maxframe));
        float per = 1.0f/maxframe;
        float u0 = t * per;
        float u1 = (t+1) * per;

        float v0 = 0;
        float v1 = 1;

        int light = this.getLightColor(pt);
        buffer.vertex(vertexes[0].x(), vertexes[0].y(), vertexes[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[1].x(), vertexes[1].y(), vertexes[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[2].x(), vertexes[2].y(), vertexes[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[3].x(), vertexes[3].y(), vertexes[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static class BloodThirstyProvider implements ParticleProvider<SimpleParticleType> {
        public BloodThirstyProvider(){}
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MyTextureSheetParticle(worldIn, x, y+0.5, z, 1, 6, 10, BLOOD_THIRSTY_PARTICLE_TEX);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class BlackKnightProvider implements ParticleProvider<SimpleParticleType> {
        public BlackKnightProvider(){}
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MyTextureSheetParticle(worldIn, x, y+0.5, z, 0, 7, 10, BLACK_KNIGHT_PARTICLE_TEX);
        }
    }

}
