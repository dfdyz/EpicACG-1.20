package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.utils.GlobalVal;
import com.dfdyz.epicacg.utils.MathUtils;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.AxisAngle4d;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.SAO_DEATH_PARTICLE;
import static net.minecraft.util.Mth.nextFloat;

public class SAODeathParticleInternal extends Particle {
    private float rotation;
    private Vector3f vector;
    private float rotSpeed;

    private int style;

    protected SAODeathParticleInternal(ClientLevel p_107670_, double x, double y, double z, double speedx, double speedy, double speedz, int lifetime) {
        super(p_107670_, x, y, z, speedx, speedy, speedz);

        vector = new Vector3f(nextFloat(random,0, 2)-1f,
                nextFloat(random,0,2)-1f,
                nextFloat(random,0,2)-1F);
        vector.normalize();

        rotation = nextFloat(random,0,360f);
        this.rotSpeed = nextFloat(random,0,30) + 10;
        this.lifetime = lifetime;
        this.style = random.nextInt(8);
        this.gravity = 0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SAO_DEATH_PARTICLE;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd += 0.01f * GlobalVal.WX;
        this.zd += 0.01f * GlobalVal.WZ;



        //rotation.set(rotation.i(),rotation.j(),rotation.k(), (float) (rotation.r() + rotSpeed/180*Math.PI));
    }

    @Override
    public void render(VertexConsumer vertexBuffer, Camera camera, float pt) {
        if(!PostEffectPipelines.isActive()) return;
        SAO_DEATH_PARTICLE.callPipeline();
        float agef = this.age + pt;
        float s = (float) Math.sqrt(1-Math.min(1f,Math.max(0,agef-1)/lifetime));

        Vec3 camPos = camera.getPosition();
        float f = (float)(Mth.lerp(pt, this.xo, this.x) - camPos.x());
        float f1 = (float)(Mth.lerp(pt, this.yo, this.y) - camPos.y());
        float f2 = (float)(Mth.lerp(pt, this.zo, this.z) - camPos.z());

        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};

        float f4 = 0.15f * s;
        Quaternionf quaternion = new Quaternionf(new AxisAngle4f(MathUtils.toDegrees(rotation + rotSpeed*agef), vector));
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        int c = style % 4;
        int r = style / 4;
        float f7 = c * 0.25f;
        float f8 = (c+1) * 0.25f;
        float f5 = r * 0.25f;
        float f6 = (r+1) * 0.25f;
        int lightColor = RenderUtils.EmissiveLightPos;
        vertexBuffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha*s).uv(f8, f6).uv2(lightColor).endVertex();
        vertexBuffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha*s).uv(f8, f5).uv2(lightColor).endVertex();
        vertexBuffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha*s).uv(f7, f5).uv2(lightColor).endVertex();
        vertexBuffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(this.rCol, this.gCol, this.bCol, this.alpha*s).uv(f7, f6).uv2(lightColor).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        //private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            //this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SAODeathParticleInternal particle = new SAODeathParticleInternal(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 30);
            return particle;
        }
    }
}
