package net.pc2.p11craft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.pc2.p11craft.block.ModBlocks;
import net.pc2.p11craft.item.ModItems;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RUBY, ModBlocks.RUBY_ORE_BLOCK);

                offerSmelting(RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.25f, 200, "ruby");
                offerBlasting(RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.25f, 100, "ruby");

                // base item, compacted item
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ModItems.RUBY, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_BLOCK);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, ModItems.RAW_RUBY, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_RUBY_BLOCK);


                // Craft Blue Planks
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_PLANKS_BLOCK, 8)
                        .pattern("PPP")
                        .pattern("PDP")
                        .pattern("PPP")
                        .input('P', ItemTags.PLANKS)    // is this literally all it takes? lmao
                        .input('D', Items.BLUE_DYE)
                        .criterion("has_blue_dye", conditionsFromItem(Items.BLUE_DYE))
                        .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                        .offerTo(exporter);

                // Craft White Bricks
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHITE_BRICKS_BLOCK, 2)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .input('B', Items.BRICK)    // is this literally all it takes? lmao
                        .input('D', Items.WHITE_DYE)
                        .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                        .criterion(hasItem(Items.BRICK), conditionsFromItem(Items.BRICK))
                        .offerTo(exporter, "white_bricks_with_items_and_dye");
                // secondary option (shows how to add strings so computer knows difference between alt recipes)
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHITE_BRICKS_BLOCK, 2)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .input('B', Items.BRICK)    // is this literally all it takes? lmao
                        .input('D', Items.BONE_MEAL)
                        .criterion(hasItem(Items.BONE_MEAL), conditionsFromItem(Items.BONE_MEAL))
                        .criterion(hasItem(Items.BRICK), conditionsFromItem(Items.BRICK))
                        .offerTo(exporter, "white_bricks_with_items_and_bone_meal");
                        // ^^ add the string so it can sort it separately from alt recipe

                // doubling down, accidentally made this with brick items not brickSSS items so also adding brick blocks
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHITE_BRICKS_BLOCK, 8)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .input('B', Items.BRICKS)
                        .input('D', Items.WHITE_DYE)
                        .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter, "white_bricks_with_blocks_and_dye");
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHITE_BRICKS_BLOCK, 8)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .input('B', Items.BRICKS)
                        .input('D', Items.BONE_MEAL)
                        .criterion(hasItem(Items.BONE_MEAL), conditionsFromItem(Items.BONE_MEAL))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter, "white_bricks_with_blocks_and_bone_meal");
            }
        };
    }

    @Override
    public String getName() {
        return "P11-Craft Recipes";
    }
}
