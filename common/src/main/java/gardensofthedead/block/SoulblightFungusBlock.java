package gardensofthedead.block;

import gardensofthedead.registry.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulblightFungusBlock extends BushBlock implements BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 13, 12);
    private static final double BONEMEAL_SUCCESS_PROBABILITY = 0.4D;

    public SoulblightFungusBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.WART_BLOCKS)
                || state.is(BlockTags.NYLIUM)
                || state.is(Blocks.MYCELIUM)
                || state.is(Blocks.SOUL_SOIL)
                || state.is(Blocks.SOUL_SAND)
                || super.mayPlaceOn(state, level, pos);
    }

    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        Block block = (ModConfiguredFeatures.SOULBLIGHT_FUNGUS_PLANTED.get().config()).validBaseState().getBlock();
        BlockState blockstate = level.getBlockState(pos.below());
        return blockstate.is(block);
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return randomSource.nextFloat() < BONEMEAL_SUCCESS_PROBABILITY;
    }

    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        ModConfiguredFeatures.SOULBLIGHT_FUNGUS_PLANTED.get().place(level, level.getChunkSource().getGenerator(), randomSource, pos);
    }
}
