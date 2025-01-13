package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.utils.RenderUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.util.Mth.nextFloat;

public class JudgementCutParticle extends NoRenderParticle {
    public JudgementCutParticle(ClientLevel level, double x, double y, double z, double rx, double ry, double rz) {
        super(level, x, y, z, rx, ry, rz);

        //level.addParticle(RegParticle.JudgementCutTrail.get(), x, y, z,x, y, z);

        //EpicAddon.LOGGER.info(rx+"");
        lifetime = 25;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        for (int i = 0; i<2; i++){
            float r = nextFloat(random,5,8);
            float theta = nextFloat(random,0,360);
            float beta = nextFloat(random,45,80);

            float r2 = 13-5;
            float theta2 = nextFloat(random,180+theta-45,180+theta+45);
            float beta2 = nextFloat(random,180+beta-20,180+beta+20);

            theta = (float) (theta/180*Math.PI);
            beta = (float) (beta/180*Math.PI);
            theta2 = (float) (theta2/180*Math.PI);
            beta2 = (float) (beta2/180*Math.PI);

            float scale = 1.35f;
            double sr = r*Math.sin(beta);
            double sx = sr*Math.sin(theta)*scale;
            double sy = r*Math.cos(beta)*scale;
            double sz = sr*Math.cos(theta)*scale;

            double er = r2*Math.sin(beta2);
            double ex = er*Math.sin(theta2)*scale;
            double ey = r2*Math.cos(beta2)*scale;
            double ez = er*Math.cos(theta2)*scale;


            RenderUtils.AddParticle(level, new JCBladeTrail(level,
                    sx + x,
                    sy + y + 1.2,
                    sz + z,
                    (-ex - sx),
                    ey-sy,
                    (-ez-sz)));
        }
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
            return new JudgementCutParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
