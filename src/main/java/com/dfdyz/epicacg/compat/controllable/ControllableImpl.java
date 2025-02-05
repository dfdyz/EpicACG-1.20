package com.dfdyz.epicacg.compat.controllable;

import com.mrcrayfish.controllable.Config;
import com.mrcrayfish.controllable.Controllable;

import java.util.Objects;

public class ControllableImpl implements IJoystickSupport{

    @Override
    public boolean rumble(float lowFrequency, float highFrequency, int timeInMs) {
        if(!Config.CLIENT.client.options.rumble.get()) return false;
        try {
            return Objects.requireNonNull(Controllable.getController()).rumble(lowFrequency, highFrequency, timeInMs);
        }catch (NullPointerException e){
            //e.printStackTrace(System.err);
            return false;
        }
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}
