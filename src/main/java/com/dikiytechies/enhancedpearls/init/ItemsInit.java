package com.dikiytechies.enhancedpearls.init;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.item.EnhancedPearl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EnhancedPearls.MOD_ID);

    public static final RegistryObject<Item> CRACKED_PEARL = ITEMS.register("cracked_pearl",
            () -> new EnhancedPearl(new Item.Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1)));
    public static final RegistryObject<EnhancedPearl> CALIBRATED_PEARL = ITEMS.register("calibrated_pearl",
            () -> new EnhancedPearl(new Item.Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1).rarity(Rarity.UNCOMMON).durability(16)));
}
