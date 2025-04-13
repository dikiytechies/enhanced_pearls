package com.dikiytechies.enhancedpearls.network.s2c;

import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SrGridInitPacket {
    private final List<UUID> requestedPlayersUUID;

    public SrGridInitPacket(List<UUID> requestedEntityUUID) {
        this.requestedPlayersUUID = requestedEntityUUID;
    }

    public static void encode(SrGridInitPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.requestedPlayersUUID.size());
        msg.requestedPlayersUUID.forEach(uuid -> {
            buf.writeUUID(uuid);
        });
    }

    public static SrGridInitPacket decode(PacketBuffer buf) {
        int size = buf.readInt();
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UUID uuid = buf.readUUID();
            uuids.add(uuid);
        }
        return new SrGridInitPacket(uuids);
    }

    public static void handle(SrGridInitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            List<PlayerEntity> clientPlayers = new ArrayList<>();
            msg.requestedPlayersUUID.forEach(uuid -> {
                PlayerEntity player = mc.player.level.getPlayerByUUID(uuid);
                clientPlayers.add(player);
            });

            TargetSelectionScreen.getInstance().initGrid(clientPlayers);
        });

        ctx.get().setPacketHandled(true);
    }
}
