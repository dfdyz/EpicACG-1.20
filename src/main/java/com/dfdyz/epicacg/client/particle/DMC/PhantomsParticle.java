package com.dfdyz.epicacg.client.particle.DMC;

import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.minecraft.util.Mth.nextFloat;

public class PhantomsParticle extends NoRenderParticle {

    private LivingEntityPatch entityPatch;

    public PhantomsParticle(ClientLevel clientLevel, double x, double y, double z,
                            LivingEntityPatch livingEntityPatch
    ) {
        super(clientLevel, x, y, z, 0, 0, 0);
        lastAng = nextFloat(random,0, 360);
        this.entityPatch = livingEntityPatch;
        hasPhysics = false;
    }


    protected float lastAng = 0;

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            if(entityPatch.getOriginal().isAlive() && age % 2 == 0){
                for(int i =0; i < 1; ++i){
                    lastAng = nextFloat(random,120+lastAng-15,120+lastAng+15);
                    float r = nextFloat(random,0, 1.2f) + 6f;

                    double x_ = Math.sin(lastAng / 180 * Math.PI) * r;
                    double y_ = nextFloat(random,-1f, 2f);
                    double z_ = Math.cos(lastAng / 180 * Math.PI) * r;

                    float ang2 = nextFloat(random,180+lastAng-20,180+lastAng+20);

                    r = nextFloat(random, 0,1.2f) + 6f;
                    double dx = (Math.sin(ang2 / 180 * Math.PI) * r) - x_;

                    double dy;
                    if(y_ > 1.2f){
                        dy = nextFloat(random,-1f, -0.2f);
                    }
                    else if(y_ < -0.2f){
                        dy = nextFloat(random,1.2f, 2f);
                    }
                    else {
                        dy = nextFloat(random,-0.2f, 1.2f);
                    }

                    double dz = (Math.cos(ang2 / 180 * Math.PI) * r) - y_;

                    double len = new Vec3(dx, dy, dz).length();
                    int lft = 5;
                    double v =  len / lft;

                    dx /= len / v;
                    dy /= len / v;
                    dz /= len / v;

                    DynamicEntityAfterImgParticle particle = DynamicEntityAfterImgParticle.create(
                            entityPatch,
                            MyAnimations.DMC5_V_JC,
                            x_ + x,
                            y_ + y + 2,
                            z_ + z,
                            dx, dy, dz,
                            lft,
                            0.7166f
                    );

                    particle.setColor(.5f, 0.9f, 0.9f);

                    RenderUtils.AddParticle(level, particle);
                }
            }

        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }
}
