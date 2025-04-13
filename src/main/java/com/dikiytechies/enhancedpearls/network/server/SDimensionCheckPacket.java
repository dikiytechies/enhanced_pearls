package com.dikiytechies.enhancedpearls.network.server;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SDimensionCheckPacket {
    private final UUID requestedPlayerUUID;
    private final UUID ownerUUID;
    private final boolean shouldRemove;
    public SDimensionCheckPacket(UUID requestedEntityUUID, UUID ownerUUID, boolean shouldRemove) {
        this.requestedPlayerUUID = requestedEntityUUID;
        this.ownerUUID = ownerUUID;
        this.shouldRemove = shouldRemove;
    }

    public static void encode(SDimensionCheckPacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.requestedPlayerUUID);
        buf.writeUUID(msg.ownerUUID);
        buf.writeBoolean(msg.shouldRemove);
    }

    public static SDimensionCheckPacket decode(PacketBuffer buf) {
        UUID requestedEntityUUID = buf.readUUID();
        UUID ownerUUID = buf.readUUID();
        boolean shouldRemove = buf.readBoolean();
        return new SDimensionCheckPacket(requestedEntityUUID, ownerUUID, shouldRemove);
    }
    public static void handle(SDimensionCheckPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EnhancedPearls.LOGGER.info("S->C");
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen instanceof TargetSelectionScreen) {
                ClientPlayNetHandler clientPlayNetHandler = mc.player.connection;
                TargetSelectionScreen targetSelectionScreen = (TargetSelectionScreen) mc.screen;
                if (msg.shouldRemove){
                    if (targetSelectionScreen.playerList.remove(clientPlayNetHandler.getPlayerInfo(msg.requestedPlayerUUID))) EnhancedPearls.LOGGER.info("REMOVED");
                }
                targetSelectionScreen.initGrid(targetSelectionScreen.playerList);
            }
        });
    }
}
