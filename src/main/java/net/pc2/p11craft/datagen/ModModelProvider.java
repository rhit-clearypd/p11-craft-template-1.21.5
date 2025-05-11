package net.pc2.p11craft.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.pc2.p11craft.block.ModBlocks;
import net.pc2.p11craft.block.custom.SoulfireLampBlock;
import net.pc2.p11craft.item.ModItems;


public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_ORE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WHITE_BRICKS_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLUE_PLANKS_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);

        // debugging
//        System.out.println("SOULFIRE_LAMP_BLOCK: " + Registries.BLOCK.getId(ModBlocks.SOULFIRE_LAMP_BLOCK));
//
//        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.SOULFIRE_LAMP_BLOCK, blockStateModelGenerator.modelCollector);
//        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.SOULFIRE_LAMP_BLOCK, "on", Models.CUBE_ALL, TextureMap::all);
//        blockStateModelGenerator.blockStateCollector.accept(
//                VariantsBlockModelDefinitionCreator.of(ModBlocks.SOULFIRE_LAMP_BLOCK)
//                        .with(BlockStateVariantMap.models(SoulfireLampBlock.CLICKED)
//                                .register(true, BlockStateModelGenerator.createWeightedVariant(lampOnIdentifier))
//                                .register(false, BlockStateModelGenerator.createWeightedVariant(lampOffIdentifier))
//                        ));
        //        // from tutorial
//        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.SOULFIRE_LAMP_BLOCK)
//                .coordinate(BlockStateModelGenerator.createBooleanModelMap(SoulfireLampBlock.CLICKED, lampOnIdentifier, lampOffIdentifier)));
//
//        blockStateModelGenerator.blockStateCollector.accept(
//                VariantsBlockModelDefinitionCreator.of(ModBlocks.SOULFIRE_LAMP_BLOCK)
//                .variant(SoulfireLampBlock.CLICKED, true, lampOnVariant)
//                .variant(SoulfireLampBlock.CLICKED, false, lampOffVariant));
//
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHISEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RESINWOOD, Models.GENERATED);
    }
}
