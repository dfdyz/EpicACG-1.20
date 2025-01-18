package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.efmextra.animated_item.AnimatedItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class Capabilities {

    public static final Capability<AnimatedItem> CAPABILITY_PERIPHERAL =
            CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void register(RegisterCapabilitiesEvent event){
        event.register(AnimatedItem.class);
    }
}
