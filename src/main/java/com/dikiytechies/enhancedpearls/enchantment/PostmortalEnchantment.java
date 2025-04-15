package com.dikiytechies.enhancedpearls.enchantment;

import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class PostmortalEnchantment extends Enchantment {
    public PostmortalEnchantment(Rarity rarity, EquipmentSlotType... slots) {
        super(rarity, EnchantmentsInit.PEARL, slots);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }
}
