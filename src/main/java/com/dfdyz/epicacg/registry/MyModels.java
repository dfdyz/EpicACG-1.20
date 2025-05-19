package com.dfdyz.epicacg.registry;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.model.custom.NoTextureJsonModel;
import com.dfdyz.epicacg.utils.OjangUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MyModels {
    public static NoTextureJsonModel SpaceBrokenModel;
    public static NoTextureJsonModel Sphere;
    public static NoTextureJsonModel BlackHoleEdge;

    public static void LoadOtherModel(){
        SpaceBrokenModel = NoTextureJsonModel.loadFromJson(OjangUtils.newRL(EpicACG.MODID, "models/effect/spacebroken.json"));
        Sphere = NoTextureJsonModel.loadFromJson(OjangUtils.newRL(EpicACG.MODID, "models/effect/sphere.json"));
        BlackHoleEdge = NoTextureJsonModel.loadFromJson(OjangUtils.newRL(EpicACG.MODID, "models/effect/blackhole_edge.json"));
    }
}
