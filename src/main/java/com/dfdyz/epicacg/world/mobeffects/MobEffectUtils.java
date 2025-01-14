package com.dfdyz.epicacg.world.mobeffects;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.entity.PartEntity;

public class MobEffectUtils {

    public static Entity getMainEntity(PartEntity entity){
        Entity e = entity.getParent();
        if(e instanceof PartEntity){
            return getMainEntity((PartEntity) e);
        }
        else {
            return e;
        }
    }
}
