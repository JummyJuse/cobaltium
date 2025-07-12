package com.jummyjuse.cobaltium.item;

import com.jummyjuse.cobaltium.cobaltium;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(cobaltium.MODID);

    public static final DeferredItem<Item> RAW_COBALT = ITEMS.register("raw_cobalt",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_ALLOY = ITEMS.register("cobalt_alloy",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_SHEET = ITEMS.register("cobalt_sheet",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<SequencedAssemblyItem> INCOMPLETE_BIONIC_MECHANISM = ITEMS.register(
            "incomplete_bionic_mechanism",
            () -> new SequencedAssemblyItem(new Item.Properties())
    );
    public static final DeferredItem<Item> BIONIC_MECHANISM = ITEMS.register("bionic_mechanism",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> SHULK = ITEMS.register("shulk",
            ()-> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_ALLOY_HELMET = ITEMS.register(
            "cobalt_alloy_helmet",
            () -> new CobaltAlloyHelmetItem(new Item.Properties()));

    public static final DeferredItem<Item> COBALT_ALLOY_CHESTPLATE = ITEMS.register(
            "cobalt_alloy_chestplate",
            () -> new CobaltAlloyArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final DeferredItem<Item> COBALT_ALLOY_LEGGINGS = ITEMS.register(
            "cobalt_alloy_leggings",
            () -> new CobaltAlloyArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final DeferredItem<Item> COBALT_ALLOY_BOOTS = ITEMS.register(
            "cobalt_alloy_boots",
            () -> new CobaltAlloyArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
