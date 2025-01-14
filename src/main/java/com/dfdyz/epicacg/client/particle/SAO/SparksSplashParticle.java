package com.dfdyz.epicacg.client.particle.SAO;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class SparksSplashParticle extends TextureSheetParticle {
    private int lt = 0;
    protected SparksSplashParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y+0.5d;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    protected int getLightColor(float p_107249_) {
        int i = super.getLightColor(p_107249_);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    @Override
    public void tick() {
        super.tick();
        float a = Math.max(1.0f*this.lifetime/(this.lt-1),1.0f);
        //this.alpha = a;
        this.setColor(1f,a*0.5f,a*0.5f);
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SparksSplashParticle particle = new SparksSplashParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.move(0, 0.5F, 0);
            Random random = new Random();
            float mass = random.nextFloat() + 0.2F;
            particle.lifetime = 21 + (random.nextInt()%10);
            particle.lt = particle.lifetime;
            particle.gravity = mass * 1.5F;
            particle.pickSprite(this.spriteSet);
            particle.scale(0.4f);
            particle.setColor(1f, 0.5f, 0.5f);
            return particle;
        }
    }

}
