package com.dikiytechies.enhancedpearls.network.client;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.server.SDimensionCheckPacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClDimensionCheckPacket {
    private final UUID requestedPlayerUUID;
    private final UUID ownerUUID;
    public ClDimensionCheckPacket(UUID requestedEntityUUID, UUID ownerUUID) {
        this.requestedPlayerUUID = requestedEntityUUID;
        this.ownerUUID = ownerUUID;
    }

    public static void encode(ClDimensionCheckPacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.requestedPlayerUUID);
        buf.writeUUID(msg.ownerUUID);
    }

    public static ClDimensionCheckPacket decode(PacketBuffer buf) {
        UUID requestedEntityUUID = buf.readUUID();
        UUID ownerUUID = buf.readUUID();
        return new ClDimensionCheckPacket(requestedEntityUUID, ownerUUID);
    }
    public static void handle(ClDimensionCheckPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EnhancedPearls.LOGGER.info("2C->S");
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.level;
                PlayerEntity owner = world.getPlayerByUUID(msg.ownerUUID);
                PlayerEntity requestedPlayer = world.getPlayerByUUID(msg.requestedPlayerUUID);
                EnhancedPearls.LOGGER.info(owner);
                EnhancedPearls.LOGGER.info(requestedPlayer);
                ItemStack pearlStack = owner.getMainHandItem();
                int level = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.MULTIDIMENSIONAL.get(), pearlStack);
                ModPackets.sendToClient(new SDimensionCheckPacket(msg.requestedPlayerUUID, msg.ownerUUID, requestedPlayer == null && level <= 0), player);
            }
        });
    }
}
