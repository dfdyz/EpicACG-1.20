package com.dfdyz.epicacg.mixinloader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.HashMap;
import java.util.List;

public class CompatInfos {
    public static final HashMap<String, CompatMixinInfo> CompatMixins;
    static final List<CompatMod> CompatMods;

    public static final CompatMod COMPAT_YAMATO;

    static {
        CompatMixins = Maps.newHashMap();
        CompatMods = Lists.newArrayList();

        //todo
        COMPAT_YAMATO = new CompatMod("yamatomoveset",
                    "MixinYamatoTest"
                );
    }

    public static final class CompatMixinInfo {
        final CompatMod mod;
        public CompatMixinInfo(CompatMod mod, String mixinClass){
            this.mod = mod;
            CompatMixins.put(mixinClass, this);
        }

        public boolean shouldApplyMixin(){
            return mod.isLoaded();
        }
    }

    public static final class CompatMod{
        final String modid;
        boolean loaded;

        public CompatMod(String modid, String... mixinClasses){
            this.modid = modid;
            CompatMods.add(this);

            for (int i = 0; i < mixinClasses.length; i++) {
                new CompatMixinInfo(this, mixinClasses[i]);
            }
        }
        public void check(){
            loaded = FMLLoader.getLoadingModList().getModFileById(modid) != null;
        }

        public boolean isLoaded(){
            return loaded;
        }
    }

    public static void initCompatInfo(){
        CompatMods.forEach(CompatMod::check);
    }


    public static boolean shouldMixin(String targetClassName, String mixinClassName){
        if(CompatMixins.containsKey(mixinClassName)){
            return CompatMixins.get(mixinClassName).shouldApplyMixin();
        }
        else {
            System.out.println("[EpicACG Mixin Loader]Apply Default Mixin: " + mixinClassName + ".class -> " + targetClassName + ".class");
            return true;
        }
    }
}
