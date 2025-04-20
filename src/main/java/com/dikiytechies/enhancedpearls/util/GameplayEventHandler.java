package com.dikiytechies.enhancedpearls.util;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = EnhancedPearls.MOD_ID)
public class GameplayEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void cancelRetrievingPearlDrop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof ServerPlayerEntity) {
            Iterator<ItemEntity> drops = event.getDrops().iterator();
            while (drops.hasNext()) {
                ItemEntity item = drops.next();
                if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.POSTMORTEM.get(), item.getItem()) > 0) {
                    drops.remove();
                }
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void giveBackDroppedPearl(PlayerEvent.Clone event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            PlayerEntity player = event.getOriginal();
            PlayerEntity respawned = event.getPlayer();
            for (int i = 0; i < event.getPlayer().inventory.getContainerSize(); i++) {
                if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.POSTMORTEM.get(), player.inventory.getItem(i)) > 0) {
                    respawned.inventory.setItem(i, player.inventory.getItem(i));
                }
            }
        }
    }
}
