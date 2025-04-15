package net.pc2.p11craft.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pc2.p11craft.item.ModItems;
import net.pc2.p11craft.util.ModTags;

public class MagicBlock extends Block {
    public MagicBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    // type "override" to see what all can be changed about a special Block
    // click on Block and press Ctrl+H to see heirarchy, see how vanilla blocks work


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
        return ActionResult.SUCCESS;
    }

    // when an ENTITY steps on the block, so we can make it be an item
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        if(entity instanceof ItemEntity itemEntity) {
            if(isValidItem(itemEntity.getStack())) {    // checks for transformable tag before transforming
                itemEntity.setStack(new ItemStack(Items.IRON_INGOT, itemEntity.getStack().getCount()));

                // p11 note: UPDATE, for future make it so poisonous_potato -> potato, rotten flesh -> leather, etc.
                //           probably wanna use Maps for this, I'm keeping this since I just learned how to use tags lol
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    // checks if the item thrown onto the block is in the Transformable tag
    private boolean isValidItem(ItemStack stack) {
        return stack.isIn(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    // this was supposed to be "appendToolTip", but I guess it's changed since
//    @Override
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
//    }
}
