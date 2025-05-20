package com.dfdyz.epicacg.network.Server;

import com.dfdyz.epicacg.client.feedback.utils.RumbleUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S_HitRumble {

    public S_HitRumble(FriendlyByteBuf buf){}
    public S_HitRumble(){}

    public static void handle(S_HitRumble msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(RumbleUtils::PlayForHit);
        ctx.get().setPacketHandled(true);
    }

}
