package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.client.render.custom.BloomParticleRenderType;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.util.Mth.nextFloat;

public class JCBladeTrail extends SingleQuadParticle {
    protected float timeOffset = 0f;
    protected final double X,Y,Z;

    public JCBladeTrail(ClientLevel level, double x, double y, double z, double rx, double ry, double rz) {
        super(level, x, y, z, rx, ry, rz);
        this.lifetime = 9;
        timeOffset = nextFloat(random,0,1);
        X = x;
        Y = y;
        Z = z;
        this.xd = rx;
        this.yd = ry;
        this.zd = rz;

        rCol = 0.55f;
        gCol = 0.6902f;
        bCol = 1;
        alpha = 0.8f;
    }

    @Override
    public boolean shouldCull() {
        return false;
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
    public void render(VertexConsumer buffer, Camera camera, float pt) {
        if(!PostEffectPipelines.isActive()) return;
        renderType.callPipeline();

        float at = this.age+pt;
        if(at < timeOffset || at > lifetime+timeOffset) return;
        float t = Math.min(1, at / this.lifetime * 2.5f);

        Vec3 vec3 = camera.getPosition();

        float f = (float)(this.X - vec3.x());
        float f1 = (float)(this.Y - vec3.y());
        float f2 = (float)(this.Z - vec3.z());

        Vector3f right = new Vector3f(f,f1,f2);
        Vector3f dir = new Vector3f((float) this.xd, (float) this.yd, (float) this.zd);

        dir.mul(t);
        right.cross(dir);
        right.normalize();
        float _t = (float) Math.sqrt(Math.min(1, (lifetime - at) / lifetime * 2.5f));
        right.mul(0.015f*_t);

        Vector3f left = new Vector3f(right);
        left.mul(-1);

        //Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        //vector3f1.transform(quaternion);

        Vector3f[] points = new Vector3f[]{
                new Vector3f(right),
                new Vector3f(left),
                new Vector3f(right),
                new Vector3f(right),
        };

        points[2].add(dir);
        points[3].add(dir);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = points[i];
            vector3f.add(f, f1, f2);
            //vector3f.mul(0f);
            //vector3f.add(f, f1, f2);
        }

        float u0 = 0;
        float u1 = 1;

        float v0 = 0;
        float v1 = 1;

        //System.out.println(t);
        int light = RenderUtils.EmissiveLightPos;

        buffer.vertex(points[0].x(), points[0].y(), points[0].z()).color(this.rCol,this.gCol,this.bCol,this.alpha).uv(u1, v1).uv2(light).endVertex();
        buffer.vertex(points[1].x(), points[1].y(), points[1].z()).color(this.rCol,this.gCol,this.bCol,this.alpha).uv(u1, v0).uv2(light).endVertex();
        buffer.vertex(points[2].x(), points[2].y(), points[2].z()).color(this.rCol,this.gCol,this.bCol,this.alpha).uv(u0, v0).uv2(light).endVertex();
        buffer.vertex(points[3].x(), points[3].y(), points[3].z()).color(this.rCol,this.gCol,this.bCol,this.alpha).uv(u0, v1).uv2(light).endVertex();
    }

    static ResourceLocation texture = RenderUtils.GetTexture("particle/sparks");

    static BloomParticleRenderType renderType = EpicACGRenderType.getBloomRenderTypeByTexture(texture);

    @Override
    public ParticleRenderType getRenderType() {
        return renderType;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        public Provider(SpriteSet spriteSet) {
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new JCBladeTrail(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
