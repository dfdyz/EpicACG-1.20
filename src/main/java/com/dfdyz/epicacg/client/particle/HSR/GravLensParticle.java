package com.dfdyz.epicacg.client.particle.HSR;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static com.dfdyz.epicacg.utils.RenderUtils.GetTexture;

public class GravLensParticle extends Particle {

    public GravLensParticle(ClientLevel level, Vec3 pos, int lifetime) {
        super(level, pos.x, pos.y, pos.z);
        this.lifetime = lifetime;
        setSize(5,5);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        if(age < 15){
            _scale = age / 15.f;
        }
        else _scale = 1;
        if(age >= lifetime - 15){
            _alpha = Math.max(0, (lifetime - age) / 15.f);
        }

        _oScale = _scale;
        _oAlpha = _alpha;
    }

    float _scale = 0f;
    float _alpha = 1f;

    float _oScale = 0f;
    float _oAlpha = 1f;

    @Override
    public void render(@NotNull VertexConsumer buffer, @NotNull Camera camera, float pt) {
        Vec3 vec3 = camera.getPosition();
        //float rt = (age+pt)/lifetime;
        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y());
        float f2 = (float)(this.z - vec3.z());

        Quaternionf quaternion = camera.rotation();

        float f4 = 8 / 0.9f * Mth.clampedLerp(_oScale,_scale,pt);
        Vector3f[] vertexes = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, -0.9f),
                new Vector3f(-1.0F, 1.0F, -0.9f),
                new Vector3f(1.0F, 1.0F, -0.9f),
                new Vector3f(1.0F, -1.0F, -0.9f)};


        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = vertexes[i];
            vector3f.rotate(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float u0 = 0;
        float u1 = 1;
        float v0 = 0;
        float v1 = 1;

        int light = RenderUtils.EmissiveLightPos;

        /*
        buffer.vertex(vertexes[0].x(), vertexes[0].y(), vertexes[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[1].x(), vertexes[1].y(), vertexes[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[2].x(), vertexes[2].y(), vertexes[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(vertexes[3].x(), vertexes[3].y(), vertexes[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
*/

        var alpha = Mth.clampedLerp(_oAlpha, _alpha, pt);

        buffer.vertex(vertexes[0].x(), vertexes[0].y(), vertexes[0].z())
                .color(this.rCol, this.gCol, this.bCol, alpha)
                .uv(u1, v1)
                .uv2(light).endVertex();
        buffer.vertex(vertexes[1].x(), vertexes[1].y(), vertexes[1].z())
                .color(this.rCol, this.gCol, this.bCol, alpha)
                .uv(u1, v0)
                .uv2(light).endVertex();
        buffer.vertex(vertexes[2].x(), vertexes[2].y(), vertexes[2].z())
                .color(this.rCol, this.gCol, this.bCol, alpha)
                .uv(u0, v0)
                .uv2(light).endVertex();
        buffer.vertex(vertexes[3].x(), vertexes[3].y(), vertexes[3].z())
                .color(this.rCol, this.gCol, this.bCol, alpha)
                .uv(u0, v1)
                .uv2(light).endVertex();
    }

    static final ResourceLocation tex =  GetTexture("particle/black_hole");
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        //return EpicACGRenderType.getRenderTypeByTexture(tex);
        return EpicACGRenderType.GravLens;
    }
}
