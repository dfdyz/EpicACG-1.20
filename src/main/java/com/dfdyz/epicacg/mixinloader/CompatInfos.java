package com.dfdyz.epicacg.mixinloader;

import com.dfdyz.epicacg.compat.IrisCompat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.types.Func;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompatInfos {
    public static final HashMap<MixinClassName, CompatMixinInfo> CompatMixins;
    static final List<AbstractCompatMod> CompatMods;

    public static AbstractCompatMod COMPAT_YAMATO;
    public static AbstractCompatMod IRIS_SHADER;

    static {
        CompatMixins = Maps.newHashMap();
        CompatMods = Lists.newArrayList();
    }

    static void register(){
        COMPAT_YAMATO = new CompatMod("yamatomoveset",
                "MixinYamatoTest"
        );
        IRIS_SHADER = new CompatMod("oculus",
                (m) -> new CompatMixinInfo(m, "MixinParticleEngineB") ,
                (m) -> new CompatMixinInfo(m, "MixinParticleEngineA") {
                    @Override
                    public boolean shouldApplyMixin() {
                        return !mod.isLoaded();
                    }
                }
                );
    }

    public record MixinClassName(String className){
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MixinClassName that)) return false;
            return className.equals(that.className);
        }
        @Override
        public int hashCode() {
            return className.hashCode();
        }
        public static MixinClassName of(String n){
            return new MixinClassName(n);
        }
    }

    public static class CompatMixinInfo{
        protected final AbstractCompatMod mod;
        public CompatMixinInfo(AbstractCompatMod mod, String mixinClass){
            this.mod = mod;
            CompatMixins.put(MixinClassName.of(mixinClass), this);
        }

        public boolean shouldApplyMixin(){
            return mod.isLoaded();
        }
    }



    public static class CompatMod extends AbstractCompatMod{
        final String modid;
        public CompatMod(String modid, String... mixinClasses){
            super();
            this.modid = modid;

            for (int i = 0; i < mixinClasses.length; i++) {
                new CompatMixinInfo(this, mixinClasses[i]);
            }
        }

        @SafeVarargs
        public CompatMod(String modid, Function<AbstractCompatMod, CompatMixinInfo>... mixinClasses){
            super();
            this.modid = modid;

            for (Function<AbstractCompatMod, CompatMixinInfo> mixinClass : mixinClasses) {
                mixinClass.apply(this);
            }
        }

        public void check(){
            loaded = FMLLoader.getLoadingModList().getModFileById(modid) != null;
        }

        public boolean isLoaded(){
            return loaded;
        }
    }

    public static abstract class AbstractCompatMod{
        protected boolean loaded;

        public AbstractCompatMod(){
            CompatMods.add(this);
        }

        public abstract void check();

        public boolean isLoaded(){
            return loaded;
        }
    }

    public static void initCompatInfo(){
        register();
        CompatMods.forEach(AbstractCompatMod::check);
    }

    static String getClassName(String classPath){
        var s =  classPath.split("\\." );
        return s[s.length-1];
    }

    public static boolean shouldMixin(String targetClassName, String mixinClassName_){
        var mixinClassName = getClassName(mixinClassName_);
        //System.out.println(mixinClassName);
        if(CompatMixins.containsKey(MixinClassName.of(mixinClassName))){
            var should = CompatMixins.get(MixinClassName.of(mixinClassName)).shouldApplyMixin();
            if(should) System.out.println("[EpicACG Mixin Loader]Apply Compat Mixin: " + mixinClassName_ + ".class -> " + targetClassName + ".class");
            else System.out.println("[EpicACG Mixin Loader]Skip Mixin: " + mixinClassName_ + ".class -> " + targetClassName + ".class");
            return should;
        }
        else {
            System.out.println("[EpicACG Mixin Loader]Apply Default Mixin: " + mixinClassName_ + ".class -> " + targetClassName + ".class");
            return true;
        }
    }
}
