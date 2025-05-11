package net.pc2.p11craft.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

public class SoulfireLampBlock extends Block {
    public static final BooleanProperty CLICKED = BooleanProperty.of("clicked");

    // Static luminance function that does not rely on 'this'
    private static final ToIntFunction<BlockState> LUMINANCE_FUNCTION = state -> state.get(CLICKED) ? 15 : 0;

    public SoulfireLampBlock(Settings settings) {
//        super(settings.luminance(LUMINANCE_FUNCTION));
        super(settings);
        setDefaultState(getDefaultState().with(CLICKED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            world.setBlockState(pos, state.cycle(CLICKED));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }
}