package com.dikiytechies.enhancedpearls.init;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.item.CrackedPearl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EnhancedPearls.MOD_ID);

    public static final RegistryObject<Item> CRACKED_PEARL = ITEMS.register("cracked_pearl",
            () -> new CrackedPearl(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));
}
