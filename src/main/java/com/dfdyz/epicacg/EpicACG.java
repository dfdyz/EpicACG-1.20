package com.dfdyz.epicacg;

import com.dfdyz.epicacg.command.ClientCommands;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillCategories;
import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.event.LoadingEvents;
import com.dfdyz.epicacg.network.Netmgr;
import com.dfdyz.epicacg.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("removal")
@Mod(EpicACG.MODID)
public class EpicACG
{
    public static final String MODID = "epicacg";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String VERSION = "20.9.6.0.fix2";

    public EpicACG()
    {
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus fg_bus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::setupCommon);

        if(FMLEnvironment.dist == Dist.CLIENT){
            bus.addListener(EventPriority.LOWEST, this::regClientReloader);
            bus.addListener(Particles::registryParticles);
            bus.addListener(PostPasses::register);
            bus.addListener(this::setupClient);
            bus.addListener(LoadingEvents::onModelRegister);
            bus.addListener(LoadingEvents::onModelBaked);
            fg_bus.addListener(ClientCommands::registerClientCommands);
        }

        bus.addListener(MyAnimations::registerAnimations);
        bus.addListener(WeaponTypes::register);

        EpicACGSkillCategories.ENUM_MANAGER.registerEnumCls(MODID, EpicACGSkillCategories.class);
        EpicACGSkillSlot.ENUM_MANAGER.registerEnumCls(MODID,EpicACGSkillSlot.class);
        WeaponTypes.EpicACGWeaponCategories.ENUM_MANAGER.registerEnumCls(MODID, WeaponTypes.EpicACGWeaponCategories.class);
        WeaponTypes.EpicACGStyles.ENUM_MANAGER.registerEnumCls(MODID, WeaponTypes.EpicACGStyles.class);

        Items.ITEMS.register(bus);
        Entities.ENTITIES.register(bus);
        Particles.PARTICLES.register(bus);
        MobEffects.EFFECTS.register(bus);
        Tab.TABS.register(bus);
        Sounds.SOUNDS.register(bus);
        MySkillDataKeys.DATA_KEYS.register(bus);
        bus.addListener(MySkills::BuildSkills);

        bus.register(Netmgr.CHANNEL);
        // Register the enqueueIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in

        fg_bus.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(Netmgr::register);
    }


    private void setupClient(final FMLClientSetupEvent event){
        MyAnimations.LoadCamAnims();
        MyModels.LoadOtherModel();
        event.enqueueWork(LoadingEvents::RegItemModelOverride);
        //Effeks.load();

        try {
            ClientConfig.Load(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void regClientReloader(final RegisterClientReloadListenersEvent event){
        //event.registerReloadListener(new Config2SkinReloader());
    }

    /*
    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

     */

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)


    /*
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }*/
}
