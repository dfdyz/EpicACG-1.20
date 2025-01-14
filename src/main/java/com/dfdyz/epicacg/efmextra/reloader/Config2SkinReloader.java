package com.dfdyz.epicacg.efmextra.reloader;

import com.dfdyz.epicacg.utils.ReflectionUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.config.RenderConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;

import java.util.Map;

@Deprecated
@OnlyIn(Dist.CLIENT)
public class Config2SkinReloader extends SimpleJsonResourceReloadListener {
    private static Map<Item, ItemSkin> itemMap;
    public Config2SkinReloader(){
        super((new GsonBuilder()).create(), "item_skins");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> r_j_map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        if(ClientConfig.cfg.EnableAutoMerge) Merge();
        else EpicACG.LOGGER.info("AutoMerge Disabled.    Skipped.");
    }

    public static void Merge(){
        GetItemMap();
        if (itemMap != null){
            EpicACG.LOGGER.info("Merge TrailItem into ItemSkins");
            RenderConfig.TrailItem.forEach((id, skin) -> {
                if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(id))){
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
                    itemMap.put(item, skin.toItemSkin());
                }
                else {
                    EpicACG.LOGGER.warn("Unknown item: " + id + ".   Skipped.");
                }
            });
        }
        else {
            EpicACG.LOGGER.info("Merge TrailItem failed");
        }
    }

    public static Map<Item, ItemSkin> GetItemMap(){
        if(itemMap == null) itemMap = ReflectionUtils.GetField(ItemSkins.class, "ITEM_SKIN_MAP");
        return itemMap;
    }

}
