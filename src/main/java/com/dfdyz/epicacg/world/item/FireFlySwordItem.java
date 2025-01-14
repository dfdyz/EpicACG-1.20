package com.dfdyz.epicacg.world.item;


import com.dfdyz.epicacg.client.render.item.ItemRenderProperties;
import net.minecraft.world.item.Tier;

import java.util.function.Consumer;

public class FireFlySwordItem extends SpecialWeaponItem {
    public FireFlySwordItem(Properties builder, Tier tier, int EnchantmentValue) {
        super(builder, tier, EnchantmentValue);
    }

    @Override
    public void initializeClient(Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(ItemRenderProperties.firefly_sword());
    }
}
