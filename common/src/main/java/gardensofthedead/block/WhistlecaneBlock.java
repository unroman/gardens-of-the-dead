package gardensofthedead.block;

import gardensofthedead.network.NetworkHandler;
import gardensofthedead.network.WhistleEffectPacket;
import gardensofthedead.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;

public class WhistlecaneBlock extends WhistlecaneBaseBlock {

    public static final BooleanProperty GROWING = BooleanProperty.create("growing");
    public static final double GROW_CHANCE = 0.1;
    public static final double WHISTLE_CHANCE = 1 / 40D;

    public WhistlecaneBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(GROWING, true));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROWING);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos updatedPos) {
        if (direction == Direction.UP && (newState.is(getHeadBlock()) || newState.is(getBodyBlock()))) {
            return getBodyBlock().defaultBlockState();
        }

        return super.updateShape(state, direction, newState, level, pos, updatedPos);
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (state.getValue(GROWING) && level.isEmptyBlock(pos.above())) {
            int height = getHeightBelowUpToMax(level, pos) + 1;
            if (height < MAX_HEIGHT && randomSource.nextFloat() < GROW_CHANCE) {
                growCane(level, pos, randomSource, height);
                return;
            }
        }

        if (randomSource.nextFloat() < WHISTLE_CHANCE && level.isEmptyBlock(pos.above())) {
            whistle(state, level, pos, randomSource);
        }
    }

    private void whistle(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        Vec3 offset = state.getOffset(level, pos);
        double x = pos.getX() + offset.x + 0.5;
        double z = pos.getZ() + offset.z + 0.5;
        double y = pos.getY() + offset.y + 1;
        float volume = 1;
        float pitch = randomSource.nextFloat() * 0.3F + 0.85F;
        level.playSound(null, x, y, z, ModSoundEvents.WHISTLECANE_WHISTLE.get(), SoundSource.BLOCKS, volume, pitch);
        sendWhistlePacket(level, pos);
    }

    private void sendWhistlePacket(ServerLevel level, BlockPos pos) {
        NetworkHandler.sendToTrackingPlayers(level, pos, new WhistleEffectPacket(pos, level));
    }
}
