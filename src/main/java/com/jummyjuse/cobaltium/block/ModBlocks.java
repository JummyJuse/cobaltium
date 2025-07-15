package com.jummyjuse.cobaltium.block;

import com.jummyjuse.cobaltium.cobaltium;
import com.jummyjuse.cobaltium.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(cobaltium.MODID);

    public static final DeferredBlock<Block> COBALT_ORE =  registerBlock("cobalt_ore",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> DEEPSLATE_COBALT_ORE =  registerBlock("deepslate_cobalt_ore",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> RAW_COBALT_BLOCK =  registerBlock("raw_cobalt_block",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().sound(SoundType.GILDED_BLACKSTONE)));
    public static final DeferredBlock<Block> COBALT_ALLOY_BLOCK =  registerBlock("cobalt_alloy_block",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().sound(SoundType.COPPER)));
    public static final DeferredBlock<Block> COBALT_HIDE = BLOCKS.register(
            "cobalt_hide",
            () -> new Block(
                    BlockBehaviour.Properties
                            .ofFullCopy(Blocks.IRON_BLOCK)
                            .noOcclusion()             // don’t cull faces, so you can see through
                            .strength(3.0f)
            )
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
