package com.dfdyz.epicacg.registry;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.particle.DMC.SpaceBrokenParticle;
import com.dfdyz.epicacg.efmextra.models.mesh.TrashBinMasterMesh;
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

    public static SpaceBrokenParticle.OBJ_JSON SpaceBrokenModel;
    public static void LoadOtherModel(){
        SpaceBrokenModel = SpaceBrokenParticle.OBJ_JSON.loadFromJson(new ResourceLocation(EpicACG.MODID, "models/effect/spacebroken.json"));
    }
}
