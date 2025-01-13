package com.dfdyz.epicacg.config;

import com.dfdyz.epicacg.utils.DeathParticleHandler;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class RenderConfig {
    public static Map<String, Trail> TrailItem = Maps.newHashMap();
    //public static final Map<String, Function<ItemStack,Trail>> SpecialTrailItem = Maps.newHashMap();
    //public static Map<String, HealthBarStyle> HealthBarEntity = Maps.newHashMap();

    public static void Init(){

    }

    static {
        TrailItem.put("minecraft:diamond_sword",new  Trail(0,0,-0.1f,0,0,-1.0f,0,249,255,140));
        TrailItem.put("minecraft:golden_sword",new  Trail(0,0,-0.1f,0,0,-1.0f,255,255,51,140));

        //TrailItem.put("epicaddon:elucidator",new  Trail(0,0,-0.18f,0,0,-1.47f,0,249,255,140));
        //TrailItem.put("epicaddon:dark_repulsor",new  Trail(0,0,-0.18f,0,0,-1.47f,0,249,255,140));
        //TrailItem.put("epicaddon:lambent_light",new  Trail(0,0,-0.12f,0,0,-1.75f,204,0,255,150));

        //TrailItem.put("epicaddon:anneal_blade",new  Trail(0,0,-0.17f,0,0,-1.27f,80,249,255,150));

        //TrailItem.put("epicfight:katana",new  Trail(0,0,-0.2f,0,-0.2f,-1.6f,255,30,30,120));
        //TrailItem.put("epicfight:netherite_greatsword",new Trail(0,0,-0.17f,0,-0f,-2.15f,138,4,226,180));
        //TrailItem.put("epicaddon:destiny",new Trail(0,0,-0.23f,0,0,-2.25f,255,255,51,180));

        /*
        AddSpecial("epicaddon:destiny",(stack)->{
            if(DestinyWeaponItem.getType(stack) == DestinyWeaponItem.types[1]){
                return null;
            }
            else return getItemTrailRaw("epicaddon:destiny");
        });

         */
    }

    public static void AddDeathParticleConfig(EntityType<?> type, DeathParticleHandler.ParticleTransform transform){
        DeathParticleHandler.config.custom.putIfAbsent(EntityType.getKey(type).toString(), transform);
    }

    static {
        AddDeathParticleConfig(EntityType.CREEPER,
                new DeathParticleHandler.ParticleTransform(
                        Vec3.ZERO,
                        new Vec3(-0.5,0,-0.35),
                        new Vec3(0.5,1.82,0.35),
                        new Vec3(90,0,0), 5,
                        4));
        AddDeathParticleConfig(EntityType.ENDERMAN,
                new DeathParticleHandler.ParticleTransform(
                        Vec3.ZERO,
                        new Vec3(-0.5,0,-0.35),
                        new Vec3(0.5,2.8,0.35),
                        Vec3.ZERO, 5,
                        4));
    }

    /*
    public static void AddSpecial(String id,Function<ItemStack,Trail> func){
        //SpecialTrailItem.put(id,func);
    }

    public static Trail getItemTrailRaw(String n){
        return TrailItem.get(n);
    }
    */

    /*
    public static Trail getItemTrail(ItemStack itemStack){
        if(itemStack.isEmpty()) return null;
        String n = itemStack.getItem().getRegistryName().toString();
        Function<ItemStack,Trail> getter = SpecialTrailItem.get(n);
        if(getter != null) return getter.apply(itemStack);
        else return getItemTrailRaw(n);
    }

     */

    /*
    public static void AddHealthBarStyle(EntityType entity,HealthBarStyle healthBarStyle){
        HealthBarEntity.put(entity.getRegistryName().toString(),healthBarStyle);
    }



    public static void AddHealthBarStyle(EntityType entity){
        AddHealthBarStyle(entity,new HealthBarStyle());
    }


    static {

        AddHealthBarStyle(EntityType.ENDER_DRAGON, new HealthBarStyle(6.0f,2,0.0f,60f));
        AddHealthBarStyle(EntityType.TROPICAL_FISH, new HealthBarStyle(0.9f,1,0.6f,110f));
        AddHealthBarStyle(EntityType.SALMON, new HealthBarStyle(0.9f,1,0.6f,110f));
        AddHealthBarStyle(EntityType.SLIME, new HealthBarStyle(1.5f,1,0.43f,100f));
        AddHealthBarStyle(EntityType.ELDER_GUARDIAN, new HealthBarStyle(2.0f,1,0.63f,80f));
        AddHealthBarStyle(EntityType.GHAST, new HealthBarStyle(3.9f,1,-0.6f,72f));
        AddHealthBarStyle(EntityType.POLAR_BEAR, new HealthBarStyle(1.3f,1,0.1f,100f));
        AddHealthBarStyle(EntityType.GUARDIAN, new HealthBarStyle(1.16f,1,0.3f,100f));
        AddHealthBarStyle(EntityType.HOGLIN, new HealthBarStyle(1.3f,1,0.3f,105f));
        AddHealthBarStyle(EntityType.ZOGLIN, new HealthBarStyle(1.3f,1,0.3f,105f));
        AddHealthBarStyle(EntityType.RABBIT, new HealthBarStyle(0.75f,1,0.6f,100f));
        AddHealthBarStyle(EntityType.LLAMA, new HealthBarStyle(1.15f,1,0.1f,110f));
        AddHealthBarStyle(EntityType.PANDA, new HealthBarStyle(1.3f,1,0.3f,110f));
        AddHealthBarStyle(EntityType.OCELOT, new HealthBarStyle(0.85f,1,0.4f,105f));
        AddHealthBarStyle(EntityType.PARROT, new HealthBarStyle(0.8f,1,0.2f,110f));
        AddHealthBarStyle(EntityType.PHANTOM, new HealthBarStyle(0.9f,1,0.46f,110f));
        AddHealthBarStyle(EntityType.RAVAGER, new HealthBarStyle(1.82f,1,0.25f,105f));
        AddHealthBarStyle(EntityType.PUFFERFISH, new HealthBarStyle(0.9f,1,0.36f,110f));
        AddHealthBarStyle(EntityType.TRADER_LLAMA, new HealthBarStyle(1.15f,1,0.1f,110f));
        AddHealthBarStyle(EntityType.SILVERFISH, new HealthBarStyle(0.8f,1,0.6f,110f));
        AddHealthBarStyle(EntityType.TURTLE, new HealthBarStyle(1.08f,1,0.7f,110f));
        AddHealthBarStyle(EntityType.WITHER, new HealthBarStyle(1.0f,1,0.5f,110f));
        AddHealthBarStyle(EntityType.CHICKEN, new HealthBarStyle(0.78f,1,0.48f,100f));


    }*/
}
