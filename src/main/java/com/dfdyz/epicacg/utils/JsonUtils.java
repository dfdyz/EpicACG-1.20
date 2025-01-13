package com.dfdyz.epicacg.utils;

import com.google.gson.JsonArray;

public class JsonUtils {
    public static float[] getAsFloatArray(JsonArray ja){
        float[] array = new float[ja.size()];

        for (int i = 0; i < ja.size(); i++) {
            array[i] = ja.get(i).getAsFloat();
        }

        return array;
    }
}
