package com.dfdyz.epicacg.efmextra.weapon;

import net.minecraft.world.item.UseAnim;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class GenShinBow extends WeaponCapability {
    public GenShinBow(CapabilityItem.Builder builder) {
        super(builder);
    }

    @Override
    public UseAnim getUseAnimation(LivingEntityPatch<?> playerpatch) {
        return UseAnim.NONE;
    }
}
