package com.jummyjuse.cobaltium.item;

import com.jummyjuse.cobaltium.block.ModBlocks;
import com.jummyjuse.cobaltium.cobaltium;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, cobaltium.MODID);

    public static final Supplier<CreativeModeTab> COBALTIUM_TAB =
            CREATIVE_MODE_TAB.register("cobaltium_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.RAW_COBALT.get()))
                    .title(Component.translatable("creativetab.cobaltium.cobaltium_tab"))
                    .displayItems((ItemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_COBALT.get());
                        output.accept(ModItems.COBALT_INGOT.get());
                        output.accept(ModItems.COBALT_ALLOY.get());
                        output.accept(ModItems.COBALT_SHEET.get());
                        output.accept(ModItems.BIONIC_MECHANISM.get());
                        output.accept(ModItems.SHULK.get());

                        output.accept(ModBlocks.COBALT_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_COBALT_ORE.get());
                        output.accept(ModBlocks.RAW_COBALT_BLOCK.get());
                        output.accept(ModBlocks.COBALT_ALLOY_BLOCK.get());

                        output.accept(ModItems.COBALT_ALLOY_HELMET.get());
                        output.accept(ModItems.COBALT_ALLOY_CHESTPLATE.get());
                        output.accept(ModItems.COBALT_ALLOY_LEGGINGS.get());
                        output.accept(ModItems.COBALT_ALLOY_BOOTS.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
