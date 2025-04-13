package net.pc2.p11craft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pc2.p11craft.P11Craft;
import net.pc2.p11craft.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup P11_CRAFT_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(P11Craft.MOD_ID, "p11_craft_items"),
            FabricItemGroup.builder().icon(()-> new ItemStack(ModItems.RUBY))
                    .displayName(Text.translatable("itemgroup.p11-craft.p11_craft_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.CHEESE);
                        entries.add(ModItems.CHISEL);
                    }).build());

    public static final ItemGroup P11_CRAFT_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(P11Craft.MOD_ID, "p11_craft_blocks"),
            FabricItemGroup.builder().icon(()-> new ItemStack(ModBlocks.RUBY_BLOCK))
                    .displayName(Text.translatable("itemgroup.p11-craft.p11_craft_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RUBY_BLOCK);
                        entries.add(ModBlocks.RAW_RUBY_BLOCK);
                        entries.add(ModBlocks.WHITE_BRICKS_BLOCK);
                        entries.add(ModBlocks.RUBY_ORE_BLOCK);
                        entries.add(ModBlocks.BLUE_PLANKS_BLOCK);

                    }).build());

    public static void registerItemGroups(){
        P11Craft.LOGGER.info("Registering item groups for " + P11Craft.MOD_ID);


    }
}
