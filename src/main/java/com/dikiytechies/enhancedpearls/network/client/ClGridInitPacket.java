package com.dikiytechies.enhancedpearls.network.client;

import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.server.TrGridInitPacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ClGridInitPacket {
    private final ItemStack itemStack;
    public ClGridInitPacket(ItemStack stack) {
        this.itemStack = stack;
    }
    public static void encode(ClGridInitPacket msg, PacketBuffer buf) {
        buf.writeItem(msg.itemStack);
    }
    public static ClGridInitPacket decode(PacketBuffer buf) { return new ClGridInitPacket(buf.readItem()); }
    public static void handle(ClGridInitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                List<UUID> players = new ArrayList<>();
                for (ServerWorld world : player.level.getServer().getAllLevels()) {
                    for (ServerPlayerEntity serverPlayer : world.getPlayers(serverPlayer -> !serverPlayer.isSpectator())) {
                        if (player.level.dimension().equals(serverPlayer.level.dimension()) ||
                                EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.MULTIDIMENSIONAL.get(), msg.itemStack) > 0) {
                            players.add(serverPlayer.getUUID());
                        }
                    }
                }
                players.remove(player.getUUID());
                ModPackets.sendToClient(new TrGridInitPacket(players), player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
