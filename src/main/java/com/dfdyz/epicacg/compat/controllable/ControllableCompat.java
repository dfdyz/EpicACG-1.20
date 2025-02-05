package com.dfdyz.epicacg.compat.controllable;

import net.minecraftforge.fml.loading.FMLLoader;

public class ControllableCompat {
    public static IJoystickSupport Impl;

    public static void load(){
        if(FMLLoader.getLoadingModList().getModFileById("controllable") != null){
            Impl = new ControllableImpl();
        }
        else {
            Impl = new IJoystickSupport() {
                @Override
                public boolean rumble(float lowFrequency, float highFrequency, int timeInMs) {
                    return false;
                }
            };
        }
    }

}
