package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.utils.OjangUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.EpicFightCreativeTabs;
import yesman.epicfight.world.item.EpicFightItems;

public class Tab {
    public static final DeferredRegister<CreativeModeTab> TABS;
    public static final RegistryObject<CreativeModeTab> TAB_ITEMS;

    static {
        TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicACG.MODID);
        TAB_ITEMS = TABS.register("items", () -> {
            return CreativeModeTab.builder().title(
                            Component.translatable("itemGroup.epicacg.items")).icon(
                            () -> {
                                return new ItemStack(EpicFightItems.SKILLBOOK.get());
                            })
                    .withTabsBefore(EpicFightCreativeTabs.TABS.getRegistryName())
                    .withBackgroundLocation(
                            OjangUtils.newRL("epicfight", "textures/gui/container/epicfight_creative_tab.png")
                    ).hideTitle().displayItems(
                            (params, output) -> {
                                Items.REGISTRY_OBJECTS.forEach((item) -> {
                                    output.accept(item.get());
                                });
                            })
                    .build();
        });
    }

}
