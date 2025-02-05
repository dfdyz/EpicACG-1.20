package com.dfdyz.epicacg.compat.controllable;

public interface IJoystickSupport {
    boolean rumble(float lowFrequency, float highFrequency, int timeInMs);
    default boolean isLoaded(){
        return false;
    }
}
