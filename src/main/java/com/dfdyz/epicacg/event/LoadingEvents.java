package com.dfdyz.epicacg.event;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.model.item.BakedModelWithISTER;
import com.dfdyz.epicacg.client.render.item.FireFlySwordRenderer;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class LoadingEvents {

    //public static void onB(ModelBake)

    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        EpicACG.LOGGER.info("Reg Item Renderer");
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(EpicACG.MODID, "firefly_sword"), "inventory");

        BakedModel existingModel = event.getModels().get(location);
        if (existingModel != null && !(existingModel instanceof BakedModelWithISTER)) {
            BakedModelWithISTER isterModel = new BakedModelWithISTER(existingModel);
            event.getModels().put(location, isterModel);
            EpicACG.LOGGER.info("OK.");
        }
        else {
            EpicACG.LOGGER.info("Error while baking model.");
        }

        FireFlySwordRenderer.layer1 = event.getModels().getOrDefault(MODEL_FIREFLY_SWORD_1, null);
        FireFlySwordRenderer.layer0 = event.getModels().getOrDefault(MODEL_FIREFLY_SWORD_0, null);

        if(FireFlySwordRenderer.layer1 == null || FireFlySwordRenderer.layer0 == null){
            FireFlySwordRenderer.layer1 = existingModel;
            FireFlySwordRenderer.layer0 = existingModel;
            EpicACG.LOGGER.error("Failed to load firefly model parts");
        }

        EpicACG.LOGGER.info("OK.");
    }

    public static final ResourceLocation MODEL_FIREFLY_SWORD_1 = new ResourceLocation(EpicACG.MODID,"item/firefly_sword_emissive");
    public static final ResourceLocation MODEL_FIREFLY_SWORD_0 = new ResourceLocation(EpicACG.MODID,"item/firefly_sword_unlit");

    public static void onModelRegister(ModelEvent.RegisterAdditional event){
        event.register(MODEL_FIREFLY_SWORD_0);
        event.register(MODEL_FIREFLY_SWORD_1);
    }

    public static void RegItemModelOverride(){
        try {

            EpicACG.LOGGER.info("Reg Item Model Override");
            /*
            ItemProperties.register(Items.FireFlySword.get(), new ResourceLocation(EpicACG.MODID, "part"),
                    (itemStack, clientWorld, livingEntity, i) ->
                    {
                        CompoundTag tags = itemStack.getOrCreateTag();
                        return tags.getShort("render_part");
                    });*/
        }catch (Exception e){

        }
    }


}
