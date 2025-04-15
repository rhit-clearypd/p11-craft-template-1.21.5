package net.pc2.p11craft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;
import net.pc2.p11craft.block.ModBlocks;
import net.pc2.p11craft.item.ModItems;
import net.minecraft.registry.RegistryKeys;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RUBY_BLOCK);
        addDrop(ModBlocks.RAW_RUBY_BLOCK);
        addDrop(ModBlocks.WHITE_BRICKS_BLOCK);
        addDrop(ModBlocks.BLUE_PLANKS_BLOCK);
        addDrop(ModBlocks.MAGIC_BLOCK);

        //addDrop(ModBlocks.RUBY_ORE_BLOCK, oreDrops(ModBlocks.RUBY_ORE_BLOCK, ModItems.RAW_RUBY));
        addDrop(ModBlocks.RUBY_ORE_BLOCK, multipleOreDrops(ModBlocks.RUBY_ORE_BLOCK, ModItems.RAW_RUBY, 2, 5));
    }

    // I have no idea if this is going to work or not lol, I stole it from the datagen git
    // custom helper method based on Copper ore behavior
    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
