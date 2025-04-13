package com.dikiytechies.enhancedpearls.network.c2s;

import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.s2c.SrGridInitPacket;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ClGridInitPacket {
    public ClGridInitPacket() {
    }

    public static void encode(ClGridInitPacket msg, PacketBuffer buf) {
    }

    public static ClGridInitPacket decode(PacketBuffer buf) {
        return new ClGridInitPacket();
    }

    public static void handle(ClGridInitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) return;
            List<UUID> data = new ArrayList<>();
            for (ServerPlayerEntity serverPlayer : player.level.getServer().getPlayerList().getPlayers()) {
                if (player.level.dimension() == serverPlayer.level.dimension()) {
                    data.add(player.getUUID());
                }
            }
            ModPackets.sendToClient(new SrGridInitPacket(data), player);
        });

        ctx.get().setPacketHandled(true);
    }
}
