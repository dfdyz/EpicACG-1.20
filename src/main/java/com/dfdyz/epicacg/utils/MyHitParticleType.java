package com.dfdyz.epicacg.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3d;

import java.util.Random;
import java.util.function.BiFunction;

public class MyHitParticleType {
    public static final BiFunction<Entity, Entity, Vector3d> Atker2Tar = (target, attacker) -> {
        EntityDimensions size = target.getDimensions(target.getPose());
        Random random = new Random();
        double x = target.getX() - attacker.getX(0.5);
        double y = target.getY() +  size.width * 0.5D - attacker.getY(0.5);
        double z = target.getZ() - attacker.getZ(0.5);
        double l = Math.pow(x*x + y*y + z*z,0.5);
        if(l>0){
            x/=l/5;
            y/=l/5;
            z/=l/5;
        }
        x+=random.nextDouble();
        y+=random.nextDouble();
        z+=random.nextDouble();
        l = Math.pow(x*x + y*y + z*z,0.5);
        if(l>0){
            x/=l/5;
            y/=l/5;
            z/=l/5;
        }
        return new Vector3d(x, y, z);
    };

}
