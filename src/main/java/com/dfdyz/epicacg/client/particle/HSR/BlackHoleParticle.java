package com.dfdyz.epicacg.client.particle.HSR;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.registry.MyModels;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.awt.*;

public class BlackHoleParticle extends Particle {
    public BlackHoleParticle(ClientLevel level, Vec3 pos, int lifetime) {
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

    //static final Color col = new Color(8,8,8,255);

    float _scale = 0f;
    float _alpha = 1f;

    float _oScale = 0f;
    float _oAlpha = 1f;



    @Override
    public void render(@NotNull VertexConsumer vertexConsumer, @NotNull Camera camera, float pt) {
        Vec3 vec3 = camera.getPosition();
        //float rt = (age+pt)/lifetime;
        float f = (float)(this.x - vec3.x());
        float f1 = (float)(this.y - vec3.y());
        float f2 = (float)(this.z - vec3.z());

        var col = new Color(.1f,.1f,.1f,  Mth.clampedLerp(_oAlpha,_alpha,pt));
        //MyModels.Sphere.renderNeg(vertexConsumer, null, new Vector3f(f, f1, f2), 6.1f, col2, RenderUtils.EmissiveLightPos);
        MyModels.Sphere.renderNeg(vertexConsumer, null, new Vector3f(f, f1, f2),
                8.1f * Mth.clampedLerp(_oScale,_scale,pt),
                col, RenderUtils.EmissiveLightPos);
    }

    static ParticleRenderType rt = EpicACGRenderType.getTriangleRenderTypeByTexture(EpicACGRenderType.NoneTexture);

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return rt;
    }
}
