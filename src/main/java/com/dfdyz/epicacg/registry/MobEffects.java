package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.world.mobeffects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EpicACG.MODID);
    public static final RegistryObject<MobEffect> WOUND = EFFECTS.register("wound", () -> new WoundEffect(MobEffectCategory.BENEFICIAL, 16735744));
    public static final RegistryObject<MobEffect> STOP = EFFECTS.register("stop", () -> new StopEffect(MobEffectCategory.BENEFICIAL, 16735744));
    public static final RegistryObject<MobEffect> NO_GRAVITY = EFFECTS.register("no_gravity", () -> new NoGravity(MobEffectCategory.BENEFICIAL, 16735744));

}
