package com.dfdyz.epicacg.compat;

import com.dfdyz.epicacg.EpicACG;

public class IrisCompat {


    static boolean isClassFound(String className) {
        try {
            Class.forName(className, false, Thread.currentThread().getContextClassLoader());
            EpicACG.LOGGER.debug("find class {}", className);
            return true;
        } catch (ClassNotFoundException e) {
            EpicACG.LOGGER.debug("can't find class {}", className);
            return false;
        }
    }

    public static boolean IS_IRIS_LOAD = isClassFound("net.coderbot.iris.compat.sodium.mixin.IrisSodiumCompatMixinPlugin");
    public static boolean IS_OPT_LOAD = false;

}
