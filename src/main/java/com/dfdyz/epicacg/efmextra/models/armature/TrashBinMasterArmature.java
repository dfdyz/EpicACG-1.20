package com.dfdyz.epicacg.efmextra.models.armature;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

import java.util.Map;

public class TrashBinMasterArmature extends Armature {

    public TrashBinMasterArmature(String name, int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
        super(name, jointNumber, rootJoint, jointMap);
    }
}
