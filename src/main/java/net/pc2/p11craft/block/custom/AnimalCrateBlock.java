package net.pc2.p11craft.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext; // Needed for getOutlineShape
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;   // <-- Ensure this is present
import net.minecraft.util.shape.VoxelShapes; // For combining VoxelShapes
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

/**
 * ================================
 * Animal Crate - Design Overview
 * ================================
 *
 * GOAL:
 * A decorative and functional block that allows players to transport a single passive/tameable mob
 * without using leads or bait. Inspired by the bee hive and axolotl bucket mechanics.
 *
 * BLOCK + ITEM COMBINATION:
 * - This crate has both a block form (placed in the world) and an item form (inventory).
 * - The item stores NBT data about the mob inside and shows basic information visually.
 * - A single CrateBlock and CustomCrateItem are used together to manage functionality.
 *
 * FUNCTIONALITY:
 * - Can "capture" passive mobs like chickens, cows, sheep, etc.
 * - Does NOT allow crating mobs already tamed by other players.
 * - Shows a message (e.g. "This mob cannot be crated") if the mob is invalid.
 * - When full, the crate stores the mob's name, type, and data in NBT.
 * - Placing the block sets a visual crate and keeps the mob "inside" (rendering TBD).
 * - Breaking the block returns the item with the mob data intact.
 * - Players can release the mob from the item with right-click or GUI interaction.
 *
 * INVENTORY RULES:
 * - Full crates cannot be inserted into containers (e.g. chests, hoppers).
 * - Similar to how shulker boxes cannot be nested.
 * - Durability is shown ONLY when the crate is empty.
 * - Durability decreases each time a mob is picked up or released.
 *
 * VISUAL DESIGN:
 * - Uses a 16x16x16 Minecraft-style texture.
 * - Full crates have a "shaded figure" inside the cage as a visual indicator.
 * - Inventory icon may include a mob silhouette (optional, phase 2).
 *
 * VARIANTS:
 * - Potential for two crate types:
 *   - Small (chickens, parrots, baby animals)
 *   - Large (pigs, cows, sheep)
 * - Crafted from basic materials:
 *   - Example: Logs in corners, slabs top/bottom, fences or iron bars in center.
 * - Optional: Wooden variant with higher durability.
 *
 * DEVELOPMENT NOTES:
 * - Rendering mobs inside blocks is complex; initial phase may skip this.
 * - Crate will use sounds similar to bundles for feedback.
 * - Crafting recipes should reflect crate value while remaining early-game accessible.
 *
 *  - PC: also maybe add a trapdoor to the crafting recipe, make it so you can open the block and place it in different
 *        directions? then you could place it on the ground and open and mobs could walk in and get trapped
 */

