package com.dfdyz.epicacg.world.entity.mob;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TrashBinMasterEntity extends LivingEntity {
    private List<ItemStack> Slots = Lists.newLinkedList();

    protected TrashBinMasterEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Slots;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }
}
