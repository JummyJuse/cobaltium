package com.jummyjuse.cobaltium.item;

import java.util.EnumMap;
import java.util.List;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class CobaltArmorMaterials {

    /** Ready as soon as the class is loaded – never null. */
    public static final Holder<ArmorMaterial> COBALT_ALLOY = register();

    private static Holder<ArmorMaterial> register() {

        var defense = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS,      3);
            m.put(ArmorItem.Type.LEGGINGS,   6);
            m.put(ArmorItem.Type.CHESTPLATE, 8);
            m.put(ArmorItem.Type.HELMET,     3);
            m.put(ArmorItem.Type.BODY,      11);   // new 1.21 slot
        });

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                ResourceLocation.fromNamespaceAndPath("cobaltium", "cobalt_alloy"),
                new ArmorMaterial(
                        defense,
                        22,                             // enchantability
                        SoundEvents.ARMOR_EQUIP_IRON,   // equip sound
                        () -> Ingredient.of(Items.IRON_INGOT), // repair
                        List.of(new ArmorMaterial.Layer(
                                ResourceLocation.fromNamespaceAndPath("cobaltium", "cobalt_alloy"))),
                        2.0f,                           // toughness
                        0.1f));                         // knock-back resist
    }

    private CobaltArmorMaterials() {} // utility class
}
