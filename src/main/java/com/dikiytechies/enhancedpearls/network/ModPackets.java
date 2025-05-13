package com.dikiytechies.enhancedpearls.network;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.network.client.ClGridInitPacket;
import com.dikiytechies.enhancedpearls.network.client.ClOpenTargetSelection;
import com.dikiytechies.enhancedpearls.network.client.ClTeleportPacket;
import com.dikiytechies.enhancedpearls.network.server.TrGridInitPacket;
import com.dikiytechies.enhancedpearls.network.server.TrOpenTargetSelection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class ModPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel clientChannel;
    private static SimpleChannel serverChannel;

    public static void init() {
        clientChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(EnhancedPearls.MOD_ID, "client_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();
        serverChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(EnhancedPearls.MOD_ID, "server_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();

        int packetIndex = 0;
        // Whenever you add a new network packet, you have to register it here like so:
        clientChannel.registerMessage(packetIndex++, ClTeleportPacket.class,
                ClTeleportPacket::encode, ClTeleportPacket::decode, ClTeleportPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        clientChannel.registerMessage(packetIndex++, ClGridInitPacket.class,
                ClGridInitPacket::encode, ClGridInitPacket::decode, ClGridInitPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        clientChannel.registerMessage(packetIndex++, ClOpenTargetSelection.class,
                ClOpenTargetSelection::encode, ClOpenTargetSelection::decode, ClOpenTargetSelection::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        serverChannel.registerMessage(packetIndex++, TrGridInitPacket.class,
                TrGridInitPacket::encode, TrGridInitPacket::decode, TrGridInitPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        serverChannel.registerMessage(packetIndex++, TrOpenTargetSelection.class,
                TrOpenTargetSelection::encode, TrOpenTargetSelection::decode, TrOpenTargetSelection::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }



    public static void sendToServer(Object msg) {
        clientChannel.sendToServer(msg);
    }

    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            serverChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        serverChannel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        serverChannel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }
}
