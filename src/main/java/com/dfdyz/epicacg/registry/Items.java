package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.world.item.FireFlySwordItem;
import com.dfdyz.epicacg.world.item.GenShinImpact.BowWeaponItem;
import com.dfdyz.epicacg.world.item.SimpleWeaponItem;
import com.dfdyz.epicacg.world.item.SpecialWeaponItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Items {
    static final List<RegistryObject<Item>> REGISTRY_OBJECTS = new ArrayList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicACG.MODID);
    public static final RegistryObject<Item> TrainingBow = register("training_bow", () ->
            new BowWeaponItem(weapon().durability(600)));
    public static final RegistryObject<Item> BattleScythe = register("battle_scythe", () ->
            new SimpleWeaponItem(weapon(), Tiers.DIAMOND, 8));
    public static final RegistryObject<Item> Elucidator = register("elucidator", () ->
            new SpecialWeaponItem(weapon(), EpicACGTier.SAO_Special, 15));
    public static final RegistryObject<Item> DarkRepulsor = register("dark_repulsor", () -> 
            new SimpleWeaponItem(weapon(), EpicACGTier.SAO_Normal, 15));
    public static final RegistryObject<Item> LambentLight = register("lambent_light", () -> 
            new SimpleWeaponItem(weapon(), EpicACGTier.SAO_Normal, 15));
    public static final RegistryObject<Item> SlimeSpear = register("slime_spear", () -> 
            new SimpleWeaponItem(weapon(), EpicACGTier.SAO_Normal, 15));

    public static final RegistryObject<Item> FireFlySword = register("firefly_sword",
            () -> new FireFlySwordItem(
                    new Item.Properties().stacksTo(1),
                    EpicACGTier.SAO_Normal, 15)
    );

    public static Item.Properties weapon(){
        return new Item.Properties().stacksTo(1);
    }

    public static RegistryObject<Item> register(String n, Supplier<Item> s){
        var i = ITEMS.register(n ,s);
        REGISTRY_OBJECTS.add(i);
        return i;
    }

    public static final RegistryObject<Item> DragonShitCrystal = register("dragon_shit_crystal", () ->
            new Item(new Item.Properties().stacksTo(16)));

    public enum EpicACGTier implements Tier {
        SAO_Normal(4, 9999, 9.0F, 6.0F, 22, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.DIAMOND_BLOCK);
        }),

        SAO_IRON(2, 380, 6.0F, 2.5F, 17, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT);
        }),

        SAO_Special(4, Integer.MAX_VALUE, 9.0F, 6.0F, 22, () -> {
            return Ingredient.of(net.minecraft.world.item.Items.DIAMOND_BLOCK);
        }),
        ;

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadedValue<Ingredient> repairMaterial;

        private EpicACGTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier repairMaterialIn) {
            this.harvestLevel = harvestLevelIn;
            this.maxUses = maxUsesIn;
            this.efficiency = efficiencyIn;
            this.attackDamage = attackDamageIn;
            this.enchantability = enchantabilityIn;
            this.repairMaterial = new LazyLoadedValue(repairMaterialIn);
        }

        public int getUses() {
            return this.maxUses;
        }

        public float getSpeed() {
            return this.efficiency;
        }

        public float getAttackDamageBonus() {
            return this.attackDamage;
        }

        public int getLevel() {
            return this.harvestLevel;
        }

        public int getEnchantmentValue() {
            return this.enchantability;
        }

        public @NotNull Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
        }
    }

}
