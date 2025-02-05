package com.dfdyz.epicacg.registry;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.model.custom.NoTextureJsonModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;

@OnlyIn(Dist.CLIENT)
public class MyModels {
    //public static TrashBinMasterMesh TRASH_BIN_MASTER;

    public static void OnMeshLoad(ModelBuildEvent.MeshBuild event){
        //TRASH_BIN_MASTER = event.getAnimated(EpicACG.MODID, "models/trash_bin_mob", TrashBinMasterMesh::new);
    }

    public static NoTextureJsonModel SpaceBrokenModel;
    public static NoTextureJsonModel Sphere;
    public static NoTextureJsonModel BlackHoleEdge;

    public static void LoadOtherModel(){
        SpaceBrokenModel = NoTextureJsonModel.loadFromJson(new ResourceLocation(EpicACG.MODID, "models/effect/spacebroken.json"));
        Sphere = NoTextureJsonModel.loadFromJson(new ResourceLocation(EpicACG.MODID, "models/effect/sphere.json"));
        BlackHoleEdge = NoTextureJsonModel.loadFromJson(new ResourceLocation(EpicACG.MODID, "models/effect/blackhole_edge.json"));
    }
}
