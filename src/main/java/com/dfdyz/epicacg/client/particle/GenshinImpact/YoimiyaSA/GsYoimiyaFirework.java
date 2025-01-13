package com.dfdyz.epicacg.client.particle.GenshinImpact.YoimiyaSA;

import com.dfdyz.epicacg.utils.FireworkUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GsYoimiyaFirework extends NoRenderParticle {
    private int fType = 0;
    public GsYoimiyaFirework(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.gravity = 0;
        this.setParticleSpeed(0,2.5,0);
    }

    public GsYoimiyaFirework(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);
        this.setParticleSpeed(0,2.5,0);
        fType = (int) dy;
        lifetime = (int) dx;
    }

    @Override
    public void tick() {
        super.tick();
        //level.addParticle(RegParticle.SPARKS_SPLASH.get(),this.x, this.y, this.z, 0, 0.1, 0);
        //level.addParticle(RegParticle.SPARKS_SPLASH.get(),this.x, this.y-0.5f, this.z, 0, 0.1, 0);
        //level.addParticle(RegParticle.SPARKS_SPLASH.get(),this.x, this.y-1f, this.z, 0, 0.1, 0);
    }

    @Override
    public void remove() {
        super.remove();
        this.level.createFireworks(this.x, this.y, this.z, 5,5,5, FireworkUtils.GS_Yoimiya_SA[fType]);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            GsYoimiyaFirework fw = new GsYoimiyaFirework(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return fw;
        }
    }
}
