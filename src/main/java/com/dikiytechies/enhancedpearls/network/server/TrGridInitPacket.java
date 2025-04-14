package com.dikiytechies.enhancedpearls.network.server;

import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TrGridInitPacket {
    private final List<UUID> players;

    public TrGridInitPacket(List<UUID> players) {
        this.players = players;
    }
    public static void encode(TrGridInitPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.players.size());
        msg.players.forEach(buf::writeUUID);
    }
    public static TrGridInitPacket decode(PacketBuffer buf) {
        List<UUID> uuids = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            UUID uuid = buf.readUUID();
            uuids.add(uuid);
        }
        return new TrGridInitPacket(uuids);
    }
    public static void handle(TrGridInitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            List<NetworkPlayerInfo> networkPlayers = new ArrayList<>();
            msg.players.forEach(uuid -> {
                ClientPlayNetHandler clientPlayNetHandler = mc.player.connection;
                    NetworkPlayerInfo player = clientPlayNetHandler.getPlayerInfo(uuid);
                    networkPlayers.add(player);
                });
            TargetSelectionScreen.getInstance().initGrid(networkPlayers);
        });
        ctx.get().setPacketHandled(true);
    }
}
