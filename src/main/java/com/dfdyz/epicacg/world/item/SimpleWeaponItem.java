package com.dfdyz.epicacg.world.item;


import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class SimpleWeaponItem extends WeaponItem {
    protected final int EnchantmentValue;
    public SimpleWeaponItem(Properties builder,Tier tier, int EnchantmentValue) {
        super(tier, 0,0.0f, builder);
        this.EnchantmentValue = EnchantmentValue;
    }
    @Override
    public int getEnchantmentValue() {
        return this.EnchantmentValue;
    }
}
