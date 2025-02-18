package com.dikiytechies.enhancedpearls;

import com.dikiytechies.enhancedpearls.init.ItemsInit;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("enhanced_pearls")
public class EnhancedPearls
{
    public static final String MOD_ID = "enhanced_pearls";
    public static final Logger LOGGER = LogManager.getLogger();

    public EnhancedPearls() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        vanillaRegistries(modEventBus);
    }
    private void vanillaRegistries(IEventBus eventBus) {
        ItemsInit.ITEMS.register(eventBus);
    }
}
