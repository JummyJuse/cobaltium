package com.jummyjuse.cobaltium.item;

import java.util.EnumMap;
import java.util.List;

import com.jummyjuse.cobaltium.cobaltium;
import com.jummyjuse.cobaltium.item.ModItems;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Defines the Cobalt-Alloy armor material properties.
 */
public class CobaltArmorMaterials {
    /** Durability factor used by Type.getDurability(...) */
    public static final int DURABILITY_MULTIPLIER = 11;

    /** The registered ArmorMaterial holder. */
    public static final Holder<ArmorMaterial> COBALT_ALLOY = register();

    private static Holder<ArmorMaterial> register() {
        var defense = Util.make(
                new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class),
                m -> {
                    m.put(ArmorItem.Type.BOOTS,      1);
                    m.put(ArmorItem.Type.LEGGINGS,   2);
                    m.put(ArmorItem.Type.CHESTPLATE, 3);
                    m.put(ArmorItem.Type.HELMET,     2);
                }
        );

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(cobaltium.MODID, "cobalt_alloy"),
                new ArmorMaterial(
                        defense,
                        DURABILITY_MULTIPLIER,
                        SoundEvents.ARMOR_EQUIP_IRON,
                        () -> Ingredient.of(ModItems.COBALT_ALLOY.get()),
                        List.of(new ArmorMaterial.Layer(
                                ResourceLocation.fromNamespaceAndPath(cobaltium.MODID, "cobalt_alloy")
                        )),
                        2.0f,  // toughness
                        0.1f   // knock-back resistance
                )
        );
    }
}
