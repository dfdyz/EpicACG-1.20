package com.dfdyz.epicacg.registry;


import com.dfdyz.epicacg.EpicACG;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class KeyMappings {

    public static final KeyMapping SwitchSkill = new KeyMapping("key." + EpicACG.MODID + ".switch_skill", InputConstants.KEY_B, "key." + EpicACG.MODID + ".gui");
    public static final KeyMapping RightSkill = new KeyMapping("key." + EpicACG.MODID + ".right_skill", InputConstants.UNKNOWN.getValue(), "key." + EpicACG.MODID + ".gui");
    public static final KeyMapping LeftSkill = new KeyMapping("key." + EpicACG.MODID + ".left_skill", InputConstants.UNKNOWN.getValue(), "key." + EpicACG.MODID + ".gui");


    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(SwitchSkill);
        event.register(RightSkill);
        event.register(LeftSkill);
    }
}
