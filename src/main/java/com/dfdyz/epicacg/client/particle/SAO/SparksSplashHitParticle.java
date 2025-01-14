package com.dfdyz.epicacg.client.particle.SAO;

import com.dfdyz.epicacg.registry.Particles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SparksSplashHitParticle extends NoRenderParticle {
    protected SparksSplashHitParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        int cnt = this.random.nextInt() % 5 + 15;
        for (int i = 0; i < cnt; i++) {
            double ox = random.nextDouble()*2;
            double oy = random.nextDouble()*2;
            double oz = random.nextDouble()*2;
            level.addParticle(Particles.SPARKS_SPLASH.get(),this.x, this.y, this.z, xd+ox, yd+oy, zd+oz);
        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        //private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            //this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SparksSplashHitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}