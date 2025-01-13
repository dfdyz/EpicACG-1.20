package com.dfdyz.epicacg.event;


import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.screeneffect.ScreenEffectBase;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Mod.EventBusSubscriber(modid = EpicACG.MODID, value = Dist.CLIENT)
public class RenderEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if(Minecraft.getInstance().isPaused()) return;
        if(event.phase == TickEvent.Phase.END){
            hiddenEntity.forEach((k,v) -> {
                v.tick();
                if(v.isEnded()){
                    waitToRemove.add(k);
                }
            });

            while (!waitToRemove.isEmpty()){
                UUID uuid = waitToRemove.poll();
                hiddenEntity.remove(uuid);
            }
        }
    }

    static Queue<UUID> waitToRemove = Queues.newArrayDeque();
    static ConcurrentHashMap<UUID, TickTimer> hiddenEntity = new ConcurrentHashMap<>();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void OnLivingEntityRender(RenderLivingEvent.Pre<?,?> event){
        if (hiddenEntity.size() == 0) return;
        if(hiddenEntity.containsKey(event.getEntity().getUUID())){
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    public static void HiddenEntity(LivingEntity entity, int maxTime){
        if(hiddenEntity.containsKey(entity.getUUID())){
            hiddenEntity.get(entity.getUUID()).setTime(maxTime);
        }else {
            hiddenEntity.put(entity.getUUID(), new TickTimer(maxTime));
        }
    }

    public static void UnhiddenEntity(LivingEntity entity){
        hiddenEntity.remove(entity.getUUID());
    }

    static class TickTimer{
        int time;
        TickTimer(int time){
            this.time = time;
        }
        public void tick(){
            if(time > 0){
                --time;
            }
        }

        public void setTime(int time){
            this.time = time;
        }

        public boolean isEnded(){ return time <= 0; }
    }

}
