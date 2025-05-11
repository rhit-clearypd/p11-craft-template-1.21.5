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
import net.pc2.p11craft.block.custom.MagicBlock;
import net.pc2.p11craft.block.custom.SoulfireLampBlock;


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

    //from chatGPT - uses second version of registerBlock that takes name and block that has already been created
    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            new MagicBlock(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(P11Craft.MOD_ID, "magic_block")))
                    .strength(4f).requiresTool()));

    // p11 note: finally fixed it by adding a new register method so we can use the "new Block..." style. Need to try with copy still

//    public static final Block SOULFIRE_LAMP_BLOCK = registerBlock("soulfire_lamp_block",
//            new SoulfireLampBlock(AbstractBlock.Settings.create()
//                    .strength(1f)
//                    .requiresTool()
//                    .luminance(state -> {
//                        if (state.contains(SoulfireLampBlock.CLICKED)) {
//                            return state.get(SoulfireLampBlock.CLICKED) ? 15 : 0;
//                        }
//                        return 0;
//                    })));
//                    .luminance(state -> state.get(SoulfireLampBlock.CLICKED) ? 15:0)));
//    public static final Block SOULFIRE_LAMP_BLOCK = registerBlock("soulfire_lamp_block",
//            new SoulfireLampBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP)
//                    .luminance(state -> state.getOrEmpty(SoulfireLampBlock.CLICKED).orElse(false) ? 15 : 0)
//            ));
//    public static final Block SOULFIRE_LAMP_BLOCK = registerBlock("soulfire_lamp_block",
//            new SoulfireLampBlock(
//                    AbstractBlock.Settings.create()
//                            .sounds(BlockSoundGroup.LANTERN)
//                            .luminance(state -> state.getOrEmpty(SoulfireLampBlock.CLICKED).orElse(false) ? 15 : 0)
//                            //.luminance(SoulfireLampBlock::getLuminance)
//            ));



//    public static final Block DIORITE_BRICKS_BLOCK = registerBlock("diorite_bricks_block",
//            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)).getSettings());

//    public static final Block DIORITE_BRICKS_BLOCK = registerBlock("diorite_bricks_block",
//            AbstractBlock.Settings.copy(AbstractBlock "bricks")
//            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS.getDefaultState()))



    // ADD NEW BLOCKS HERE
    public static void registerModBlocks(){
        P11Craft.LOGGER.info("Registering Mod Blocks for " + P11Craft.MOD_ID);

        // register blocks here
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.RUBY_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.RAW_RUBY_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.WHITE_BRICKS_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.RUBY_ORE_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.BLUE_PLANKS_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.MAGIC_BLOCK);
//            fabricItemGroupEntries.add(ModBlocks.SOULFIRE_LAMP_BLOCK);
        });
    }



//---- HELPER METHODS BELOW: no need to add anything -------------------------------------------------------------------

    // helper methods to register blocks (original)
    public static Block registerBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(P11Craft.MOD_ID, name));
        Block block = new Block(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(P11Craft.MOD_ID, name), block);
    }

    // secondary version from ChatGPT, now takes in a block that has already been created ("new...")
    public static Block registerBlock(String name, Block block) {
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
