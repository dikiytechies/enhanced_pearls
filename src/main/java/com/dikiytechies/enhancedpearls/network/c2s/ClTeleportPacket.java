package com.dikiytechies.enhancedpearls.network.c2s;

import com.dikiytechies.enhancedpearls.item.EnhancedPearl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ClTeleportPacket {
    private final UUID requestedPlayerUUID;
    public ClTeleportPacket(UUID requestedEntityUUID) {
        this.requestedPlayerUUID = requestedEntityUUID;
    }

    public static void encode(ClTeleportPacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.requestedPlayerUUID);
    }

    public static ClTeleportPacket decode(PacketBuffer buf) {
        UUID requestedEntityUUID = buf.readUUID();
        return new ClTeleportPacket(requestedEntityUUID);
    }

    public static void handle(ClTeleportPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) return;
            PlayerEntity requestedPlayer = null;
            ServerWorld requestedPlayerWorld = null;
            for (ServerWorld world : Objects.requireNonNull(player.level.getServer()).getAllLevels()) {
                if (world.getEntity(Objects.requireNonNull(world.getServer().getPlayerList().getPlayer(msg.requestedPlayerUUID)).getId()) != null) {
                    requestedPlayer = (PlayerEntity) world.getEntity(Objects.requireNonNull(world.getServer().getPlayerList().getPlayer(msg.requestedPlayerUUID)).getId());
                    requestedPlayerWorld = world;
                }
            }
            if (requestedPlayer == null) return;

            player.level.playSound(player, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            player.teleportTo(requestedPlayerWorld, requestedPlayer.position().x, requestedPlayer.position().y, requestedPlayer.position().z, requestedPlayer.yRot, requestedPlayer.xRot);
            player.level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                if (player.getMainHandItem().getItem() instanceof EnhancedPearl) {
                    if (player.getMainHandItem().isDamageableItem()) {
                        player.getMainHandItem().hurtAndBreak(1, player, stack -> stack.broadcastBreakEvent(Hand.MAIN_HAND));
                    } else {
                        player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        player.getMainHandItem().shrink(1);
                    }
                } else if (player.getOffhandItem().getItem() instanceof EnhancedPearl) {
                    if (player.getOffhandItem().isDamageableItem()) {
                        player.getOffhandItem().hurtAndBreak(1, player, stack -> stack.broadcastBreakEvent(Hand.OFF_HAND));
                    } else {
                        player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        player.getOffhandItem().shrink(1);
                    }
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
