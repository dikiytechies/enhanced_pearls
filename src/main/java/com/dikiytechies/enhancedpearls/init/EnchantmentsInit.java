package com.dikiytechies.enhancedpearls.init;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.enchantment.MultidimensionalEnchantment;
import com.dikiytechies.enhancedpearls.item.EnhancedPearl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentsInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EnhancedPearls.MOD_ID);

    public static final EnchantmentType PEARL = EnchantmentType.create("ENHANCED_PEARL", item -> item instanceof EnhancedPearl);

    public static final RegistryObject<Enchantment> MULTIDIMENSIONAL = ENCHANTMENTS.register("multidimensional",
            () -> new MultidimensionalEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND));
}