public class AnimalCrateBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty FULL = BooleanProperty.of("full");
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN; // For open/closed state

    // Define the shape of your block. Cages are often not full cubes.
    // Example: A simple cube for now. You can make this more complex.
    // These values are (minX, minY, minZ, maxX, maxY, maxZ) in 1/16th block units.
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    // You might want different shapes for OPEN state or based on FACING later.

    // Constructor
    public AnimalCrateBlock(Settings settings) {
        super(settings);
        // Set default states for the properties you've defined.
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FULL, false)
                .with(FACING, Direction.NORTH) // Default to facing North
                .with(OPEN, false));          // Default to closed
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // Add all your properties here
        builder.add(FULL, FACING, OPEN);
    }

    // This method is called when the block is placed in the world.
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    // --- Interaction Logic ---
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            // On the client, return success if it's likely an action will occur, for hand swing.
            // If player is sneaking, an action (opening/closing) will happen.
            // If not sneaking, an interaction with the block entity is attempted.
            return (player.isSneaking() || state.get(FULL) || /* logic for capturing with empty hand */ true)
                    ? ActionResult.SUCCESS
                    : ActionResult.PASS;
        }

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof AnimalCrateBlockEntity crateEntity) {
            if (player.isSneaking()) { // Player is sneaking, try to open/close the crate
                BlockState newState = state.cycle(OPEN);
                world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD); // Flags ensure update and redraw
                // Play sound based on new state (if it just opened or just closed)
                world.playSound(null, pos, newState.get(OPEN) ? SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN : SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.1f + 0.9f);
                return ActionResult.CONSUME; // Consume the action
            } else {
                // Player is not sneaking, try to interact with the crate's inventory (mob)
                // The AnimalCrateBlockEntity will handle the logic for capturing/releasing.
                return crateEntity.onPlayerInteract(player, hand, state, pos);
            }
        }
        return ActionResult.PASS; // Should not happen if BlockEntity is always present, but good fallback.
    }

    // --- Block Entity Related Methods ---
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        // This is where you return an instance of your BlockEntity.
        // Assumes AnimalCrateBlockEntity has a constructor like: public AnimalCrateBlockEntity(BlockPos pos, BlockState state)
        return new AnimalCrateBlockEntity(pos, state);
        // Make sure you have a registered BlockEntityType for AnimalCrateBlockEntity.
        // e.g. return new AnimalCrateBlockEntity(ModBlockEntities.ANIMAL_CRATE_BLOCK_ENTITY, pos, state);
        // if your BE constructor needs the type. Often, the type is passed in its own constructor:
        // super(ModBlockEntities.ANIMAL_CRATE_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // Provides a ticker if the block entity needs to perform actions every tick.
        // The checkType method is a standard way to ensure you only tick your specific BlockEntity.
        // Replace 'YourModBlockEntityTypes.ANIMAL_CRATE' with your actual registered BlockEntityType.
        if (world.isClient()) {
            // If you need client-side ticking (e.g., for animations handled by the BE):
            // return checkType(type, YourModBlockEntityTypes.ANIMAL_CRATE, AnimalCrateBlockEntity::clientTick);
            return null; // No client-side ticking for now based on your description.
        } else {
            // Server-side ticking
            // The AnimalCrateBlockEntity needs a static method:
            // public static void serverTick(World world, BlockPos pos, BlockState state, AnimalCrateBlockEntity blockEntity)
            return checkType(type, null /* Replace with YourModBlockEntityTypes.ANIMAL_CRATE */, AnimalCrateBlockEntity::serverTick);
        }
    }

    // Helper method for getTicker to ensure correct BlockEntityType.
    // This is a common pattern from Minecraft's own code.
    @SuppressWarnings("unchecked")
    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType,
            BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    // --- Block Behavior and Appearance ---

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Return the defined shape for the block.
        // This affects collision, raycasting, and rendering outline.
        return SHAPE;
        // You could have different shapes based on state, e.g.,
        // if (state.get(OPEN)) return OPEN_SHAPE; else return CLOSED_SHAPE;
    }

    // Called when the block is broken or replaced.
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) { // If the block type has actually changed
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AnimalCrateBlockEntity crateEntity) {
                if (state.get(FULL)) {
                    // If the crate was full, drop the item with the mob's NBT data.
                    // The AnimalCrateBlockEntity should have a method to prepare this stack.
                    // For example, crateEntity.getStackWith contenuData()
                    // ItemScatterer.spawn(world, pos, crateEntity.getDropStackWithNbt()); // You'll need to implement this
                }
                // If you have other inventory items in the BlockEntity (not in this design, but generally),
                // you'd scatter them here too.
                world.updateComparators(pos, this); // Update nearby comparators
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    // Determines how the block interacts with pistons.
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        // If the crate is full, it should break and drop its contents (item form).
        // If empty, it can be pushed normally.
        return state.get(FULL) ? PistonBehavior.BLOCK : PistonBehavior.NORMAL;
        // BLOCK means it will be destroyed by pistons, dropping itself as an item.
        // You could also use PistonBehavior.IGNORE if you don't want full crates to be moved or broken by pistons.
    }

    // --- Redstone Interaction ---
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        // Indicates that this block can output a redstone signal to a comparator.
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        // The strength of the redstone signal.
        // 0 if empty, 15 if full (or some other value based on contents if you extend it).
        return state.get(FULL) ? 15 : 0;
    }

    // Optional: If your block isn't a full cube, you might need to override these for lighting and suffocation.
    // @Override
    // public boolean isOpaque(BlockState state, BlockView world, BlockPos pos) {
    //    // Is the block visually opaque (does it block light rendering)?
    //    return false; // Cages are typically not opaque.
    // }

    // @Override
    // public boolean canSuffocate(BlockState state, BlockView world, BlockPos pos) {
    //    // Can entities suffocate inside this block?
    //    return false; // Probably not for a cage.
    // }

    // Consider how rotation affects the block. For HORIZONTAL_FACING:
    // If you want the block to rotate with structure rotations (e.g. Structure Blocks, Commands)
    /*
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    */
}