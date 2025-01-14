package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Queue;
import java.util.Random;

import static net.minecraft.util.Mth.nextDouble;

public class AirWaveParticle extends NoRenderParticle {
    int count;

    public AirWaveParticle(ClientLevel level, double x, double y, double z, int waveCount, int lifetime) {
        super(level, x, y, z);
        this.lifetime = lifetime;
        count = waveCount;

        waves.add(new Wave(9.f,0.2f, 30));
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime && waves.size() == 0) {
            this.remove();
        }
        else {
            if(age < lifetime && age % 3 == 0){
                waves.add(new Wave(9.f,0.2f, 40));
            }
        }

        waves.forEach((wave -> {
            wave.tick();
            wave.pushParticle(level, x, y, z, random);
        }));

        waves.removeIf(Wave::isEnd);
    }
    Queue<Wave> waves = Queues.newConcurrentLinkedQueue();

    static class Wave{
        float r = 0.2f;
        float rO = 0.2f;
        float targetR;
        float smooth;

        int lifetime;
        int age = 0;

        public Wave(float targetR, float smooth, int lft){
            this.targetR = targetR;
            this.smooth = smooth;
            this.lifetime = lft;
        }

        public void tick(){
            rO = r;
            r = Mth.lerp(smooth, r, targetR);
            ++age;
        }

        public boolean isEnd(){
            return age >= lifetime;
        }

        public void pushParticle(ClientLevel level, double x, double y, double z, RandomSource random){
            int inter = Math.max(1,(int) ((r - rO) / 0.2f));
            float perR = (r - rO) / inter;
            float perYAdder = 0.5f / inter;

            for (int j = 0; j < inter; j++) {
                int cnt = Math.max(6, (int) (Math.PI * (rO + perR*j) * 2 / 0.2));

                double perAng = Math.PI * 2 / cnt;
                for (int i = 0; i < cnt; i++) {
                    double x_ = Math.cos(perAng * i) * (rO + perR*j);
                    double z_ = Math.sin(perAng * i) * (rO + perR*j);
                    RenderUtils.AddParticle(level, new AirParticle(level,
                            x_ + x + nextDouble(random,-0.2, 0.2),
                            y + nextDouble(random,-0.2, 0.2) + perYAdder * j,
                            z_ + z + nextDouble(random,-0.2, 0.2),
                            nextDouble(random,-0.05, 0.05),
                            0.25f,
                            nextDouble(random,-0.05, 0.05),
                            (int) (6.f / inter * (j+1))));
                }
            }


        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    public static class AirParticle extends Particle{
        public AirParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, int lifetime) {
            super(level, x, y, z, xd, yd, zd);
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
            alphaO = alpha;
            hasPhysics = false;
            this.lifetime = lifetime;

            //this.setColor(0.1f, 0.8f, 1.0f);
        }

        float alphaO;

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            alphaO = alpha;
            if (this.age++ >= this.lifetime) {
                this.remove();
            }
            else {
                x += xd;
                y += yd;
                z += zd;
            }
            alpha = Math.min(0.5f, 0.5f * (lifetime - age) / lifetime);
            alpha = Math.max(alpha, 0);

            setPos(x,y,z);
        }

        float getAlpha(float pt){
            return Mth.lerp(pt,alphaO, alpha);
        }

        @Override
        public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
            float alp = getAlpha(pt);
            float t_ = (age % 10 + pt) / 9.f;
            if(t_ <= 0.5f) t_ = 4*t_ - 1;
            else t_ = -4*t_ + 3;
            float sz =  (0.5f + 0.1f * t_) * alp * 1.8f;

            RenderUtils.RenderQuadFaceOnCamera2(vertexConsumer, camera,
                    (float) Mth.lerp(pt, this.xo, this.x),
                    (float) Mth.lerp(pt, this.yo, this.y),
                    (float) Mth.lerp(pt, this.zo, this.z),
                    this.rCol, this.gCol, this.bCol, alp,
                    sz
            );
        }

        static EpicACGRenderType.EpicACGQuadParticleRenderType renderType = EpicACGRenderType.getRenderTypeByTexture(
                RenderUtils.GetTexture("particle/photo2"));

        @Override
        public ParticleRenderType getRenderType() {
            return renderType;
        }

        @Override
        public boolean shouldCull() {
            return false;
        }

    }



}
