package com.dikiytechies.enhancedpearls;

import com.dikiytechies.enhancedpearls.init.ItemsInit;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("enhanced_pearls")
public class EnhancedPearls
{
    public static final String MOD_ID = "enhanced_pearls";
    public static final Logger LOGGER = LogManager.getLogger();

    public EnhancedPearls() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        vanillaRegistries(modEventBus);
        modEventBus.addListener(this::preInit);
    }
    private void preInit(FMLCommonSetupEvent event) {
        ModPackets.init();
    }
    private void vanillaRegistries(IEventBus eventBus) {
        ItemsInit.ITEMS.register(eventBus);
    }
}
