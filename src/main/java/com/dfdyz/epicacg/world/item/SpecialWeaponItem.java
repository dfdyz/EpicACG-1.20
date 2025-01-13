package com.dfdyz.epicacg.world.item;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

import java.util.function.Consumer;

public class SpecialWeaponItem extends WeaponItem {
    protected final int EnchantmentValue;
    public SpecialWeaponItem(Properties builder, Tier tier, int EnchantmentValue) {
        super(tier, 0,0.0f, builder);
        this.EnchantmentValue = EnchantmentValue;
    }
    @Override
    public int getEnchantmentValue() {
        return this.EnchantmentValue;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return 0;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }
}
