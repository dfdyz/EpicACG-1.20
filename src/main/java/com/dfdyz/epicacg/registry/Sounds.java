package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid= EpicACG.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class Sounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EpicACG.MODID);
    //public static final HashSet<SoundEvent> SOUND_EVENTS = Sets.newHashSet();
    public static final RegistryObject<SoundEvent> GENSHIN_BOW = RegSound("weapon.genshin_bow");
    public static final RegistryObject<SoundEvent> GENSHIN_BOW_FALLATK = RegSound("weapon.genshin_bow_fallatk");
    //public static final RegistryObject<SoundEvent> Yoimiya_Combo1 = RegSound("character.yoimiya.genshin_bow");
    //public static final RegistryObject<SoundEvent> Yoimiya_Combo2 = RegSound("character.yoimiya.genshin_bow");

    public static final RegistryObject<SoundEvent> Yoimiya_Skill1 = RegSound("character.yoimiya.skill1");
    public static final RegistryObject<SoundEvent> Yoimiya_Skill2 = RegSound("character.yoimiya.skill2");
    public static final RegistryObject<SoundEvent> Yoimiya_Skill3 = RegSound("character.yoimiya.skill3");

    public static final RegistryObject<SoundEvent> DualSword_SA1_1 = RegSound("weapon_skill.dual_sword.sa1_1");
    public static final RegistryObject<SoundEvent> DualSword_SA1_2 = RegSound("weapon_skill.dual_sword.sa1_2");
    public static final RegistryObject<SoundEvent> DMC5_JC_1 = RegSound("weapon_skill.dmc5_jc_1");
    public static final RegistryObject<SoundEvent> DMC5_JC_2 = RegSound("weapon_skill.dmc5_jc_2");


    private static RegistryObject<SoundEvent> RegSound(String name) {
        ResourceLocation r = new ResourceLocation(EpicACG.MODID, name);
        return SOUNDS.register(name, () -> {
            return SoundEvent.createVariableRangeEvent(r);
        });
    }
}
