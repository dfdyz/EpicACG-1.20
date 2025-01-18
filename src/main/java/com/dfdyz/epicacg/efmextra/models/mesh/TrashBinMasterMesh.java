package com.dfdyz.epicacg.efmextra.models.mesh;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.AnimatedVertexBuilder;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.ModelPart;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class TrashBinMasterMesh extends AnimatedMesh {
    public TrashBinMasterMesh(Map<String, float[]> arrayMap,
                              @Nullable Map<MeshPartDefinition, List<AnimatedVertexBuilder>> partBuilders,
                              AnimatedMesh parent, RenderProperties properties) {
        super(arrayMap, partBuilders,parent, properties);
    }
}
