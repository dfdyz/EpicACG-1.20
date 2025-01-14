package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.world.entity.projectile.GenShinArrow;
import com.dfdyz.epicacg.world.entity.projectile.YoimiyaSAArrow;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Entities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicACG.MODID);


    public static final RegistryObject<EntityType<GenShinArrow>> GENSHIN_ARROW = ENTITIES.register("genshin_arrow", () ->
            EntityType.Builder.<GenShinArrow>of(GenShinArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("genshin_arrow")
    );

    public static final RegistryObject<EntityType<YoimiyaSAArrow>> GS_YoimiyaSA_ARROW = ENTITIES.register("gs_yoimiya_sa_arrow", () ->
            EntityType.Builder.<YoimiyaSAArrow>of(YoimiyaSAArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("gs_yoimiya_sa_arrow")
    );

    @Mod.EventBusSubscriber(modid = EpicACG.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EntityRendererRegister{
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(GENSHIN_ARROW.get(), TippableArrowRenderer::new);
            event.registerEntityRenderer(GS_YoimiyaSA_ARROW.get(), TippableArrowRenderer::new);
        }
    }

}
