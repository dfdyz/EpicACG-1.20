package com.dfdyz.epicacg.registry;


import com.dfdyz.epicacg.EpicACG;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.item.RenderBow;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRender {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RegItemModelOverride(FMLClientSetupEvent event){

        event.enqueueWork(() -> {
            EpicACG.LOGGER.info("RegItemModelOverride");
            /*
            ItemProperties.register(Item.Destiny.get(), new ResourceLocation(EpicAddon.MODID, "style"), (itemStack, clientWorld, livingEntity, i) -> {
                CompoundTag tags = itemStack.getOrCreateTag();
                String type = tags.getString("epicaddon_type");
                int t = tags.getShort("epicaddon_typeidx");
                if(t == 0){
                    if(type == DestinyWeaponItem.types[0]) return 0;
                    else return 3;
                }
                else{
                    if(type == DestinyWeaponItem.types[0]) return 2;
                    else{
                        if(t>7) return 1;
                        else return 3;
                    }
                }
            });*/


            ItemProperties.register(Items.TrainingBow.get(), new ResourceLocation(EpicACG.MODID,"pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                if (p_174637_ == null) {
                    return 0.0F;
                } else {
                    return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemProperties.register(Items.TrainingBow.get(), new ResourceLocation(EpicACG.MODID,"pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
            });
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RegItemEFMRenderer(PatchedRenderersEvent.Add event){
        EpicACG.LOGGER.info("Reg Item EFM Renderer Override");
        event.addItemRenderer(Items.TrainingBow.get(), new RenderBow());
    }
}
