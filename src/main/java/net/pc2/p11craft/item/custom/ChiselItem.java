package net.pc2.p11craft.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.Map;

// click on item and press Ctrl+H to bring up class heriarchy, shows how all vanilla items work
public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.MUD, Blocks.MUD_BRICKS,
                    Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        // filter to make sure the item is being used on a block that it's meant to be (from list above)
        if(CHISEL_MAP.containsKey(clickedBlock)) {
            if(!world.isClient()) {
                // for on a server, not in client
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                // damages the item
                context.getStack().damage(1,((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                // plays a sound
                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);

                // particle effects? (doesn't work)
                //world.addBlockBreakParticles(context.getBlockPos(), clickedBlock.getDefaultState());
                //((ServerWorld) world).addBlockBreakParticles(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());
                //world.getBlockState(context.getBlockPos()).getBlock()

            }
        }

        return ActionResult.SUCCESS;
    }
}
