package com.jummyjuse.cobaltium.item;

import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import com.simibubi.create.content.equipment.armor.CardboardArmorStealthOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CobaltAlloyArmorItem extends BaseArmorItem {

    public CobaltAlloyArmorItem(Type slot, Properties props) {
        super(CobaltArmorMaterials.COBALT_ALLOY,
                slot,
                props,
                ResourceLocation.fromNamespaceAndPath("cobaltium", "cobalt_alloy"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        // Attach Create’s stealth overlay so the player model disappears when sneaking
        //consumer.accept(new CardboardArmorStealthOverlay());
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipe) { return 1000; }
}

