package gardensofthedead.network;

import dev.architectury.networking.NetworkManager;
import gardensofthedead.client.WhistleEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WhistleEffectPacket {

    private final BlockPos pos;
    private final ResourceKey<Level> dimension;

    public WhistleEffectPacket(BlockPos pos, Level level) {
        this.pos = pos;
        this.dimension = level.dimension();
    }

    public WhistleEffectPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
        dimension = buffer.readResourceKey(Registry.DIMENSION_REGISTRY);
    }

    void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeResourceKey(dimension);
    }

    void apply(Supplier<NetworkManager.PacketContext> context) {
        context.get().queue(() -> {
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension().equals(dimension)) {
                WhistleEventHandler.add(pos);
            }
        });
    }
}
