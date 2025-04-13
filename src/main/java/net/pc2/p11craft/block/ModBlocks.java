package net.pc2.p11craft.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
//import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.pc2.p11craft.P11Craft;

    // p11: to see original minecraft block methods, shift+shift, type in blocks, check the box for files not in this project

public class ModBlocks {

    // ADD NEW BLOCKS HERE

    // Ruby Block
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL));
    // Block of Raw Ruby
    public static final Block RAW_RUBY_BLOCK = registerBlock("raw_ruby_block",
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL));
    // Block of White Bricks
    public static final Block WHITE_BRICKS_BLOCK = registerBlock("white_bricks_block",
            AbstractBlock.Settings.create()
                    .strength(2f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS));
    public static final Block RUBY_ORE_BLOCK = registerBlock("ruby_ore_block",
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE));
            // tried to make it an ExperienceDroppingBlock, but it didn't work
    public static final Block BLUE_PLANKS_BLOCK = registerBlock("blue_planks_block",
            AbstractBlock.Settings.create()
                    .strength(2f)
                    .sounds(BlockSoundGroup.WOOD));
//    public static final Block DIORITE_BRICKS_BLOCK = registerBlock("diorite_bricks_block",
//            AbstractBlock.Settings.copy(AbstractBlock "bricks")
//    public static final Block DIORITE_BRICKS_BLOCK = registerBlock("diorite_bricks_block",
//            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));
//            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS.getDefaultState()))



    // ADD NEW BLOCKS HERE
    public static void registerModBlocks(){
        P11Craft.LOGGER.info("Registering Mod Blocks for " + P11Craft.MOD_ID);

        // register blocks here
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(ModBlocks.RUBY_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(ModBlocks.RAW_RUBY_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(ModBlocks.WHITE_BRICKS_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(ModBlocks.RUBY_ORE_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(ModBlocks.BLUE_PLANKS_BLOCK));

    }

    // HELPER METHODS BELOW: no need to add anything -------------------------------------------------------------------

    // helper methods to register blocks
    public static Block registerBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(P11Craft.MOD_ID, name));
        Block block = new Block(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(P11Craft.MOD_ID, name), block);
    }

    // helper function to register block items
    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID, name));
        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
    }
}
