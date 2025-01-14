package com.dfdyz.epicacg.client.particle.DMC;

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
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import static com.dfdyz.epicacg.client.render.EpicACGRenderType.SpaceBrokenEnd;

public class SpaceBrokenEndParticle extends Particle {
    private float rotation;
    private Vector3f vector;
    private float rotSpeed;

    private int style;

    public SpaceBrokenEndParticle(ClientLevel level, double x, double y, double z, int lifetime) {
        super(level, x, y, z);

        vector = new Vector3f(Mth.nextFloat(random,-1f,1),
                Mth.nextFloat(random,-1f,1),
                Mth.nextFloat(random,-1f,1));
        vector.normalize();

        rotation = Mth.nextFloat(random,0,360);
        this.rotSpeed = Mth.nextFloat(random,5,10);
        this.lifetime = lifetime;
        this.style = random.nextInt(4);
        this.gravity = Mth.nextFloat(random,1, 2);

        this.yd = -0.1f;
    }

    @Override
    public void tick() {
        super.tick();
        if(age > 1 && y - yo > -0.1f){
            remove();
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float pt) {
        //float at = this.age+pt;
        if(!PostEffectPipelines.isActive()) return;
        EpicACGRenderType.SpaceBrokenEnd.callPipeline();

        float agef = this.age + pt;

        Vec3 camPos = camera.getPosition();
        float f = (float)(Mth.lerp(pt, this.xo, this.x) - camPos.x());
        float f1 = (float)(Mth.lerp(pt, this.yo, this.y) - camPos.y());
        float f2 = (float)(Mth.lerp(pt, this.zo, this.z) - camPos.z());

        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};

        Vector3f normal = new Vector3f(0,0, 1F);

        float f4 = 1.2f;
        Quaternionf quaternion = new Quaternionf(new AxisAngle4f(MathUtils.toDegrees(rotation + rotSpeed*agef), vector));
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        normal.rotate(quaternion);

        int c = style % 2;
        int r = style / 2;
        float f7 = c * 0.5f;
        float f8 = (c+1) * 0.5f;
        float f5 = r * 0.5f;
        float f6 = (r+1) * 0.5f;
        int lightColor = RenderUtils.EmissiveLightPos;

        Vector3f camNormal = new Vector3f(camera.getLookVector());
        camNormal.normalize();

        normal.normalize();
        float offset = Math.abs(camNormal.dot(normal));

        float camYaw = camera.getYRot();
        camYaw = (((camYaw % 360) + 360 + 180 * offset) % 360) / 360.f;
        camYaw = camYaw < 0.5 ? camYaw * 2 : - camYaw * 2 + 2;

        float camPitch = camera.getXRot();
        camPitch = camPitch / 90;
        camPitch = camPitch > 0 ? camPitch : -camPitch;

        buffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(offset,camYaw,camPitch,1).uv(f8, f6).uv2(lightColor).endVertex();
        buffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(offset,camYaw,camPitch,1).uv(f8, f5).uv2(lightColor).endVertex();
        buffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(offset,camYaw,camPitch,1).uv(f7, f5).uv2(lightColor).endVertex();
        buffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(offset,camYaw,camPitch,1).uv(f7, f6).uv2(lightColor).endVertex();
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SpaceBrokenEnd;
    }

}
