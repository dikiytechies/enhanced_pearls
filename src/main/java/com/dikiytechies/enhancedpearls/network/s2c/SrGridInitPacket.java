package com.dikiytechies.enhancedpearls.network.s2c;

import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import com.dikiytechies.enhancedpearls.item.EnhancedPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            List<ClientPlayerEntity> clientPlayers = new ArrayList<>();
            msg.requestedPlayersUUID.forEach(uuid -> {
                if (mc.player == null) return;
                mc.player.level.players().forEach(player -> {
                    if (player instanceof ClientPlayerEntity) {
                        ClientPlayerEntity clientPlayer = (ClientPlayerEntity) player;
                        if (clientPlayer == mc.player) return;
                        clientPlayers.add((ClientPlayerEntity) player);
                    }
                });
            });

            TargetSelectionScreen.getInstance().initGrid(clientPlayers);
        });

        ctx.get().setPacketHandled(true);
    }
}
