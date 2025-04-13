package com.dikiytechies.enhancedpearls.network.c2s;

import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.s2c.SrGridInitPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ClGridInitPacket {
    private final ItemStack itemStack;

    public ClGridInitPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void encode(ClGridInitPacket msg, PacketBuffer buf) {
        buf.writeItem(msg.itemStack);
    }

    public static ClGridInitPacket decode(PacketBuffer buf) {
        return new ClGridInitPacket(buf.readItem());
    }

    public static void handle(ClGridInitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) return;
            List<UUID> data = new ArrayList<>();
            for (ServerPlayerEntity serverPlayer : player.level.getServer().getPlayerList().getPlayers()) {
//                if (!EnchantmentHelper.getEnchantments(msg.itemStack).containsKey(EnchantmentsInit.MULTIDIMENSIONAL.get())) {
                if (player.level.dimension().toString().equals(serverPlayer.level.dimension().toString())) {
                    data.add(serverPlayer.getUUID());
                }
//                } else {
//                    data.add(serverPlayer.getUUID());
//                }
            }
            if (!data.isEmpty()) {
                data.remove(player.getUUID());
            }
            ModPackets.sendToClient(new SrGridInitPacket(data), player);
        });

        ctx.get().setPacketHandled(true);
    }
}
