package com.dfdyz.epicacg.network;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.network.Client.C_RollSkillSelect;
import com.dfdyz.epicacg.network.Server.S_HitRumble;
import com.dfdyz.epicacg.utils.OjangUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.dfdyz.epicacg.EpicACG.VERSION;

public class Netmgr {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(OjangUtils.newRL(EpicACG.MODID, "network_manager"),
            () -> VERSION, VERSION::equals, VERSION::equals);

    public static <MSG> void sendToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }


    public static <MSG> void sendToClient(MSG message, PacketDistributor.PacketTarget packetTarget) {
        CHANNEL.send(packetTarget, message);
    }

    public static <MSG> void sendToAll(MSG message) {
        sendToClient(message, PacketDistributor.ALL.noArg());
    }

    public static <MSG> void sendToAllPlayerTrackingThisEntity(MSG message, Entity entity) {
        sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        sendToClient(message, PacketDistributor.PLAYER.with(() -> player));
    }

    public static <MSG> void sendToAllPlayerTrackingThisEntityWithSelf(MSG message, ServerPlayer entity) {
        sendToPlayer(message, entity);
        sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }
    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, C_RollSkillSelect.class, C_RollSkillSelect::toBytes, C_RollSkillSelect::fromBytes, C_RollSkillSelect::handle);
        CHANNEL.registerMessage(id++, S_HitRumble.class, (a, b)->{}, S_HitRumble::new, S_HitRumble::handle);
    }

}
