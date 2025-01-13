package com.dfdyz.epicacg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonConfig {
    public static final Gson GSON = (new GsonBuilder()).create();
    public static final CommonConfigValue Values = new CommonConfigValue();



}
