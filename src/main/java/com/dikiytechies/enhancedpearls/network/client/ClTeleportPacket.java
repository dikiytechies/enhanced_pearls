package com.dikiytechies.enhancedpearls.network.client;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.init.ItemsInit;
import com.dikiytechies.enhancedpearls.item.EnhancedPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class ClTeleportPacket {
    private final UUID requestedEntityUUID;
    public ClTeleportPacket(UUID requestedEntityUUID) {
        this.requestedEntityUUID = requestedEntityUUID;
    }

    public static void encode(ClTeleportPacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.requestedEntityUUID);
    }

    public static ClTeleportPacket decode(PacketBuffer buf) {
        UUID requestedEntityUUID = buf.readUUID();
        return new ClTeleportPacket(requestedEntityUUID);
    }

    public static void handle(ClTeleportPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                PlayerEntity requestedPlayer = (PlayerEntity) Minecraft.getInstance().level.getEntity(player.level.getServer().getPlayerList().getPlayer(msg.requestedEntityUUID).getId());
                if (requestedPlayer != null) {
                    player.level.playSound(player, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    player.moveTo(requestedPlayer.position());
                    player.level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        Random random = new Random();
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
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
